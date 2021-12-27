package com.qianbo.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/5 17:37
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "comm")
public class CommonProperties {

}
