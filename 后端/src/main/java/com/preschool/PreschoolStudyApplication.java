package com.preschool;

import com.preschool.controller.WebSocketController;
import com.preschool.service.DiscussPostService;
import com.preschool.service.SubjectService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

//启动类，将进行自动扫描
@SpringBootApplication
@EnableScheduling //开启定时功能的注解
public class PreschoolStudyApplication {

    public static void main(String[] args) {

        ApplicationContext applicationContext=SpringApplication.run(PreschoolStudyApplication.class, args);

        //既然SpringBoot在运行时无法为WebSocket注入Bean，那么可以在SpringBoot启动时手动注入Bean，并且该Bean应该是static的
        //首先在SpringBoot初始化时获取ApplicationContext
        //通过applicationContext.getBean(Bean.class)来获取Spring IOC容器中的Bean
        //将获取到的Bean通过静态方法注入到WebSocktServer
        SubjectService subjectService = applicationContext.getBean(SubjectService.class);
        DiscussPostService discussPostService=applicationContext.getBean(DiscussPostService.class);
        WebSocketController.setService(subjectService,discussPostService);
    }

}
