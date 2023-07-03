package com.preschool.service;

import com.preschool.pojo.Subject;

import java.util.List;

public interface SubjectService{
    /**
        * @param
        * @return java.util.List<com.preschool.pojo.Subject>
    */
    List<Subject> queryByCategory(int category);

    int getSupportNumByUserId(int userId);
}
