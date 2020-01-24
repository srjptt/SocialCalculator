package com.springboot.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PDPSro {

    private CommonProductOfferGroupDTO commonProductOfferGroupDTO;
}
