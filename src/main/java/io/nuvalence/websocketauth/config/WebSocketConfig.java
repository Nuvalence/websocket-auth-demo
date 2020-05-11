package io.nuvalence.websocketauth.config;

import io.nuvalence.websocketauth.authentication.WebSocketHandshakeAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    final WebSocketHandshakeAuthInterceptor webSocketHandshakeAuthInterceptor;

    @Autowired
    public WebSocketConfig(WebSocketHandshakeAuthInterceptor webSocketHandshakeAuthInterceptor) {
        this.webSocketHandshakeAuthInterceptor = webSocketHandshakeAuthInterceptor;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/websocket/connect")
                .addInterceptors(webSocketHandshakeAuthInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
