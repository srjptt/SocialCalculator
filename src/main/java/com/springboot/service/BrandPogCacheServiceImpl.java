package com.springboot.service;

import com.google.common.base.Strings;
import com.snapdeal.base.cache.CacheManager;
import com.springboot.cache.BrandPogCache;
import com.springboot.pojo.CommonProductOfferGroupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 4:47 PM
 */

@Service
 class BrandPogCacheServiceImpl {

    @Value("${brand.pog.file.path}")
    private String filePath;

    @Autowired
    private MobApiMockService mobApiMockService;


     void fillBrandPogCache() {
        BrandPogCache brandPogCache = new BrandPogCache();
        try {
            Set<String> lines = loadFlatFile(filePath);
            Map<String, List<CommonProductOfferGroupDTO>> brandMap = new HashMap<>();
            try {
                for (String line : lines) {
                    String[] split = line.split(",", -1);
                    List<String> pogList = new ArrayList<>(Arrays.asList(split).subList(1, split.length));
                    pogList = pogList.stream().map(String::trim).collect(Collectors.toList());
                    brandMap.put(split[0].toLowerCase().trim(), getRecommendedPogDetail(pogList));
                }
                brandPogCache.setBrandPogMap(brandMap);
                CacheManager.getInstance().setCache(brandPogCache);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<CommonProductOfferGroupDTO> getRecommendedPogDetail(List<String> pogIds){
        return pogIds.stream()
                .map(x -> mobApiMockService.getPdpDetails(x))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Set<String> loadFlatFile(String file) throws IOException {
        Set<String> keys = new LinkedHashSet<>();
        InputStream resource = getTestData(file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource))) {
            String value;
            while ((value = br.readLine()) != null) {
                if (!Strings.isNullOrEmpty(value)) {
                    keys.add(value);
                }
            }
        }
        return Collections.unmodifiableSet(keys);
    }

    private InputStream getTestData(String filePath){
        try {
            ClassPathResource classPathResource = new ClassPathResource(filePath);
            return classPathResource.getInputStream();
        }catch (Exception e){

        }
        return null;
    }

}
