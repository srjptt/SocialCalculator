package com.springboot.cache;

import com.snapdeal.base.annotations.Cache;
import com.springboot.pojo.PDPSro;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 5:11 PM
 */

@Data
@ToString
@Cache(name = "brandPogCache")
public class BrandPogCache {

    private Map<String,List<PDPSro>> brandPogMap;

}
