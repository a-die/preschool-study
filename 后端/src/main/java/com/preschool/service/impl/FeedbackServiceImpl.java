package com.preschool.service.impl;

import com.preschool.pojo.Feedback;
import com.preschool.dao.FeedbackMapper;
import com.preschool.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-11-29
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public List<Map<String, Object>> getFeedBackByState(int state) {
        List<Map<String, Object>> feedBackByState = feedbackMapper.getFeedBackByState(state);
        return feedBackByState;
    }

    @Override
    public int addFeedback(Feedback feedback) {
        int insert = feedbackMapper.insert(feedback);
        return insert;
    }
}
