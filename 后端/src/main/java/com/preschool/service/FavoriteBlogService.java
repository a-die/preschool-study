package com.preschool.service;

import com.preschool.pojo.FavoriteBlog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.FavoriteBlogDiscussPostUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
public interface FavoriteBlogService extends IService<FavoriteBlog> {
    /*
        获得该用户点赞了的所有博文的Id
        * @param null
        * @return
    */
    List<FavoriteBlog>getFavoriteBolgByUserId(int userId);
    /*
        获得该用户点赞了的所有博文的详细信息，其中包括了博文表中的部分信息
        * @param null
        * @return
    */
    List<FavoriteBlogDiscussPostUser>getFavoriteBolgDetailedByUserId(int userId);


}
