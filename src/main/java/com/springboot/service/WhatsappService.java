package com.springboot.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class WhatsappService {

    private GenericHttpClientUtility httpClient;

    @PostConstruct
    public void init(){
        this.httpClient=new GenericHttpClientUtility();
    }




}
