package com.preschool.dao;

import com.preschool.pojo.SupportBlog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
@Repository
public interface SupportBlogMapper extends BaseMapper<SupportBlog> {
    int getSupportNumByUserId(int userId);

    int getSupportNumByDiscussPostId(int DiscussPostId);
}
