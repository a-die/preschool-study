package com.preschool.service.impl;

import com.preschool.dao.DiscussPostMapper;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.SupportBlog;
import com.preschool.dao.SupportBlogMapper;
import com.preschool.service.SupportBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
@Service
public class SupportBlogServiceImpl extends ServiceImpl<SupportBlogMapper, SupportBlog> implements SupportBlogService {

    @Autowired
    SupportBlogMapper supportBlogMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;
    @Override
    public int getSupportNumByUserId(int userId) {
        //先查出来该用户的博文数量
        int num=0;
        List<DiscussPost> discussPosts = discussPostMapper.queryByUserId(userId,0);
        for (DiscussPost discussPost : discussPosts) {
            int num_per = supportBlogMapper.getSupportNumByDiscussPostId(discussPost.getId());
            num+=num_per;
        }
        return num;
    }
}
