package com.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.request.CreateWhatsAppGroupRequest;
import com.springboot.response.WhatsappResponse;
import com.springboot.utils.GenericHttpClientUtility;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Map;

@RestController
public class WhatsappController {


    @Value("")
    private String token;

    @Value("")
    private String apiEndpoint;

    private String msgUrl;

    private GenericHttpClientUtility httpClient;
    private ObjectMapper mapper;

    @PostConstruct
    public void init(){
        this.httpClient = GenericHttpClientUtility.getInstance();
        this.mapper = new ObjectMapper();
    }

    @ResponseBody
    public WhatsappResponse createGroup(CreateWhatsAppGroupRequest request) {
        this.msgUrl = apiEndpoint+"";
           /* Map<String, String> headersMap = new HashMap<>();
            populateHeaders(headersMap, token);
            String body = message.getPayload().toJSONString();
            HttpResponse response = getHttpResponse(msgUrl, headersMap, body);
            return parseResponse(response);*/
        return null;
    }

    private HttpResponse getHttpResponse(String msgURL, Map<String, String> headersMap, String body) {
        HttpResponse response = null;
        long start = System.nanoTime();
        try {
            response = httpClient.post(msgURL, body, headersMap, true);
        } catch (Exception e) {
        }
        return response;
    }

    private void   populateHeaders(Map<String, String> headersMap, String token) {
        headersMap.put("Content-Type", "application/json");
        headersMap.put("Authorization", "Bearer " + token);
    }

}
