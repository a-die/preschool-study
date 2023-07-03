package com.preschool.service.impl;

import com.preschool.pojo.FavoriteBlog;
import com.preschool.dao.FavoriteBlogMapper;
import com.preschool.pojo.FavoriteBlogDiscussPostUser;
import com.preschool.service.FavoriteBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
@Service
public class FavoriteBlogServiceImpl extends ServiceImpl<FavoriteBlogMapper, FavoriteBlog> implements FavoriteBlogService {

    @Autowired
    FavoriteBlogMapper favoriteBlogMapper;

    @Override
    public List<FavoriteBlog> getFavoriteBolgByUserId(int userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        List<FavoriteBlog> favoriteBlogs = favoriteBlogMapper.selectByMap(map);
        return favoriteBlogs;
    }

    @Override
    public List<FavoriteBlogDiscussPostUser> getFavoriteBolgDetailedByUserId(int userId) {
        return favoriteBlogMapper.getFavoriteBolgDetailedByUserId(userId);
    }
}
