package com.springboot.service;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/23/20 4:47 PM
 */

@Service
public class BucketGenderCacheServiceImpl {

    Set<String> mappingLines = FlatFileLoader.loadFlatFile(Constants.FLAT_FILES_PATH + Constants.LABEL1_BUCKET_MAPPING);


}
