package com.demo.security.customEvent

import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class AuthenticationFailureUserNotFoundEvent extends AbstractAuthenticationFailureEvent {

    public AuthenticationFailureUserNotFoundEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication, exception);
    }
}