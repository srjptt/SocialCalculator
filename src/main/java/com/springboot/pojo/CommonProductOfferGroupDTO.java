package com.springboot.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonProductOfferGroupDTO {

    private Long id;
    private String title;
    private Integer sellingPrice;
    private Integer percentOff;
    private Double avgRating;
    private Integer noOfRatings;
    private List<Categories> categories;
    private List<String>images;
    private Integer price;
    private Integer groupBuyDiscount;


}
