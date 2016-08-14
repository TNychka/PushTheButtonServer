package com.pressTheButton.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tyler on 2016-08-14.
 */
@Configuration
@EnableAutoConfiguration
public class StormpathConfiguation {

    public StormpathConfiguation(){
    }

    @Bean
    public StormpathApp stormpathApp(){
        return new StormpathApp();
    }
}
