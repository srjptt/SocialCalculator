package com.springboot.utils;

import com.google.common.base.Strings;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/24/20 1:30 PM
 */
public class SSLIgnoreClient {

    private static SSLIgnoreClient sInstance;
    public static CloseableHttpClient httpClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(SSLIgnoreClient.class);

    private SSLIgnoreClient() {
        super();
    }

    public static SSLIgnoreClient getInstance() {
        if (sInstance == null) {
            synchronized (SSLIgnoreClient.class) {
                if (sInstance == null) {
                    sInstance = new SSLIgnoreClient();
                }
            }
        }
        return sInstance;
    }

    public void init(){
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null,new TrustManager[]{new SSLIgnoreClient.AlwaysTrust()},new java.security.SecureRandom());
            httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    })
                    .setSSLContext(sc)
                    .disableContentCompression()
                    .setConnectionTimeToLive(1L ,TimeUnit.MINUTES)
                    .build();
        }catch (Exception e) {
            LOGGER.error("Problem in initializing ssl http client");
            e.printStackTrace();
        }
    }

    public static HttpResponse httpPost(String url, String body, Map<String, String> headerMap) throws IOException {
        HttpPost request = new HttpPost(url);
        StringEntity params = new StringEntity(body, ContentType.APPLICATION_JSON);
        if (headerMap != null && headerMap.entrySet() !=null ) {
            for (Map.Entry<String, String> en : headerMap.entrySet()) {
                if(!Strings.isNullOrEmpty(en.getKey())) {
                    request.setHeader(en.getKey(), en.getValue());
                }
            }
        }
        request.setEntity(params);
        return httpClient.execute(request);
    }

    static class AlwaysTrust implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
