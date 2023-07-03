package com.preschool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SwaggerConfig {

    //配置Swagger的Docket的bean实例
    //.enable是否启动swagger，如果为False，则Swagger不能在浏览器中访问
    @Bean
    public Docket docket(Environment environment){
        //设置要显示的Swagger环境
        Profiles profile= Profiles.of("dev");
        //获取项目的环境：
        //通过environment.acceptsProfiles判断是否处在自己设定的环境当中
        boolean flag = environment.acceptsProfiles(profile);

        System.out.println(flag);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("实体类")
                .enable(flag)
                .select()
                //RequestHandlerSelector，配置要扫描接口的方式
                //basePackage：指定要扫描的包
                .apis(RequestHandlerSelectors.basePackage("com.preschool.controller"))
                //只扫描带有adie的请求
                //.paths(PathSelectors.ant("adie/**"))
                .build();
    }

    //配置swagger信息apiInfo
    private ApiInfo apiInfo(){
        Contact contact = new Contact("a碟", "https://blog.csdn.net/ladiez", "993204079@qq.com");
        return new ApiInfo(
                "preschool-study",
                "Swagger配置",
                "v1.0",
                "https://gitee.com/hnucm/preschool-study", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
                );
    }


    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("A");
    }
    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("B");
    }
    @Bean
    public Docket docket3(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("C");
    }
}