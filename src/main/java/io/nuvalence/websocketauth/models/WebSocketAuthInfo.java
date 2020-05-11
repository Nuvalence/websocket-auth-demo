package io.nuvalence.websocketauth.models;

import java.util.UUID;

public class WebSocketAuthInfo {
    UUID authToken;

    public WebSocketAuthInfo() {
    }

    public WebSocketAuthInfo(UUID authToken) {
        this.authToken = authToken;
    }

    public UUID getAuthToken() {
        return authToken;
    }
}
