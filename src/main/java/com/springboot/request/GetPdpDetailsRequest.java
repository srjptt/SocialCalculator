package com.springboot.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GetPdpDetailsRequest {

    private String responseProtocol;
    private String requestProtocol;

    @Setter
    private Long pogId;

    private String deviceType;
    private String locale;
    private String zone;
    private String pincode;
    private String brandAligned;

    public GetPdpDetailsRequest(long pogId) {
        this.requestProtocol="PROTOCOL_JSON";
        this.responseProtocol="PROTOCOL_JSON";
        this.pogId=pogId;
        this.deviceType="WEB";
        this.locale="en";
        this.zone="";
        this.pincode="";
        this.brandAligned="false";

    }
}
