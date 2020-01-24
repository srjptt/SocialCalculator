package com.springboot.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateWhatsAppGroupRequest {
    private String subject;

  /*  public String toJSONString() {
        return JsonUtility.getInstance().toJson(this);
    }*/
}
