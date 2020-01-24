package com.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.POGGroupMappingCache;
import com.springboot.pojo.CommonProductOfferGroupDTO;
import com.springboot.request.GetPdpDetailsRequest;
import com.springboot.utils.GenericHttpClientUtility;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("mobApiMockService")
public class MobApiMockService {

    private GenericHttpClientUtility httpClient;
    private ObjectMapper mapper;

    @PostConstruct
    public void init(){
        this.httpClient = GenericHttpClientUtility.getInstance();
        this.mapper = new ObjectMapper();
        Map<String, Set<String>> groupBuyMapping = new HashMap<>();
        POGGroupMappingCache cache = new POGGroupMappingCache();
        cache.setGroupDetailsForGroupBuy(groupBuyMapping);
        CacheManager.getInstance().setCache(cache);
    }

    public CommonProductOfferGroupDTO getPdpDetails(String pogId){
        CommonProductOfferGroupDTO dto = new CommonProductOfferGroupDTO();
        String url = "http://mobileapi.snapdeal.com/service/mobapi/getPdpDetails";
        try{
            GetPdpDetailsRequest request = new GetPdpDetailsRequest(Long.valueOf(pogId));
            StringEntity entity = new StringEntity(mapper.writeValueAsString(request));
            HttpResponse response = httpClient.post(new URI(url), entity, false);
            String data = httpClient.getResponseContent(response);
            Map<String, Object> responseData =  mapper.readValue(data, Map.class);
            Map<String, Object> dtoMap = (Map<String, Object>) ((Map<String, Object>) responseData.get("pdpsro")).get("commonProductOfferGroupDTO");
            List<String> images = (List<String>) ((Map<String, Object>)(((List)dtoMap.get("offers")).get(0))).get("images");
            dto = mapper.convertValue(dtoMap, CommonProductOfferGroupDTO.class);
            dto.setImages(images);
            if(dto.getSellingPrice()==null || dto.getPrice() == null){
                return null;
            }
            return dto;
        }catch (Exception e){
            System.out.println(pogId);
            return null;
        }
    }
}
