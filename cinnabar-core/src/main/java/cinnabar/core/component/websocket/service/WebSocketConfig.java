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
        //服务端发送消息给客户端的域,多个用逗号隔开
        registry.enableSimpleBroker(Constant.WEBSOCKETBROADCASTPATH, Constant.P2PPUSHBASEPATH);
        //定义一对一推送的时候前缀
        registry.setUserDestinationPrefix(Constant.P2PPUSHBASEPATH);
        //定义websoket前缀
        registry.setApplicationDestinationPrefixes(Constant.WEBSOCKETPATHPERFIX);
    }
    
}