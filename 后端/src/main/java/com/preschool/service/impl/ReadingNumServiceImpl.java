package com.preschool.service.impl;

import com.preschool.pojo.ReadingNum;
import com.preschool.dao.ReadingNumMapper;
import com.preschool.pojo.User;
import com.preschool.service.ReadingNumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-12-12
 */
@Service
public class ReadingNumServiceImpl extends ServiceImpl<ReadingNumMapper, ReadingNum> implements ReadingNumService {
    @Autowired
    ReadingNumMapper readingNumMapper;

    @Override
    /*
        根据userId查找对应的实体类
        * @param userId
        * @return com.preschool.pojo.ReadingNum
    */

    public ReadingNum getByUserId(int userId){
        Map<String,Object> map = new HashMap();
        map.put("userId",userId);
        return readingNumMapper.selectByMap(map).get(0);
    }

}
