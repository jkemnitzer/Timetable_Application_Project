package de.hofuniversity.minf.stundenplaner.common;

import lombok.Getter;

@Getter
public class SimpleAuthException extends RuntimeException {

    public enum AuthErrorType {
        LOGIN_FAIL, USER_NOT_FOUND, INVALID_TOKEN, USER_ALREADY_EXIST
    }

    public SimpleAuthException(AuthErrorType type) {
        super("Authentication failed with Error: " + type.toString());
        this.type = type;
    }

    private AuthErrorType type;

}
