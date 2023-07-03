package com.preschool;

import com.alibaba.fastjson.JSON;
import com.preschool.dao.DiscussPostMapper;
import com.preschool.dao.UserMapper;
import com.preschool.pojo.FavoriteBlog;
import com.preschool.pojo.User;
import com.preschool.service.FavoriteBlogService;
import com.preschool.utils.CsvUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PreschoolStudyApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    DiscussPostMapper discussPostMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    FavoriteBlogService favoriteBlogService;
    @Test
    void contextLoads() {
        List<User> userList = new ArrayList<>();
        for(int i=0;i<10;i++){
            User user = new User();
            user.setUsername(i+"0");
            user.setPassword(i+"1");
            userList.add(user);
        }

        String string = JSON.toJSONString(userList);
        //System.out.println(string);
    }

    @Test
    void testGetCsv(){
        String headDataStr = "userId,discussPostId,rating";
        String projectPath = System.getProperty("user.dir");//获取当前目录
        String csvfile = projectPath+"/ratings.csv";
        List<User> users = userMapper.selectList(null);
        List<String> dataList = new ArrayList<>();
        for(User user:users){
            String s = "";
            s += user.getId().toString()+",";
            //得到该用户喜欢的所有文章
            List<FavoriteBlog> favoriteBolgByUserId = favoriteBlogService.getFavoriteBolgByUserId(user.getId());
            for(FavoriteBlog favoriteBlog:favoriteBolgByUserId){
                String temp = s+favoriteBlog.getId().toString()+",1";
                dataList.add(temp);
            }
        }
        CsvUtil.writeToCsv(headDataStr, dataList, csvfile, false);
    }

    @Test
    void testRestTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8081/discussPost/getBlogByIdentify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Integer> map= new LinkedMultiValueMap<>();
        map.add("identify",0);
        map.add("page",1);
        map.add("size",2);

        HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
        //System.out.println(response.getBody());
    }

}
