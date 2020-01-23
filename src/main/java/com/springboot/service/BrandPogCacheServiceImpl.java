package com.springboot.service;

import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.BrandPogCache;
import jdk.internal.joptsimple.internal.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 4:47 PM
 */

@Service
 class BrandPogCacheServiceImpl {

    @Value("${brand.pog.file.path}")
    private String filePath;


     void fillBrandPogCache() {
        BrandPogCache brandPogCache = new BrandPogCache();
        try {
            Set<String> lines = loadFlatFile(filePath);
            Map<String, List<String>> brandMap = new HashMap<>();
            try {
                for (String line : lines) {

                    String[] split = line.split(",", -1);
                    List<String> pogList = new ArrayList<>();
                    String[] split1 = split[1].split("", -1);
                    Collections.addAll(pogList, split1);
                    brandMap.put(split[0], pogList);
                }

                brandPogCache.setBrandPogMap(brandMap);
                CacheManager.getInstance().setCache(brandPogCache);
            } catch (Exception e) {
                // error
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Set<String> loadFlatFile(String path) throws IOException {
        Set<String> keys = new LinkedHashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String value;
            while ((value = br.readLine()) != null) {
                if (!Strings.isNullOrEmpty(value)) {
                    keys.add(value);
                }
            }
        }
        return Collections.unmodifiableSet(keys);
    }


}
