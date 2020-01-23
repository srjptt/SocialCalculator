package com.springboot.request;

import lombok.Data;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 4:04 PM
 */

@Data
public class FeedRequest {

    private String userId;
    private String brandName;
    private String gender;
    private Integer limit;

}
