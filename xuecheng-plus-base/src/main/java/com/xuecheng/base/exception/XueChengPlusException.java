package com.xuecheng.base.exception;

/**
 * @author 朱焕杰
 * @version 1.0
 * @description 统一异常
 * @date 2023/2/25 10:26
 */

public class XueChengPlusException extends RuntimeException {

    private String errMessage;

    public XueChengPlusException() {
    }

    public String getErrMessage() {
        return errMessage;
    }

    public XueChengPlusException(String message) {
        super(message);
        this.errMessage = message;
    }

    public static void cast(String errMessage) {
        throw new XueChengPlusException(errMessage);
    }

    public static void cast(CommonError commonError) {
        throw new XueChengPlusException(commonError.getErrMessage());
    }

}
