package com.liu.orderservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@Component()
public class RestTemplateUtil {

    @Autowired
    private RestOperations restTemplate;
    /**
     * @param req
     * @param url
     * @param method
     * @param params maybe null
     * @return
     */
    private ResponseEntity<String> request(ServletRequest req, String url, HttpMethod method, Map<String, ?> params) {
        HttpServletRequest request = (HttpServletRequest) req;
        //获取header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        //获取parameter信息
        if(params == null) {
            params = request.getParameterMap();
        }

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity<String> rss = restTemplate.exchange(url, method, requestEntity, String.class, params);
        return rss;
    }

    /**
     * restTemplate
     * @param req
     * @param url
     * @param params
     * @return
     */
    public String post(String url,Map<String, ?> params,ServletRequest req) {
        HttpServletRequest request = (HttpServletRequest) req;
        //拿到header信息
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            requestHeaders.add(key, value);
        }
        HttpEntity<Map<String, ?>> requestEntity = new HttpEntity<Map<String, ?>>(params, requestHeaders);
        String result = restTemplate.postForObject(url, requestEntity, String.class);
        return result;
    }
    public String post(String url, Map<String, ?> params,HttpHeaders headers) {
        HttpEntity<Map<String, ?>> requestEntity = new HttpEntity<Map<String, ?>>(params, headers);
        String result = restTemplate.postForObject(url, requestEntity, String.class);
        return result;
    }
}