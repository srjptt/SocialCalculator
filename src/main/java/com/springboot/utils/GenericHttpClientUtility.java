package com.springboot.utils;

import com.google.common.base.Strings;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GenericHttpClientUtility {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericHttpClientUtility.class);
    private static GenericHttpClientUtility sInstance;
    private String userAgent = "Mozilla/5.0";
    private final CloseableHttpClient client = HttpClients.createDefault();
    private CloseableHttpClient sslEnabledHttpClient ;
    private final CookieStore cookieStore = new BasicCookieStore();

    public static GenericHttpClientUtility getInstance() {
        if (sInstance == null) {
            synchronized (GenericHttpClientUtility.class) {
                if (sInstance == null) {
                    sInstance = new GenericHttpClientUtility();
                    sInstance.init();
                }
            }
        }
        return sInstance;
    }

    public HttpResponse get(URI url, boolean sslEnabled) {
        return this.sendGet(url, sslEnabled);
    }

    public HttpResponse get(Map<String, Object> requestParameters, String url, boolean sslEnabled) {
        List<NameValuePair> nameValuePairs = new ArrayList();
        Iterator var4 = requestParameters.keySet().iterator();

        while(var4.hasNext()) {
            String key = (String)var4.next();
            NameValuePair nameValuePair = new BasicNameValuePair(key, String.valueOf(requestParameters.get(key)));
            nameValuePairs.add(nameValuePair);
        }

        URI uri = null;

        try {
            uri = (new URIBuilder(url)).addParameters(nameValuePairs).build();
        } catch (URISyntaxException var7) {

        }

        return sendGet(uri, sslEnabled);
    }

    public HttpResponse post(String url, String body, Map<String, String> headerMap, boolean sslEnabled) throws Exception {
        StringEntity entity = new StringEntity(body, ContentType.APPLICATION_JSON);
        return sendPost(new URI(url), entity, headerMap, sslEnabled);
    }

    public HttpResponse post(URI url, HttpEntity httpEntity, boolean sslEnabled) {
        return sendPost(url, httpEntity, new HashMap<>(), sslEnabled);
    }

    public String getResponseContent(HttpResponse response) {
        String result = null;

        try {
            BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(response.getEntity());
            result = EntityUtils.toString(bufferedHttpEntity);
            response.setEntity(bufferedHttpEntity);
            return result;
        } catch (IOException var7) {
            var7.printStackTrace();
            return result;
        }
    }

    public InputStream getContent(HttpResponse httpResponse) {
        InputStream inputStream = null;

        try {
            inputStream = httpResponse.getEntity().getContent();
            return inputStream;
        } catch (IOException var7) {
            var7.printStackTrace();
            return inputStream;
        }
    }


    private HttpResponse sendGet(URI url, boolean sslEnabled) {
        HttpGet get = new HttpGet(url);
        return execute(get, sslEnabled);
    }

    private HttpResponse sendPost(URI url, HttpEntity httpEntity, Map<String, String> headerMap, boolean sslEnabled) {
        HttpPost post = new HttpPost(url);
        post.setEntity(httpEntity);
        if (headerMap != null && headerMap.entrySet() !=null ) {
            for (Map.Entry<String, String> en : headerMap.entrySet()) {
                if(!Strings.isNullOrEmpty(en.getKey())) {
                    post.setHeader(en.getKey(), en.getValue());
                }
            }
        }
        return execute(post, sslEnabled);
    }

    private synchronized HttpResponse execute(HttpUriRequest request, boolean sslEnabled) {
        request.addHeader(new BasicHeader("User-Agent", this.userAgent));
        request.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(this.cookieStore);
        CloseableHttpResponse response = null;

        try {
           if (sslEnabled){
               response = this.sslEnabledHttpClient.execute(request, context);
           }else {
               response = this.client.execute(request, context);
           }
        } catch (IOException var8) {
            var8.printStackTrace();
        } return response;
    }
    private void init(){
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLS");
            sc.init(null,new TrustManager[]{new GenericHttpClientUtility.AlwaysTrust()},new java.security.SecureRandom());
            this.sslEnabledHttpClient = HttpClients.custom()
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
