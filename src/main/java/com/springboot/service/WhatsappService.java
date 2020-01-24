package com.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.request.CreateWhatsAppGroupRequest;
import com.springboot.response.WhatsappResponse;
import com.springboot.utils.GenericHttpClientUtility;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service("whatsappService")
public class WhatsappService {

    private GenericHttpClientUtility httpClient;
    private ObjectMapper mapper;

    @Value("")
    private String token;

    @Value("")
    private String apiEndpoint;

    @PostConstruct
    public void init(){
        this.httpClient = GenericHttpClientUtility.getInstance();
        this.mapper = new ObjectMapper();
    }

    public WhatsappResponse createGroup(CreateWhatsAppGroupRequest request){
return null;
    }


    private HttpResponse getHttpResponse(String msgURL, Map<String, String> headersMap, String body) {
        HttpResponse response = null;
        try {
            response = httpClient.post(msgURL, body, headersMap, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void   populateHeaders(Map<String, String> headersMap, String token) {
        headersMap.put("Content-Type", "application/json");
        headersMap.put("Authorization", "Bearer " + token);
    }

}
