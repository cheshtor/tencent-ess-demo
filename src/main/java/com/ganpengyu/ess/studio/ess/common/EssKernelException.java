package com.ganpengyu.ess.studio.ess.common;

/**
 * 电子合同内核异常
 *
 * @author Pengyu Gan
 * CreateDate 2022/12/5
 */
public class EssKernelException extends RuntimeException {

    public EssKernelException() {
        super();
    }

    public EssKernelException(String message) {
        super(message);
    }

    public EssKernelException(String message, Throwable cause) {
        super(message, cause);
    }

    public EssKernelException(Throwable cause) {
        super(cause);
    }

    protected EssKernelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
