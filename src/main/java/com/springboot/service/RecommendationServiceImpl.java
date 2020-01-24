package com.springboot.service;

import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.BrandPogCache;
import com.springboot.pojo.PDPSro;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 6:01 PM
 */

@Service
public class RecommendationServiceImpl {

    public List<PDPSro> getRecommendations(String brandName, Integer limit){
        List<PDPSro> pdpSros = new ArrayList<>();
        BrandPogCache brandPogCache = CacheManager.getInstance().getCache(BrandPogCache.class);
        String[] brands = brandName.split(",",-1);
        for (String brand : brands) {
            pdpSros.addAll(Optional.ofNullable(brandPogCache.getBrandPogMap().get(brand)).orElse(new ArrayList<>()));
        }
        pdpSros = pdpSros.stream().filter(x -> x.getCommonProductOfferGroupDTO()!= null).collect(Collectors.toList());
        Collections.shuffle(pdpSros);
        return (pdpSros.size() < limit) ? pdpSros : pdpSros.subList(0,limit);
    }


}
