package io.nuvalence.websocketauth.config;

import io.nuvalence.websocketauth.authentication.HttpAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HttpEndpointConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpAuthenticationInterceptor())
                .addPathPatterns("/authentication/token");
    }
}
