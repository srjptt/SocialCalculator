package com.springboot.controller;

import com.springboot.response.WhatsappResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhatsappController {

    @ResponseBody
    public WhatsappResponse inviteFriends(){
        return null;
    }

}
