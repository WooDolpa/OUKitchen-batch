package com.project.toy.batch.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: jwlee
 * Date: 2020/07/26
 */
@Configuration
public class StaticApplicationContext implements ApplicationContextAware {

    static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }

    public static ApplicationContext getContext(){
        return applicationContext;
    }
}
