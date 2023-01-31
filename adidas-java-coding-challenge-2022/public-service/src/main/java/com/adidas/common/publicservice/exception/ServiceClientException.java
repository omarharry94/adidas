package com.adidas.common.publicservice.exception;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;


/**
 * Object used to handle runtime exception
 *
 * @author omar.bakhtaoui
 */
public class ServiceClientException extends NestedRuntimeException {
    private final HttpStatus status;

    private final String reason;

    /**
     * Service client exception
     *
     * @param status the HTTP status (required)
     * @param reason the associated reason (optional)
     * @param cause  a nested exception (optional)
     */
    public ServiceClientException(HttpStatus status, String reason, Throwable cause) {
        super(null, cause);
        this.status = status;
        this.reason = reason;
    }

    /**
     * Service client exception
     *
     * @param status the HTTP status (required)
     * @param reason the associated reason (optional)
     */
    public ServiceClientException(HttpStatus status, String reason) {
        this(status, reason,  null);
    }

}
