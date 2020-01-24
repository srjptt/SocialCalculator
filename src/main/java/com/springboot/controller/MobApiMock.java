package com.springboot.controller;

import com.springboot.pojo.CommonProductOfferGroupDTO;
import com.springboot.response.GetPdpDetailsResponse;
import com.springboot.service.MobApiMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController(value ="/service/mobapi/")
public class MobApiMock {

    @Autowired
    MobApiMockService mobApiMockService;

    @RequestMapping(value = "getPdpDetails", method = RequestMethod.GET)
    @ResponseBody
    public GetPdpDetailsResponse getPdpDetails(@RequestParam String pogId){
        GetPdpDetailsResponse response = new GetPdpDetailsResponse();
        try {
            CommonProductOfferGroupDTO details = mobApiMockService.getPdpDetails(pogId);
            if (details!=null)
                response.setCommonProductOfferGroupDTO(details);
        }catch (Exception e){

        }
        return response;
    }
}
