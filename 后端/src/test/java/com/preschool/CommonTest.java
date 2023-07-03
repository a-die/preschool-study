package com.preschool;

import com.alibaba.fastjson.JSONArray;
import com.preschool.service.impl.ScheduledService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class CommonTest {

    @Test
    void testRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8000/checkLegal/";
//        String url = "http://localhost:8081/discussPost/getBlogByIdentify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("content","傻逼，你是不是很厉害");

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );
        String result = response.getBody();
    }

    @Test
    void testScheduled(){
        ScheduledService scheduledService = new ScheduledService();
        scheduledService.clearReadingNum();
    }
}
