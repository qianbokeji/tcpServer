package com.qianbo.vo;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * 用于包装已经json序列化的数据
 *
 * @author zhumo 黔博科技
 * @date 2021/8/4 19:31
 */
@Getter
@AllArgsConstructor
@ToString
public class JsonData {

    @JsonValue
    @JsonRawValue
    private final String data;

    public static JsonData of(String data) {
        return data == null ? null : new JsonData(data);
    }

}
