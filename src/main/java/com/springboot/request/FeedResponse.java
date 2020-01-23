package com.springboot.request;

import com.snapdeal.base.model.common.ServiceResponse;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 6:32 PM
 */

@Data
@ToString
public class FeedResponse extends ServiceResponse {

    private List<String> pogList;
}
