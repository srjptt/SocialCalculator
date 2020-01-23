package com.springboot.cache;

import com.snapdeal.base.annotations.Cache;

import java.util.Map;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 4:43 PM
 */

@Cache(name = "bucketGenderCache")
public class BucketToGenderCache {

    private Map<String, String> bucketGenderMap;

}
