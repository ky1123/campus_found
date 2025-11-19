package com.zhixun.demo.exception;

/**
 * 自定义业务异常类
 * 用于处理业务逻辑中的异常情况
 */
public class CustomException extends RuntimeException {

    /**
     * 构造方法 - 只有错误消息
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * 构造方法 - 包含错误消息和原因
     */
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造方法 - 只有原因
     */
    public CustomException(Throwable cause) {
        super(cause);
    }
}