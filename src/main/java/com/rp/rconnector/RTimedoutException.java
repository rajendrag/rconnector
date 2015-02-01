/*
 *  Any use of the Material is governed by the terms of the actual license
 *  agreement between LeanTaaS Inc. and the user.
 *  Copyright 2010 LeanTaaS Inc., Sunnyvale CA USA.
 *  All rights reserved. Any rights not expressly granted herein are
 *  reserved.
 */

package com.rp.rconnector;

public class RTimedoutException extends RuntimeException {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public RTimedoutException() {
    }

    /**
     * @param message
     */
    public RTimedoutException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public RTimedoutException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public RTimedoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
