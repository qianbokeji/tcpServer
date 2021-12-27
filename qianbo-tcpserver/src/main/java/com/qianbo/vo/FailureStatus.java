package com.qianbo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 失败请求状态
 *
 * @author zhumo 黔博科技
 * @date 2021/8/4 19:31
 */
@Getter
@AllArgsConstructor
public enum FailureStatus {

    //未认证异常
    NO_AUTHENTICATED(100, "No authenticated"),
    //证书失效
    INVALID_CREDENTIAL(101, "Invalid credential"),
    //证书过期
    EXPIRED_CREDENTIAL(102, "Expired credential"),
    //无权限操作
    AUTHORITY_DENIED(103, "Authority denied"),
    //认证错误
    BAD_CREDENTIAL(104, "Bad credential"),
    //错误的状态
    BAD_STATUS(105, "Bad status"),
    //错误操作
    BAD_OPERATION(106, "Bad operation"),
    //数据不存在
    NO_DATA(-100, "No data"),
    //错误的数据类型,非法参数
    BAD_PARAM(-101, "Bad data type"),
    //服务异常
    SERVER_ERROR(-205, "Server error");

    private final int code;

    private final String message;

}
