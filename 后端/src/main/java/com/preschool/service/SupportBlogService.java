package com.preschool.service;

import com.preschool.pojo.SupportBlog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
public interface SupportBlogService extends IService<SupportBlog> {
    int getSupportNumByUserId(int userId);
}
