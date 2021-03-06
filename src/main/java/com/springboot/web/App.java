package com.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by suraj on 27/6/19.
 */
@EnableAsync
@SpringBootApplication
@ComponentScan({"com.springboot.*"})
public class App extends SpringBootServletInitializer {
    public static void main(String[] args){
                     SpringApplication.run(App.class, args);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }


}
