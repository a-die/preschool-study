package com.preschool.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.Subject;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectMapper extends BaseMapper<Subject> {
    List<Subject> queryByCategory(int category);

    int getSupportNumByUserId(int userId);


}
