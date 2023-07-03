package com.preschool.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preschool.pojo.ColumnSelf;
import com.preschool.pojo.DiscussPost;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColumnSelfMapper extends BaseMapper<ColumnSelf> {
    /**
        * 通过用户id查出某用户下的所有专栏
        * @param user_id
        * @return java.util.List<com.preschool.pojo.Column_self>
    */
    List<ColumnSelf> queryByUserId(int user_id);

    /**
        * 通过专栏id查询该专栏下所有博文
        * @param column_id
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPost> queryByColumnId(int column_id);
}
