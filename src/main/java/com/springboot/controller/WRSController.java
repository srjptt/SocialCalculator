package com.springboot.controller;

import com.springboot.request.FeedRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 3:57 PM
 */

@RestController("/wrs")
public class WRSController {

    @RequestMapping(value = "/getBrandRecommendedPog")
    public List<String> getBrandRecommendedPog(@RequestBody FeedRequest feedRequest){

        return new ArrayList<>();
    }
}
