package io.nuvalence.websocketauth.authentication;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpAuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authenticationToken = request.getHeader("Authorization");

        if (authenticationToken == null || !authenticationToken.equals("SuperSecureToken")) {
            response.setStatus(401);
            return false;
        }

        return true;
    }
}
