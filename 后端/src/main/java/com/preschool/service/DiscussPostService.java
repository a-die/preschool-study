package com.preschool.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.DiscussPostUser;
import com.preschool.pojo.SupportBlog;

import java.util.HashMap;
import java.util.List;

public interface DiscussPostService extends IService<DiscussPost>{

    /**
     * @param subject
     * @param btype
     * @return java.util.List<com.preschool.pojo.Discuss_post>
     */
    List<DiscussPostUser> queryBySubjectAndBtype(int subject, int btype);

    /**
        * @param discuss_post
        * @return int
    */

    int addDiscussPost(DiscussPost discuss_post);

    DiscussPost queryByDiscussPostId(int id);

    /**
     * @param id
     * @return com.preschool.pojo.Discuss_post
     */
    DiscussPostUser queryById(int id);

    DiscussPost queryOneById(int id);

    /**
        * @param discuss_post
        * @return int
    */

    int updateOnePost(DiscussPost discuss_post);

    /**
        * @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPostUser> queryByBtype(int btype);

    /**
        * @param btype
        * @return int
    */
    int queryCountByBtype(int btype);

    /**
        * @param btype
        * @return int
    */
    int queryCommentAndReplyCount(int btype);

    /**
        * @param user_id
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPost> queryByUserId(int user_id,int identify);

    /**
        * @param user_id
    	* @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    List<DiscussPost> queryByUserIdAndBtype(int user_id, int btype);

    /**
        * @param id
        * @return int
    */

    int deleteOnePost(int id);

    /**
        * @param user_id
        * @return int
    */

    int deleteUserAllPosts(int user_id);

    /**
        * @param user_id
    	* @param btype
        * @return int
    */

    int deleteUserSomePosts(int user_id,int btype);


    /**
        * @param id
    	* @param column_id
        * @return int
    */

    int addPostToColumn(int id,int column_id);

    /**
        * @param page
        * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */

    List<HashMap<String,Object>> getBlogByIdentify(Page<DiscussPost> page, int identify);

    /**
        * @param
        * @return int
    */

    int getBlogNumByIdentify(int identify);

    /**
        * @param title
    	* @param identify
        * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */

    List<HashMap<String,Object>> getDiscussPostListByTitle(String title,int identify);

    /**
        * @param id
    	* @param identify
        * @return int
    */

    int setIdentify(int id,int identify);

    int updateBlogType(int id,int type);

    int queryBySubject(int subject);

    List<DiscussPost> getDiscussPostCommended(int userId);

    Boolean calculateTheSimilarity(String content);

    int getDiscussPostNum();

    List<DiscussPostUser>getDiscussPostByColumnId(int columnId);

    int getBlogNumByUserId(int userId);

    boolean checkLegal(String content);
}
