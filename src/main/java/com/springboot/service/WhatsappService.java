package com.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.POGGroupMappingCache;
import com.springboot.pojo.CommonProductOfferGroupDTO;
import com.springboot.pojo.WhatsappTextPayload;
import com.springboot.request.CreateWhatsAppGroupRequest;
import com.springboot.request.GetPdpDetailsRequest;
import com.springboot.request.WhatsappGroupCreationRequest;
import com.springboot.request.WhatsappMessageSentRequest;
import com.springboot.response.CreateGroupResponse;
import com.springboot.response.GetPdpDetailsResponse;
import com.springboot.utils.GenericHttpClientUtility;
import com.springboot.utils.GroupBuyDiscountCalculatorUtility;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.PostConstruct;
import java.util.*;

@Service("whatsappService")
public class WhatsappService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WhatsappService.class);
    private GenericHttpClientUtility httpClient;
    private ObjectMapper mapper;
    private String token;
    private String endPoint;

    @Autowired
    private Environment env;

    @Autowired
    private MobApiMockService mobApiMockService;

    @PostConstruct
    public void init() {
        this.httpClient = GenericHttpClientUtility.getInstance();
        this.mapper = new ObjectMapper();
        this.endPoint = env.getProperty("whatsapp.endpoint");
        this.token = env.getProperty("whatsapp.token");
    }

    public boolean urlAccessImpression(String groupId, String userId){
        boolean result = false;
        POGGroupMappingCache cache = CacheManager.getInstance().getCache(POGGroupMappingCache.class);
        if (cache.getGroupDetailsForGroupBuy().containsKey(groupId)){
            result = cache.getGroupDetailsForGroupBuy().get(groupId).contains(userId);
            cache.getGroupDetailsForGroupBuy().get(groupId).add(userId);
            return result;

        }else {
            Set<String> users = new HashSet<>();
            users.add(userId);
            cache.getGroupDetailsForGroupBuy().put(groupId, users);
            return true;
        }
    }

    public void sendUpdateInOffer(String groupId, String pogId){
        try {
            String text = new StringBuilder("Checkout link to avail offer!!!").append("http://10.41.97.38:3000/product/").append(pogId)
                    .append("?groupId=").append("918130222868-1579878822").toString();
            String url = new StringBuilder(endPoint).append("messages/").toString();
            WhatsappMessageSentRequest request = new WhatsappMessageSentRequest(groupId, new WhatsappTextPayload(text));
            request.setRecipient_type("group");
            request.setPreview_url(false);
            request.setRender_mentions(true);
            getHttpResponse(url, new HashMap<>(), mapper.writeValueAsString(request) , RequestMethod.POST);
        }catch (Exception e){

        }
    }

    public CreateGroupResponse createGroup(CreateWhatsAppGroupRequest request) {
        CreateGroupResponse response = new CreateGroupResponse();
        try {
            String groupId = createGroup(request.getProductName());
            if (null != groupId) {
                LOGGER.info("Group Id created : "+ groupId);
                String groupInviteLink = getGroupLink(groupId);
                if (null != groupInviteLink) {
                    sendWhatsappLink(groupInviteLink, request.getPhoneNumber());
                    response.setLink(groupInviteLink);
                    response.setGroupId(groupId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String createGroup(String productName) {
        String subject = "SD " + productName;
        if (subject.length()>24)
            subject = subject.substring(0,24);
        WhatsappGroupCreationRequest request = new WhatsappGroupCreationRequest(subject);
        String url = endPoint+"groups";
        String groupId = "";
        try{
            HttpResponse response = getHttpResponse(url, new HashMap<>(), mapper.writeValueAsString(request), RequestMethod.POST);
            Map<String, Object> reponseMap = getResponseMap(response);
            if (reponseMap.containsKey("groups")){
                List groupDetails = (List) reponseMap.get("groups");
                Map<String, Object> groupDetail = (Map<String, Object>) groupDetails.get(0);
                if (groupDetail.containsKey("id")){
                    return groupDetail.get("id").toString();
                }
            }
        }catch (Exception e){

        }

        return groupId;
    }

    private String getGroupLink(String groupId) {
        String url = new StringBuilder(endPoint).append("groups").append("/").append(groupId).append("/").append("invite").toString();
        String groupInvite = "";
        try{
            HttpResponse response = getHttpResponse(url, new HashMap<>(), null , RequestMethod.GET);
            Map<String, Object> reponseMap = getResponseMap(response);
            if (reponseMap.containsKey("groups")){
                List groupDetails = (List) reponseMap.get("groups");
                Map<String, Object> groupDetail = (Map<String, Object>) groupDetails.get(0);
                if (groupDetail.containsKey("link")){
                    return groupDetail.get("link").toString();
                }
            }
        }catch (Exception e){

        }
        return groupInvite;
    }

    private String sendWhatsappLink(String groupInviteLink,String phoneNumber) {
        String url = new StringBuilder(endPoint).append("messages/").toString();
        try{
                WhatsappMessageSentRequest messageSentRequest = new WhatsappMessageSentRequest(phoneNumber,
                        new WhatsappTextPayload(groupInviteLink));
                messageSentRequest.setRecipient_type("individual");
                HttpResponse response = getHttpResponse(url, new HashMap<>(), mapper.writeValueAsString(messageSentRequest) , RequestMethod.POST);
                if (response.getStatusLine().getStatusCode() == 201){
                    return "sent successfully";
                } else
                    return "failed to send invite link to "+phoneNumber;
        }catch (Exception e){

        }
        return "";
    }

    private HttpResponse getHttpResponse(String msgURL, Map<String, String> headersMap, String body, RequestMethod method) {
        HttpResponse response = null;
        try {
            populateHeaders(headersMap);
            switch (method){
                case POST:
                    response = httpClient.post(msgURL, body, headersMap, true);
                    break;
                case GET:
                    response = httpClient.get( msgURL,headersMap, true);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void populateHeaders(Map<String, String> headersMap) {
        headersMap.put("Authorization", token);
    }

    private Map<String, Object> getResponseMap(HttpResponse response){
        String data = httpClient.getResponseContent(response);
        Map<String, Object> responseMap = new HashMap<>();
        try {
            responseMap = mapper.readValue(data, Map.class);
        }catch (Exception e){

        }
        return responseMap;
    }
}
