package com.springboot.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CreateWhatsAppGroupRequest {
    String productName;
    String userName;
    String phoneNumber;
}
