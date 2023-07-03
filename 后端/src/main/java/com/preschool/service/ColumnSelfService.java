package com.preschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.ColumnSelf;
import com.preschool.pojo.DiscussPost;

import java.util.List;

public interface ColumnSelfService extends IService<ColumnSelf> {
    /**
        * @param user_id
        * @return java.util.List<com.preschool.pojo.Column_self>
    */
    List<ColumnSelf> queryByUserId(int user_id);

    /**
        * @param column_id
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPost> queryByColumnId(int column_id);
    
    /**
        * @param column_self
        * @return void
    */
    int addColumn(ColumnSelf column_self);

    /**
        * @param column_self
        * @return void
    */
    int updateColumn(ColumnSelf column_self);

   /**
       * 删除专栏时仅删除专栏
       * @param id
       * @return void
   */
    int deleteColumn(int id);

    /**
        * 删除专栏时仅删除专栏下的博文
        * @param column_id
        * @return void
    */
    int deletePosts(int column_id);

    /**
        * 删除专栏和专栏下的博文
        * @param column_id
        * @return void
    */
    int deleteBoth(int column_id);
}
