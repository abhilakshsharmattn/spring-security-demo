package com.demo.security.filter

import grails.plugin.springsecurity.web.authentication.GrailsUsernamePasswordAuthenticationFilter
import groovy.util.logging.Log4j
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Log4j
class ApplicationAuthenticationFilter extends GrailsUsernamePasswordAuthenticationFilter {

    @Override
    Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.debug("-> attemptAuthentication")
        if (!request.post) {
            throw new AuthenticationServiceException("Authentication method not supported: $request.method")
        }
        log.debug("Checking for Valid Login Form")
        HttpSession session = request.getSession()
        String loginUuidFromRequest = request.getParameter('loginUuid')
        String loginUuidFromSession = session.getAttribute('loginUuid')
        session.removeAttribute("loginUuid")
        if (!loginUuidFromSession.equals(loginUuidFromRequest)) {
            throw new AuthenticationServiceException("Unauthorized authentication request : Invalid uuid")
        }
        log.debug("<- attemptAuthentication: return super.attemptAuthentication(request, response)")
        return super.attemptAuthentication(request, response)
    }
}