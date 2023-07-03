package com.preschool.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preschool.pojo.CommentReply;
import com.preschool.pojo.CountPerMonth;
import com.preschool.pojo.Trend;
import com.preschool.pojo.CommentReplyUser;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyMapper extends BaseMapper<CommentReply> {
    /**
        * 根据所属评论id查询该评论下的所有回复（按回复时间从早到晚排序）
        * @param comment_id
        * @return java.util.List<com.preschool.pojo.Comment_reply>
    */
    List<CommentReplyUser> queryAllById(int comment_id);

    /**
        * 根据评论id删除回复
        * @param comment_id
        * @return void
    */
    void deleteByCommentId(int comment_id);

    /**
     * 按月统计回复数量
     * @param
     * @return java.util.List<com.preschool.pojo.Trend>
     */
    List<CountPerMonth> queryCountPerMonth();
}
