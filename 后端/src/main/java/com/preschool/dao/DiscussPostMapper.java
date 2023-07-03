package com.preschool.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.CountPerMonth;

import com.preschool.pojo.DiscussPostUser;
import com.preschool.utils.entity.Result;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {
    /**
        * 根据科目类型和博文类型查找一系列相应的博文
        * @param subject
    	* @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPostUser> queryBySubjectAndBtype(int subject, int btype);

    /**
        * 发表博文
        * @param discuss_post
        * @return int
    */
    int addDiscussPost(DiscussPost discuss_post);

    /**
        * 根据某条博文id得到博文数据
        * @param id
        * @return com.preschool.pojo.Discuss_post
    */
    DiscussPostUser queryById(int id);

    /**
        * 修改博客
        * @param discuss_post
        * @return int
    */
    void updateOnePost(DiscussPost discuss_post);

    /**
        * 删除指定的一条博客
        * @param id
        * @return int
    */
    int deleteOnePost(int id);

    /**
        * 获得某博客类型下所有博客
        * @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPostUser> queryByBtype(int btype);

    /**
     * 获得某博客类型下的所有博客数量
     * @param btype
     * @return
     */
    int queryCountByBtype(int btype);

    /**
        * 获得某用户发表的所有博文
        * @param user_id
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPost> queryByUserId(int user_id,int identify);

    /**
     * 获得某用户博客类型下所有博客
     * @param btype
     * @return java.util.List<com.preschool.pojo.Discuss_post>
     */
    List<DiscussPost> queryByUserIdAndBtype(int user_id, int btype);

    /**
        * 获得某学科类型下的所有博客数量
        * @param subject
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    int queryBySubject(int subject);

    /**
        * 删除某用户的所有博文
        * @param user_id
        * @return int
    */
    int deleteUserAllPosts(int user_id);

    /**
        * 删除某用户某博文类型下的所有博文
        * @param user_id
    	* @param btype
        * @return int
    */
    int deleteUserSomePosts(int user_id,int btype);

    /**
        * 删除某专栏下的所有博文
        * @param column_id
        * @return int
    */
   int deleteByColumnId(int column_id);

  /**
      *添加一条博客至某专栏
      * @param id
  	  * @param column_id
      * @return int
  */
   int addPostToColumn(int id,int column_id);

   /**
       * 根据所属专栏id更新博文的所属专栏属性为null
       * @param id
       * @return int
   */
    int updateByColumnId(int id);

    /**
        * 分页查询未审核的博文
        * @param page
        * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */

    List<HashMap<String,Object>> getBlogByIdentify(Page<DiscussPost> page, int identify);


    /**
        * 根据审核情况查找博文的数量
        * @param
        * @return int
    */

    int getNotAuditNum(int identify);

    /**
        * 根据审核和未审核，标题获得模糊查询
        * @param title
    	* @param identify
        * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */

    List<HashMap<String,Object>> getDiscussPostListByTitle(String title,int identify);

    /**
        * 得到用户发表的博文数量
        * @param user_id
        * @return int
    */
    int queryCountByUserId(int user_id);

    /**
        * 按月统计博文数量
        * @param
        * @return java.util.List<com.preschool.pojo.Trend>
    */
    List<CountPerMonth> queryCountPerMonth();

    /**
     * 获得博文总数
     * @return
     */
    int getDiscussPostNum();


    int queryCommentNumByBtype(int btype);

    int queryReplyNumByBtype(int btype);

    List<DiscussPostUser>getDiscussPostByColumnId(int columnId);

    int getBlogNumByUserId(int userId);
}
