package com.preschool.controller;


import com.preschool.pojo.ReadingNum;
import com.preschool.pojo.User;
import com.preschool.service.ReadingNumService;
import com.preschool.service.UserService;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/reading-num")
public class ReadingNumController {
    @Autowired
    ReadingNumService readingNumService;
    @Autowired
    UserService userService;


    @PostMapping("/addReadingNum")
    @ApiOperation("用户访问文章之后阅读量+1")
    @Transient
    public Result addReadingNum(int userId){
        ReadingNum temp = readingNumService.getByUserId(userId);
        if(temp==null){//如果为空，说明该用户该日第一次阅读，需要添加一个
            ReadingNum readingNum = new ReadingNum();
            readingNum.setNum(1);
            readingNum.setUserId(userId);
            if(readingNumService.save(readingNum))return Result.ok();
            return Result.error();
        }
        else{//如果不为空，则该用户该日已经阅读过了，不需要添加，直接对阅读量修改
            temp.setNum(temp.getNum()+1);
            if (temp.getNum()==10){//只要阅读量达到了10，就增加CC币，因为每天只能增加一次CC币，所以判断是否等于10
                User user = userService.queryById(userId);
                user.setCcNum(user.getCcNum()+5);
                userService.updateById(user);
            }
            if(readingNumService.updateById(temp)) return Result.ok();
            return Result.error();
        }
    }

}

