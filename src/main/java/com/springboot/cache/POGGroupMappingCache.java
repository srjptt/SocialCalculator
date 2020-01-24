package com.springboot.cache;

import com.snapdeal.base.annotations.Cache;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Data
@ToString
@Cache(name = "groupBuyCache")
public class POGGroupMappingCache {

   private Map<String, Set<String>> groupDetailsForGroupBuy = new HashMap<>();


}
