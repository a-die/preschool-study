package com.preschool.controller;


import com.preschool.pojo.EmailVerification;
import com.preschool.pojo.User;
import com.preschool.service.EmailVerificationService;
import com.preschool.service.UserService;
import com.preschool.utils.UUID_DIY;
import com.preschool.utils.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-12-14
 */
@RestController
@RequestMapping("/email-verification")
public class EmailVerificationController {
    @Autowired
    UserService userService;

    @Autowired
    EmailVerificationService verificationService;

    @PostMapping("/sendEmail")
    /*
        发送验证码
        * @param email
        * @return com.preschool.utils.entity.Result
    */

    public Result sendEmail(String email){
        //先判断该邮箱是否在user表中存在
        Map<String, Object> map = new HashMap<>();
        map.put("email",email);
        List<User> users = userService.listByMap(map);
        if(users.size()<=0)return Result.error(401,"邮箱输入有误");

        //查找当前验证码表中是否有该emdil的数据，如果有，则说明这个code还未过期，不能发送验证
        List<EmailVerification> emailVerifications = verificationService.listByMap(map);
        if (emailVerifications.size()>0)return Result.error(402,"这个code还未过期，不能发送验证");

        return Result.ok(verificationService.sendEmail(email));
    }



    @PostMapping("/checkCode")
    /*
        比对验证码是否正确
        * @param email
    	* @param code
        * @return com.preschool.utils.entity.Result
    */

    public Result checkCode(String email,String code){
        Map<String, Object> map = new HashMap<>();
        map.put("email",email);
        EmailVerification emailVerification = verificationService.listByMap(map).get(0);
        if (emailVerification.getCode().equals(code))return Result.ok("可以修改密码");
        return Result.error("验证码错误");

    }

}

