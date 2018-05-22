package cinnabar.core.component.websocket.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import cinnabar.core.component.websocket.constant.Constant;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint(Constant.WEBSOCKETPATH).setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //����˷�����Ϣ���ͻ��˵���,����ö��Ÿ���
        registry.enableSimpleBroker(Constant.WEBSOCKETBROADCASTPATH, Constant.P2PPUSHBASEPATH);
        //����һ��һ���͵�ʱ��ǰ׺
        registry.setUserDestinationPrefix(Constant.P2PPUSHBASEPATH);
        //����websoketǰ׺
        registry.setApplicationDestinationPrefixes(Constant.WEBSOCKETPATHPERFIX);
    }
    
}