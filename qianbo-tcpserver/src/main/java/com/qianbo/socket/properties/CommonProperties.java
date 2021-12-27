package com.qianbo.socket.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zhumo 黔博科技
 * @date 2021/8/4 15:27
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "common")
@PropertySource("classpath:application.yml")
public class CommonProperties {

    private SocketProperties socket = new SocketProperties();

}
