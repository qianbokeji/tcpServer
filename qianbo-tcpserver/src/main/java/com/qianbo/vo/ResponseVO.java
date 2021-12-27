package com.qianbo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * 响应返回对象
 *
 * @author zhumo 黔博科技
 * @date 2021/8/4 19:31
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseVO<T> {

    private final int code;

    private final String message;

    private final T data;

    private static final int OK = 0;

    private static final ResponseVO<Void> SUCCESS = new ResponseVO<>(null);

    public static ResponseVO<Void> success() {
        return SUCCESS;
    }

    public static <T> ResponseVO<T> success(T data) {
        return new ResponseVO<>(data);
    }

    public static ResponseVO<Void> failure(FailureStatus status) {
        return failure(status, status.getMessage());
    }

    public static ResponseVO<Void> failure(FailureStatus status, String message) {
        return new ResponseVO<>(status.getCode(), message, null);
    }

    private ResponseVO(T data) {
        this(OK, null, data);
    }

    private ResponseVO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
