package com.ustb.evaluation.mod06.ex;

/**
 * @Author 蒲锐
 * @CreateTme 2022/12/16 23:54
 * Version1.0.0
 */

public class RunException extends RuntimeException{
    public RunException() {
        super();
    }

    public RunException(String message) {
        super(message);
    }

    public RunException(String message, Throwable cause) {
        super(message, cause);
    }

    public RunException(Throwable cause) {
        super(cause);
    }

    protected RunException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
