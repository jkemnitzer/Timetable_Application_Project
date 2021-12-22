package de.hofuniversity.minf.stundenplaner.common.exception;

import lombok.Getter;

@Getter
public class SimpleAuthException extends RuntimeException{

    public enum AuthErrorType {
        LOGIN_FAIL, USER_NOT_FOUND, INVALID_TOKEN
    }

    public SimpleAuthException(AuthErrorType type){
        super("Authentication failed with Error: "+type.toString());
        this.type = type;
    }

    private final AuthErrorType type;

}
