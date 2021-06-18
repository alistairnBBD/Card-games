package com.bbd.tariq.Blackjack;

import com.bbd.tariq.Blackjack.Interfaces.ISecurityService;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ISecurityService _secSecurityService;

    public WebConfig(ISecurityService securityService) {
        _secSecurityService = securityService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(_secSecurityService))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**").pathMatcher(new AntPathMatcher());
    }
}
