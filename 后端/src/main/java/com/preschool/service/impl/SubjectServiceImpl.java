package com.preschool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.dao.SubjectMapper;
import com.preschool.pojo.Subject;
import com.preschool.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;
    @Override
    public List<Subject> queryByCategory(int category) {
        return subjectMapper.queryByCategory(category);
    }

    @Override
    public int getSupportNumByUserId(int userId) {
        return subjectMapper.getSupportNumByUserId(userId);
    }
}
