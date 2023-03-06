package org.client.config;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;


@Data
@PropertySource("classpath:application.properties")
public class AppProperty {

    private String version;

    private Integer individualLoadingTimeout;
}
