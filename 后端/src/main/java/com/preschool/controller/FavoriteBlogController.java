package com.preschool.controller;


import com.preschool.pojo.FavoriteBlog;
import com.preschool.service.FavoriteBlogService;
import com.preschool.utils.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
@RequestMapping("/favorite-blog")
@CrossOrigin
public class FavoriteBlogController {
    @Autowired
    FavoriteBlogService favoriteBlogService;

    @PostMapping("/addFavoriteBlog")
    /*
        收藏一篇文章
        * @param favoriteBlog
        * @return com.preschool.utils.entity.Result
    */
    public Result addFavoriteBlog(FavoriteBlog favoriteBlog){

        if(favoriteBlogService.save(favoriteBlog))return Result.ok();
        return Result.error();
    }

    @PostMapping("/deleteFavoriteBlog")
    /*
        取消收藏
        * @param userId
    	* @param discussPostId
        * @return com.preschool.utils.entity.Result
    */
    public Result deleteFavoriteBlog(int userId,int discussPostId){
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("userId",userId);
        columnMap.put("discussPostId",discussPostId);
        if(favoriteBlogService.removeByMap(columnMap))return Result.ok();
        return Result.error();
    }



    @PostMapping("/getFavoriteBolgDetailedByUserId")
    /*
        获得该用户收藏了的所有博文的详细信息，其中包括了博文表中的部分信息
        * @param userId
        * @return com.preschool.utils.entity.Result
    */
    public Result getFavoriteBolgDetailedByUserId(int userId){
        return Result.ok(favoriteBlogService.getFavoriteBolgDetailedByUserId(userId));
    }


}

