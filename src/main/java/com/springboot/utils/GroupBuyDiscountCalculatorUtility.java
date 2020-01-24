package com.springboot.utils;

import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.POGGroupMappingCache;

public class GroupBuyDiscountCalculatorUtility {

    public static int discountValue(String groupId, int sellingPrice){
        Double discountValue = 0.0;
        POGGroupMappingCache cache = CacheManager.getInstance().getCache(POGGroupMappingCache.class);
        if (cache.getGroupDetailsForGroupBuy().containsKey(groupId) &&
                cache.getGroupDetailsForGroupBuy().get(groupId).size() <= 4){
            discountValue=calculate(sellingPrice, cache.getGroupDetailsForGroupBuy().get(groupId).size());
        }else {
            discountValue=calculate(sellingPrice, 5);
        }
        return discountValue.intValue();

    }
    private static double calculate(int sellingPrice, int userCount){
        if (sellingPrice < 500){
            return (sellingPrice*userCount*1.5)/100;
        }else if(sellingPrice > 2000){
            return (sellingPrice*userCount*0.8/100);
        }else {
            return (sellingPrice*userCount*1.25)/100;
        }
    }

}
