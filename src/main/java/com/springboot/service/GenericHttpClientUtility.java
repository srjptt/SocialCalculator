package com.springboot.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class GenericHttpClientUtility {
    private String userAgent = "Mozilla/5.0";
    private URI url;
    private final CloseableHttpClient client = HttpClients.createDefault();
    private final CookieStore cookieStore = new BasicCookieStore();
    private static final Map<HttpResponse, String> responseCache = new HashMap();


    public HttpClient getClient() {
        return this.client;
    }

    public CookieStore getCookieStore() {
        return this.cookieStore;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    public void setUserAgent(String userAgent) {

        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public static Map<HttpResponse, String> getResponseCache() {
        return Collections.unmodifiableMap(responseCache);
    }

    public HttpResponse get(URI url) {
        return this.sendGet(url);
    }

    public HttpResponse get() {
        return this.sendGet(this.url);
    }

    public HttpResponse get(Map<String, Object> requestParameters, String url) {
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

        return sendGet(uri);
    }

    public HttpResponse post(URI url, HttpEntity httpEntity) {
        return sendPost(url, httpEntity);
    }

    public HttpResponse post(HttpEntity httpEntity) {
        return this.sendPost(this.url, httpEntity);
    }

    private HttpResponse sendGet(URI url) {
        HttpGet get = new HttpGet(url);
        return execute(get);
    }

    private HttpResponse sendPost(URI url, HttpEntity httpEntity) {
        HttpPost post = new HttpPost(url);
        post.setEntity(httpEntity);
        String postParameters = null;

        try {
            postParameters = EntityUtils.toString(httpEntity);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return execute(post);
    }

    private String logResponse(HttpResponse response) {
        String result = this.getResponseContent(response);
        responseCache.put(response, result);
        return result;
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
        } finally {

        }
    }

    private final synchronized HttpResponse execute(HttpUriRequest request) {
        request.addHeader(new BasicHeader("User-Agent", this.userAgent));
        request.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(this.cookieStore);
        CloseableHttpResponse response = null;

        try {
            response = this.client.execute(request, context);
        } catch (IOException var8) {
            var8.printStackTrace();
        } finally {
            if (response != null) {
            }

            this.logResponse(response);
            return response;
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
        } finally {
            ;
        }
    }
}
