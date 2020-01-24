package com.springboot.web;

import com.springboot.service.StartupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 7:11 PM
 */
@Component
public class ApplicationContextListener {

    @Autowired
    StartupServiceImpl startupService;


    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent() {
        startupService.init();

    }
}