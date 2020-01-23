package com.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.pojo.CommonProductOfferGroupDTO;
import com.springboot.pojo.PDPSro;
import com.springboot.request.GetPdpDetailsRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Map;

@Service("mobApiMockService")
public class MobApiMockService {

    private GenericHttpClientUtility httpClient;
    private ObjectMapper mapper;

    @PostConstruct
    public void init(){
        this.httpClient = new GenericHttpClientUtility();
        this.mapper = new ObjectMapper();
    }

    public PDPSro getPdpDetails(GetPdpDetailsRequest request){
        PDPSro sro = new PDPSro();
        String url = "http://mobileapi.snapdeal.com/service/mobapi/getPdpDetails";
        try{
            StringEntity entity = new StringEntity(mapper.writeValueAsString(request));
            HttpResponse response = httpClient.post(new URI(url), entity);
            String data = httpClient.getResponseContent(response);
            Map<String, Object> responseData =  mapper.readValue(data, Map.class);
            Map<String, Object> pdpSroMap = (Map<String, Object>) responseData.get("pdpsro");
            sro = mapper.convertValue(pdpSroMap, PDPSro.class);
        }catch (Exception e){
            e.printStackTrace();

        }

        return sro;

    }
}
