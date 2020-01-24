package com.springboot.service;

import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.BrandPogCache;
import com.springboot.pojo.CommonProductOfferGroupDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 6:01 PM
 */

@Service
public class RecommendationServiceImpl {

    public List<CommonProductOfferGroupDTO> getRecommendations(String brandName, Integer limit){
        List<CommonProductOfferGroupDTO> pdpSros = new ArrayList<>();
        BrandPogCache brandPogCache = CacheManager.getInstance().getCache(BrandPogCache.class);
        String[] brands = brandName.split(",",-1);
        for (String brand : brands) {
            pdpSros.addAll(Optional.ofNullable(brandPogCache.getBrandPogMap().get(brand)).orElse(new ArrayList<>()));
        }
        pdpSros = pdpSros.stream().filter(Objects::nonNull).collect(Collectors.toList());
        Collections.shuffle(pdpSros);
        return (pdpSros.size() < limit) ? pdpSros : pdpSros.subList(0,limit);
    }


}
