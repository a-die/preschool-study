package com.preschool.service.impl;

import com.preschool.dao.UserMapper;
import com.preschool.pojo.EmailVerification;
import com.preschool.pojo.FavoriteBlog;
import com.preschool.pojo.User;
import com.preschool.service.EmailVerificationService;
import com.preschool.service.FavoriteBlogService;
import com.preschool.service.ReadingNumService;
import com.preschool.service.UserService;
import com.preschool.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ScheduledService {

    @Autowired
    UserService userService;
    @Autowired
    FavoriteBlogService favoriteBlogService;
    @Autowired
    ReadingNumService readingNumService;
    @Autowired
    EmailVerificationService verificationService;
    //在一个特定的时间执行这个方法  ~Timer
    //秒 分 时 日 月 周几
    @Scheduled(cron = "0 5 3 * * ?")
    //每一天的10.03分
    //0 3/5 10,18 * * ？ 每天的10点03和18点03，每隔五分钟执行一次
    public void generateCSV(){
        String headDataStr = "userId,discussPostId,rating";
        //String projectPath = System.getProperty("user.dir");//获取当前目录
        String csvfile = "../基于用户或博文类型的推荐算法/RecommendationAlgorithm/static/ratings.csv";

        List<User> users = userService.list();
        List<String> dataList = new ArrayList<>();
        for(User user:users){
            String s = "";
            s += user.getId().toString()+",";
            //System.out.println(user.getId());
            //得到该用户喜欢的所有文章
            List<FavoriteBlog> favoriteBolgByUserId = favoriteBlogService.getFavoriteBolgByUserId(user.getId());
            for(FavoriteBlog favoriteBlog:favoriteBolgByUserId){
                String temp = s+favoriteBlog.getDiscussPostId().toString()+",1";
                dataList.add(temp);
            }
        }
        CsvUtil.writeToCsv(headDataStr, dataList, csvfile, false);
    }

    //每天的0点0定时清空ReadingNum表
    @Scheduled(cron = "0 0 0 * * ?")
    public void clearReadingNum(){
        readingNumService.remove(null);
    }

    //判断验证码表中的验证码是否过期
    @Scheduled(cron = "0 27/5 2 * * ?")
    public void clearCodeWhereBad(){
        List<EmailVerification> list = verificationService.list();
        Date date = new Date();
        for(EmailVerification emailVerificationService:list){
            Date date1 = emailVerificationService.getCreateTime();
            //System.out.println(date.getTime()-date1.getTime());
            if((date.getTime()-date1.getTime())/1000/60>=5){//超过五分钟，失效
                verificationService.removeById(emailVerificationService);
            }
        }
    }
}