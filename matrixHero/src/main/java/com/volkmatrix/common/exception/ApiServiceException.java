package com.volkmatrix.common.exception;

import org.springframework.stereotype.Component;

@Component
public class ApiServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    //private ServiceExceptionCodes statusCode;
    private String message;
    private String devMessage;

    public ApiServiceException() {

    }

    public ApiServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ApiServiceException(String msg) {
        super(msg);
    }

}
