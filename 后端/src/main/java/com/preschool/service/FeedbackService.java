package com.preschool.service;

import com.preschool.pojo.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-11-29
 */
public interface FeedbackService extends IService<Feedback> {
    List<Map<String,Object>> getFeedBackByState(int state);

    int addFeedback(Feedback feedback);
}
