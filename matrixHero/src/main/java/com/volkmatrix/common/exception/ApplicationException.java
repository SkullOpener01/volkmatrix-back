package com.volkmatrix.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplicationException extends RuntimeException {

    public ApplicationException(String msg, Throwable cause) {
        log.info(msg);
        log.error(msg);
    }


}


