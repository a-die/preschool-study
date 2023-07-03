package com.preschool.dao;

import com.preschool.pojo.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author a碟
 * @since 2021-11-29
 */
@Repository
public interface FeedbackMapper extends BaseMapper<Feedback> {
    List<Map<String,Object>> getFeedBackByState(int state);
}
