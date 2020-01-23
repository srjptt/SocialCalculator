package com.springboot.controller;

import com.springboot.pojo.PDPSro;
import com.springboot.request.GetPdpDetailsRequest;
import com.springboot.response.GetPdpDetailsResponse;
import com.springboot.service.MobApiMockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController(value ="/service/mobapi/")
public class MobApiMock {

    @Autowired
    MobApiMockService mobApiMockService;

    @RequestMapping(value = "getPdpDetails", method = RequestMethod.POST)
    @ResponseBody
    public GetPdpDetailsResponse getPdpDetails(@RequestBody GetPdpDetailsRequest request){
        GetPdpDetailsResponse response = new GetPdpDetailsResponse();
        try {
            PDPSro details = mobApiMockService.getPdpDetails(request);
            response.setPdpsro(details);
        }catch (Exception e){

        }
        return response;
    }
}
