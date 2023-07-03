package com.preschool.controller;


import com.preschool.pojo.DiscussPost;
import com.preschool.service.DiscussPostService;
import com.preschool.service.SupportBlogService;
import com.preschool.utils.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
@RestController
@RequestMapping("/support-blog")
@CrossOrigin
public class SupportBlogController {
    @Autowired
    SupportBlogService supportBlogService;
    @Autowired
    DiscussPostService discussPostService;

    @PostMapping("/checkSupportStatus")
    public Result checkSupportStatus(int userId, int discussPostId){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("userId",userId);
        columnMap.put("discussPostId",discussPostId);
        if(supportBlogService.listByMap(columnMap).size()>0)return Result.ok("已点赞");
        return Result.error("未点赞");
    }

    //获得某用户被点赞的数量
    @PostMapping("/getSupportNumByUserId")
    public Result getSupportNumByUserId(int userId){
        return Result.ok(supportBlogService.getSupportNumByUserId(userId));
    }
}

