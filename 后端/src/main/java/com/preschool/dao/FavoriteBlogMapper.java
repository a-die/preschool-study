package com.preschool.dao;

import com.preschool.pojo.FavoriteBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preschool.pojo.FavoriteBlogDiscussPostUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
@Repository
public interface FavoriteBlogMapper extends BaseMapper<FavoriteBlog> {
    List<FavoriteBlogDiscussPostUser> getFavoriteBolgDetailedByUserId(int userId);
}
