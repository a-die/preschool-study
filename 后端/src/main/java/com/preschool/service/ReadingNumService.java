package com.preschool.service;

import com.preschool.pojo.ReadingNum;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-12-12
 */
public interface ReadingNumService extends IService<ReadingNum> {
    public ReadingNum getByUserId(int userId);
}
