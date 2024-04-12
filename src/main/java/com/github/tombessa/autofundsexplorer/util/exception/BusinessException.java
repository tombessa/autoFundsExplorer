package com.github.tombessa.autofundsexplorer.util.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessException extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(BusinessException.class);

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        logger.error(cause.getMessage());
    }
}