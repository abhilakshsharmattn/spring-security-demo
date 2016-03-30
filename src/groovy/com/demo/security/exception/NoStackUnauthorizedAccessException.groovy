package com.demo.security.exception

import org.springframework.security.core.AuthenticationException

public class NoStackUnauthorizedAccessException extends AuthenticationException {

    public NoStackUnauthorizedAccessException(String msg) {
        super(msg);
    }

    public NoStackUnauthorizedAccessException(String msg, Throwable t) {
        super(msg, t);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        // do nothing
        return this;
    }
}

