package org.brave.common;

/**
 * Created by junzhang on 2017/8/3.
 */
public class LockException extends RuntimeException {

    public LockException(String message) {
        super(message);
    }

    public LockException(Exception e){
        super(e);
    }
}
