package com.preschool.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.dao.DiscussPostMapper;
import com.preschool.pojo.CommentUser;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.DiscussPostUser;
import com.preschool.pojo.FavoriteBlog;
import com.preschool.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper,DiscussPost> implements DiscussPostService {
    @Autowired
    DiscussPostMapper discussPostMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    FavoriteBlogServiceImpl favoriteBlogService;
    @Autowired
    SupportBlogServiceImpl supportBlogService;
    @Autowired
    ReplyService replyService;
    @Autowired
    CommentService commentService;
    @Autowired
    FeedbackService feedbackService;
    @Autowired
    ReportingInformationService reportingInformationService;

    @Override

    /**
        * @param subject
    	* @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    public List<DiscussPostUser> queryBySubjectAndBtype(int subject, int btype) {
        return discussPostMapper.queryBySubjectAndBtype(subject,btype);
    }

    @Override
    public Boolean calculateTheSimilarity(String content) {
//        String uri = "http://1.117.51.169:8001/tfidf/";
        String uri = "http://localhost:8000/tfidf/";
//        String url = "http://localhost:8081/discussPost/getBlogByIdentify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("content",content);

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );
        ////System.out.println(response.getBody());
        String result = response.getBody();
        JSONArray jsonArray = JSONArray.parseArray(result);
        for(int i=0;i<jsonArray.size();i++){

            BigDecimal ratio = (BigDecimal) jsonArray.get(i);
            double v = ratio.doubleValue();
            //////System.out.println(ratio);
            if(v>0.9)return false;
        }
        return true;
    }



    @Override
    public int getDiscussPostNum() {
        return discussPostMapper.getDiscussPostNum();
    }

    @Override
    public List<DiscussPostUser> getDiscussPostByColumnId(int columnId) {
        return discussPostMapper.getDiscussPostByColumnId(columnId);
    }

    @Override
    public int getBlogNumByUserId(int userId) {
        return discussPostMapper.getBlogNumByUserId(userId);
    }

    @Override
    public boolean checkLegal(String content) {

        String uri = "http://localhost:8000/checkLegal/";
//        String url = "http://localhost:8081/discussPost/getBlogByIdentify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("content",content);

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );
        String result = response.getBody();
        return result.equals("1");
    }

    /**
        * @param discuss_post
        * @return int
    */
    @Override
    public int addDiscussPost(DiscussPost discuss_post) {
        discuss_post.setType(0);
        discuss_post.setStatus(0);
        discuss_post.setComment_count(0);
        discuss_post.setUp_count(0);
        if(discuss_post.getBtype()!=0)discuss_post.setColumn_id(0);//如果该博文类型不是笔记，则没有专栏分类，设置为0
        int insert = discussPostMapper.insert(discuss_post);
        return insert;
    }





    @Override
    public DiscussPost queryByDiscussPostId(int id) {
        return discussPostMapper.selectById(id);
    }


    /**
        * @param id
        * @return com.preschool.pojo.Discuss_post
    */
    @Override
    public DiscussPostUser queryById(int id) {
        return discussPostMapper.queryById(id);
    }

    @Override
    public DiscussPost queryOneById(int id) {
        return discussPostMapper.selectById(id);
    }


    /**
        * @param discuss_post
        * @return int
    */
    
    @Override
    public int updateOnePost(DiscussPost discuss_post) {
        int i = discussPostMapper.updateById(discuss_post);
        return i;
    }

    /**
        * @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    
    @Override
    public List<DiscussPostUser> queryByBtype(int btype) {
        return discussPostMapper.queryByBtype(btype);
    }

    /**
        * @param btype
        * @return int
    */
    @Override
    public int queryCountByBtype(int btype) {
        return discussPostMapper.queryCountByBtype(btype);
    }

    @Override
    public int queryCommentAndReplyCount(int btype) {
        int c1=discussPostMapper.queryCommentNumByBtype(btype);
        int c2=discussPostMapper.queryReplyNumByBtype(btype);
        return c1+c2;
    }

    /**
        * @param user_id
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */


    
    @Override
    public List<DiscussPost> queryByUserId(int user_id,int identify) {
        return discussPostMapper.queryByUserId(user_id,identify);
    }

    /**
        * @param user_id
    	* @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    
    @Override
    public List<DiscussPost> queryByUserIdAndBtype(int user_id, int btype) {
        return discussPostMapper.queryByUserIdAndBtype(user_id,btype);
    }

    /**
        * @param id
        * @return int
     * 同时还需要删掉点赞表和评论表，以及回复表中,收藏表的对应数据
    */
    @Override
    public int deleteOnePost(int id) {
        //删除点赞表信息
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("discussPostId",id);
        supportBlogService.removeByMap(map);

        //删除收藏表信息
        favoriteBlogService.removeByMap(map);


        //删除举报表信息
        map.remove("discussPostId");
        map.put("reportIdTo",id);
        reportingInformationService.removeByMap(map);


        //找到该博文的所有评论信息
        map.remove("reportIdTo");
        List<CommentUser> commentUsers = commentService.queryAllById(id);
        for (CommentUser commentUser : commentUsers) {
            int comment_id = commentUser.getId();
            map.put("comment_id",comment_id);
            //先删除回复表信息，外键约束
            replyService.removeByMap(map);
            map.remove("comment_id");
            //删除评论表信息
            map.put("discussPostId",id);
            commentService.removeByMap(map);
            map.remove("discussPostId");
        }

        //最后删除该文章
        int i = discussPostMapper.deleteOnePost(id);
        return i;
    }

    /**
        * @param user_id
        * @return int
    */
    
    @Override
    public int deleteUserAllPosts(int user_id) {
        //先获得该用户所有博文

        int i = discussPostMapper.deleteUserAllPosts(user_id);
        return i;
    }

    /**
        * @param user_id
    	* @param btype
        * @return int
    */
    @Override
    public int deleteUserSomePosts(int user_id, int btype) {
        int i = discussPostMapper.deleteUserSomePosts(user_id, btype);
        return i;
    }

    /**
        * @param id
    	* @param column_id
        * @return int
    */
    
    @Override
    public int addPostToColumn(int id, int column_id) {
        int i = discussPostMapper.addPostToColumn(id, column_id);
        return i;
    }

    /**
        * @param page
        * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */
    @Override
    public List<HashMap<String,Object>> getBlogByIdentify(Page<DiscussPost> page, int identify) {
        return discussPostMapper.getBlogByIdentify(page,identify);
    }

    /**
        * @param 
        * @return int
    */
    
    @Override
    public int getBlogNumByIdentify(int identify) {
        return discussPostMapper.getNotAuditNum(identify);
    }

    /**
        * @param title
    	* @param identify
        * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */
    
    @Override
    public List<HashMap<String, Object>> getDiscussPostListByTitle(String title, int identify) {
        return discussPostMapper.getDiscussPostListByTitle(title,identify);
    }

    /**
        * @param id
    	* @param identify
        * @return int
    */
    @Override
    public int setIdentify(int id, int identify) {
        DiscussPost discuss_post = discussPostMapper.selectById(id);
        discuss_post.setId(id);
        discuss_post.setIdentify(identify);
        int i = discussPostMapper.updateById(discuss_post);
        return i;
    }

    @Override
    public int updateBlogType(int id, int type) {
        DiscussPost discuss_post = discussPostMapper.selectById(id);
        discuss_post.setId(id);
        discuss_post.setType(type);
        return discussPostMapper.updateById(discuss_post);
    }

    @Override
    public int queryBySubject(int subject) {
        return discussPostMapper.queryBySubject(subject);
    }

    @Override
    public List<DiscussPost> getDiscussPostCommended(int userId) {
//        String uri = "http://1.117.51.169:8001/getSimilarUser/";
        String uri = "http://localhost:8000/getSimilarUser/";
//        String url = "http://localhost:8081/discussPost/getBlogByIdentify";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Integer> map= new LinkedMultiValueMap<>();
        map.add("userId",userId);

        HttpEntity<MultiValueMap<String, Integer>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );
        ////System.out.println(response.getBody());
        String result = response.getBody();
        JSONArray jsonArray = JSONArray.parseArray(result);
        List<DiscussPost>discussPosts = new ArrayList<>();

        for(int i=0;i<jsonArray.size();i++){
            ////System.out.println(jsonArray.get(i));
            int id = (int) jsonArray.get(i);
            List<FavoriteBlog> favoriteBolgByUserId = favoriteBlogService.getFavoriteBolgByUserId(id);
            for(FavoriteBlog favoriteBlog:favoriteBolgByUserId){
                Integer id1 = favoriteBlog.getDiscussPostId();
                //System.out.println(id1+"文章id");
                DiscussPost discussPost = discussPostMapper.selectById(id1);
                discussPosts.add(discussPost);
            }
        }
        //去重
        discussPosts = discussPosts.stream().distinct().collect(Collectors.toList());

        return discussPosts;

    }



}
