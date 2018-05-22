package cinnabar.core.component.websocket.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Predicates;

import cinnabar.core.component.websocket.constant.Constant;
import cinnabar.core.component.websocket.dto.MessageEntity;

/**
 * send message to one/all
 * with retryer to retry when occur error.
 * @author Vic.Chu
 *
 */
@Service
public class WebSocketService {

	private static final Logger LOG = LoggerFactory.getLogger(WebSocketService.class);
	
    @Autowired
    private SimpMessagingTemplate template;

    /**
     * broadcast
     *
     * @param msg
     */
    public void sendMsg(MessageEntity msg) {
    	try {
    		template.convertAndSend(Constant.PRODUCERPATH, msg);
    	} catch (Exception e) {
    		LOG.error("when sending message to all users, occurred an error! {}", e.getMessage());
    		LOG.debug("trying to resend message to all users");
    		resend2All(msg);
    	}
    }

    /**
     * 
     * @param users
     * @param msg
     */
    public void send2Users(List<String> users, MessageEntity msg) {
        for (String user: users) {
        	try {
        		template.convertAndSendToUser(user, Constant.P2PPUSHPATH, msg);
        	} catch (Exception e) {
        		LOG.error("when sending message to user {}, occurred an error! {}", user, e.getMessage());
        		LOG.debug("trying to resend message to user {}", user);
        		resend2Users(user, msg);
        	}
        }
    }
    
    private void resend2Users(String user, MessageEntity msg) {
    	Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()  
                .retryIfException()
                .retryIfResult(Predicates.equalTo(false))
                .withWaitStrategy(WaitStrategies.fixedWait(5,TimeUnit.SECONDS))  
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))  
                .build(); 
    	try {
			retryer.call(buildTask4Users(user, msg));
		} catch (ExecutionException | RetryException e) {
			e.printStackTrace();
			LOG.error("Sending message to user {} failed! Because of cannot call the retryer!", user);
		}
    }
    
    private void resend2All(MessageEntity msg) {
    	Retryer<Boolean> retryer = RetryerBuilder.<Boolean>newBuilder()  
                .retryIfException()
                .retryIfResult(Predicates.equalTo(false))
                .withWaitStrategy(WaitStrategies.fixedWait(5,TimeUnit.SECONDS))  
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))  
                .build(); 
    	try {
			retryer.call(buildTask4All(msg));
		} catch (ExecutionException | RetryException e) {
			e.printStackTrace();
			LOG.error("Sending message to all users failed! Because of cannot call the retryer!");
		}
    }
    
    private Callable<Boolean> buildTask4Users(String user, MessageEntity msg) {  
        return new Callable<Boolean>() {  
            @Override  
            public Boolean call() throws Exception {
            	try {
            		template.convertAndSendToUser(user, Constant.P2PPUSHPATH, msg);
            		return true;
            	} catch(Exception e) {
            		LOG.error("when retrying to send message to user {}, occurred an error! {}", user, e.getMessage());
            	}
            	return false;
            }  
        };  
    }
    
    private Callable<Boolean> buildTask4All(MessageEntity msg) {  
        return new Callable<Boolean>() {  
            @Override  
            public Boolean call() throws Exception {
            	try {
            		template.convertAndSend(Constant.PRODUCERPATH, msg);
            		return true;
            	} catch(Exception e) {
            		LOG.error("when retrying to send message to all users, occurred an error! {}", e.getMessage());
            	}
            	return false;
            }  
        };  
    } 
}