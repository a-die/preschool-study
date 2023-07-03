package com.preschool.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/test")
    public String test(){
        return "环境无误";
    }

    @GetMapping("/random")
    public String randomSentence(){
        String uri = "https://api.xygeng.cn/one";
//        String url = "http://localhost:8081/discussPost/getBlogByIdentify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //MultiValueMap<String, String> map= new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );
        String result = response.getBody();
        //JSONArray jsonArray = JSONArray.parseArray(result);

        return result;
    }
}
