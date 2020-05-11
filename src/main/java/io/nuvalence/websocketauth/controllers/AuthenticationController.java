package io.nuvalence.websocketauth.controllers;

import io.nuvalence.websocketauth.models.WebSocketAuthInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    final Cache authCache;

    @Autowired
    public AuthenticationController(CacheManager cacheManager) {
        this.authCache = cacheManager.getCache("AuthCache");
    }

    @GetMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public UUID getToken() {
        UUID websocketAuthToken = UUID.randomUUID();

        WebSocketAuthInfo webSocketAuthInfo =
                new WebSocketAuthInfo(websocketAuthToken);

        authCache.put(websocketAuthToken, webSocketAuthInfo);

        return websocketAuthToken;
    }
}
