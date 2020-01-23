package com.springboot.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class GetPdpDetailsRequest {

    private String responseProtocol;
    private String requestProtocol;

    @Setter
    private String pogId;

    @Setter
    private String deviceType;

    @Setter
    private String locale;

    @Setter
    private String zone;

    @Setter
    private String pincode;

    @Setter
    private String brandAligned;

    public GetPdpDetailsRequest() {
        this.requestProtocol="PROTOCOL_JSON";
        this.responseProtocol="PROTOCOL_JSON";
    }
}
