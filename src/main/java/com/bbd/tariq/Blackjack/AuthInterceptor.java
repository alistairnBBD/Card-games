package com.bbd.tariq.Blackjack;

import com.bbd.tariq.Blackjack.Exceptions.UnauthorisedException;
import com.bbd.tariq.Blackjack.Interfaces.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    /**
     * Create a new WebRequestHandlerInterceptorAdapter for the given WebRequestInterceptor.
     *
     * @param requestInterceptor the WebRequestInterceptor to wrap
     */

    @Autowired
    private ISecurityService _securityService;

    public AuthInterceptor(ISecurityService securityService) {
        _securityService = securityService;
    }


    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {


        var jwt = request.getHeader("token");

        if (jwt == null)
            throw new UnauthorisedException("ERROR: Authorization is required.");


        var auth = _securityService.checkAuthentication(jwt);

        if (!auth)
            throw new UnauthorisedException("ERROR: Authorization is required.");

        return true;
    }
}
