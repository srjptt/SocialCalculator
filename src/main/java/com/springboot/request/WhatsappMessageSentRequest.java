package com.springboot.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.pojo.WhatsappTextPayload;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WhatsappMessageSentRequest {
    private String to;
    private String type = "text";
    private Boolean preview_url = true;
    private String recipient_type ;
    private Boolean render_mentions;
    private WhatsappTextPayload text;

    public WhatsappMessageSentRequest(String to, WhatsappTextPayload text) {
        this.to = to;
        this.text = text;
    }


}
