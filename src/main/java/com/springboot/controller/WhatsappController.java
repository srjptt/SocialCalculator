package com.springboot.controller;

import com.springboot.request.CreateAdminRequest;
import com.springboot.request.CreateWhatsAppGroupRequest;
import com.springboot.response.CreateGroupResponse;
import com.springboot.response.SetAdminResponse;
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
    public CreateGroupResponse createGroup(@RequestBody CreateWhatsAppGroupRequest request) {
        CreateGroupResponse response = new CreateGroupResponse();
        try {
            response = whatsappService.createGroup(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value = "/urlAccessImpression", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createGroup(@RequestParam String groupId, @RequestParam String userId, @RequestParam String pogId) {
        try {
            if(whatsappService.urlAccessImpression(groupId, userId))
                whatsappService.sendUpdateInOffer(groupId, pogId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "updated successfully";
    }

    @RequestMapping(value = "/createAdmin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SetAdminResponse createAdmin(@RequestBody CreateAdminRequest request) {
        SetAdminResponse response = new SetAdminResponse();
        try {
         //   Thread.sleep(60000);
            response.setMessage(whatsappService.setGroupAdmin(request.getGroupId(),request.getWhatsappId()));
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

}
