package com.springboot.service;

import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.BrandPogCache;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 6:01 PM
 */

@Service
public class RecommendationServiceImpl {

    public List<String> getRecommendations(String brandName, Integer limit){
        List<String> pogs = new ArrayList<>();
        BrandPogCache brandPogCache = CacheManager.getInstance().getCache(BrandPogCache.class);
        String[] brands = brandName.split(",",-1);
        for (String brand : brands) {
            pogs.addAll(Optional.ofNullable(brandPogCache.getBrandPogMap().get(brand)).orElse(new ArrayList<>()));
        }
        Collections.shuffle(pogs);
        return (pogs.size() < limit) ? pogs : pogs.subList(0,limit);
    }
}
