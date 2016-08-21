package com.pressTheButton.Auth;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by Tyler on 2016-08-14.
 */

@Component
@NoArgsConstructor
public class StormpathConfiguation {

    @Bean
    public StormpathApp stormpathApp(){
        return new StormpathApp();
    }
}
