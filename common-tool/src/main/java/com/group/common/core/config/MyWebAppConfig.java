package com.group.common.core.config;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyWebAppConfig implements EnvironmentAware {

    public static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
