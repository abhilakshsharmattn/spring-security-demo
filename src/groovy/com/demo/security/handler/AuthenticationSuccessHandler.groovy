package com.demo.security.handler

import grails.plugin.springsecurity.SpringSecurityUtils
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    final Log log = LogFactory.getLog(this.getClass());

    private String superUserUrl
    private String customerUserUrl

    public void setSuperUserUrl(String s) {
        this.superUserUrl = s
    }

    public void setCustomerUserUrl(String s) {
        this.customerUserUrl = s
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        log.debug("-> AuthenticationSuccessHandler: determineTargetUrl")
        String targetURL

        if (SpringSecurityUtils.ifAllGranted("ROLE_SUPER_USER")) {
            targetURL = superUserUrl
        } else if (SpringSecurityUtils.ifAllGranted("ROLE_CUSTOMER_USER")) {
            targetURL = customerUserUrl
        } else {
            targetURL = super.determineTargetUrl(request, response);
        }
        log.debug("targetURL: ${targetURL}")
        log.debug("<- AuthenticationSuccessHandler: determineTargetUrl")
        targetURL
    }
}
