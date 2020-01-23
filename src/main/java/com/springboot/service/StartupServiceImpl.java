package com.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 5:42 PM
 */

@Service
public class StartupServiceImpl {

    @Autowired
    private BrandPogCacheServiceImpl brandPogCacheService;

    private void init(){
        brandPogCacheService.fillBrandPogCache();
    }


}
