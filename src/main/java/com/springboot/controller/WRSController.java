package com.springboot.controller;

import com.springboot.request.FeedRequest;
import com.springboot.request.FeedResponse;
import com.springboot.service.RecommendationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 3:57 PM
 */

@RestController("/wrs")
@Component
public class WRSController {

    @Autowired
    private RecommendationServiceImpl recommendationServiceImpl;

    @RequestMapping(value = "/getBrandRecommendedPog")
    public FeedResponse getBrandRecommendedPog(@RequestBody FeedRequest feedRequest) {
        FeedResponse feedResponse = new FeedResponse();
        try {
            feedResponse.setPogList(recommendationServiceImpl.getRecommendations(feedRequest.getBrandName()
                    .toLowerCase().trim(), feedRequest.getLimit()));
            feedResponse.setCode("200");
            feedResponse.setMessage("Recommendation successful");
        } catch (Exception e) {
            feedResponse.setMessage("Recommendation failure due to :" + e.getMessage());
        }
        return feedResponse;
    }
}
