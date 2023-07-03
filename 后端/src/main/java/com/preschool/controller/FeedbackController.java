package com.preschool.controller;


import com.preschool.pojo.Feedback;
import com.preschool.service.FeedbackService;
import com.preschool.utils.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;

    @PostMapping("/getFeedBackByState")
    public List<Map<String,Object>> getFeedBackByState(int state){
        List<Map<String, Object>> feedBackByState = feedbackService.getFeedBackByState(state);
        return feedBackByState;
    }

    @PostMapping("/addFeedback")
    public Result addFeedback(Feedback feedback){
        int i = feedbackService.addFeedback(feedback);
        if(i>=1)return Result.ok("新增反馈记录成功");
        else return Result.error("新增反馈记录失败");
    }

    @PostMapping("/updateFeedback")
    public Result updateFeedback(int id,int state){
        Feedback feedback = feedbackService.getById(id);
        feedback.setState(state);
        if(feedbackService.update(feedback,null))return Result.ok();
        return Result.error();
    }
}

