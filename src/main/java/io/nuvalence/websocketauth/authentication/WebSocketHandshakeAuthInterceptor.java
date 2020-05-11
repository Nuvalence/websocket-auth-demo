package io.nuvalence.websocketauth.authentication;

import io.nuvalence.websocketauth.models.WebSocketAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@Component
public class WebSocketHandshakeAuthInterceptor extends HttpSessionHandshakeInterceptor {
    final Cache authCache;

    @Autowired
    public WebSocketHandshakeAuthInterceptor(CacheManager cacheManager) {
        this.authCache = cacheManager.getCache("AuthCache");
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        UUID authToken = getAuthToken(request);
        WebSocketAuthInfo webSocketAuthInfo = getWebSocketAuthInfo(authToken);

        if (webSocketAuthInfo == null) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return false;
        }

        return true;
    }

    UUID getAuthToken(ServerHttpRequest request) {
        try {
            return UUID.fromString(UriComponentsBuilder.fromHttpRequest(request).build()
                    .getQueryParams().get("authentication").get(0));
        } catch (NullPointerException e) {
            return null;
        }
    }

    WebSocketAuthInfo getWebSocketAuthInfo(UUID authToken) {
        if (authToken == null) {
            return null;
        }
        Cache.ValueWrapper cacheResult = authCache.get(authToken);
        if (cacheResult == null) {
            return null;
        }
        authCache.evict(authToken);
        return (WebSocketAuthInfo) cacheResult.get();
    }
}
