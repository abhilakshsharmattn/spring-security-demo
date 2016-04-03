package com.demo.security.listener

import com.demo.security.customEvent.AuthenticationFailureUserNotFoundEvent
import grails.plugin.springsecurity.userdetails.NoStackUsernameNotFoundException
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.access.event.AbstractAuthorizationEvent
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.event.AbstractAuthenticationEvent
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent
import org.springframework.security.authentication.event.AuthenticationFailureDisabledEvent
import org.springframework.security.authentication.event.AuthenticationFailureExpiredEvent
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent

class ApplicationSecurityEventListener implements ApplicationListener<ApplicationEvent> {

    public void onApplicationEvent(final ApplicationEvent e) {
        if (e instanceof AbstractAuthenticationEvent) {
            if (e instanceof InteractiveAuthenticationSuccessEvent) {
            } else if (e instanceof AuthenticationSuccessEvent) {
            } else if (e instanceof AuthenticationFailureBadCredentialsEvent) {
                //for both wrong password and wrong user name
                Exception ex = e.getException()
                if (ex instanceof NoStackUsernameNotFoundException) {
                    //Do nothing in case no user is found with this name
                } else if (ex instanceof BadCredentialsException) {
                    String username = e.getSource()?.principal
                    print "User \"${username}\" tried to login with incorrect password"
                }
            }

            else if (e instanceof AuthenticationFailureUserNotFoundEvent) {
                String username = e.getSource()?.principal
                print "No user found with username \"${username}\"."
            }

            else if (e instanceof AuthenticationFailureExpiredEvent) {
            } else if (e instanceof AuthenticationFailureLockedEvent) {
            } else if (e instanceof AuthenticationFailureCredentialsExpiredEvent) {
            } else if (e instanceof AuthenticationFailureDisabledEvent) {
            } else if (e instanceof AuthenticationFailureServiceExceptionEvent) {
            } else if (e instanceof AuthenticationFailureProviderNotFoundEvent) {
            } else if (e instanceof AbstractAuthenticationFailureEvent) {
            } else if (e instanceof AuthenticationSwitchUserEvent) {
            }
        }
        else if (e instanceof AbstractAuthorizationEvent) {
        }
    }
}
