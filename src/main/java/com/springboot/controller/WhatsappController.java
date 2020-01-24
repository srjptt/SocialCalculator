package com.springboot.controller;

import com.springboot.request.CreateWhatsAppGroupRequest;
import com.springboot.response.WhatsappResponse;
import com.springboot.service.WhatsappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class WhatsappController {

    @Autowired
    private WhatsappService whatsappService;

    @RequestMapping(value = "/createGroup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WhatsappResponse createGroup(@RequestBody CreateWhatsAppGroupRequest request) {
        return null;
    }



}
