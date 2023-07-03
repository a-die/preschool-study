package com.preschool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.dao.ColumnSelfMapper;
import com.preschool.dao.DiscussPostMapper;
import com.preschool.pojo.ColumnSelf;
import com.preschool.pojo.DiscussPost;
import com.preschool.service.ColumnSelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColumnSelfServiceImpl extends ServiceImpl<ColumnSelfMapper, ColumnSelf>  implements ColumnSelfService {
    @Autowired
    ColumnSelfMapper columnSelfMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;

    /**
        * @param user_id
        * @return java.util.List<com.preschool.pojo.Column_self>
    */
    @Override
    public List<ColumnSelf> queryByUserId(int user_id) {
        return columnSelfMapper.queryByUserId(user_id);
    }

    /**
        * @param column_id
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    @Override
    public List<DiscussPost> queryByColumnId(int column_id) {
        return columnSelfMapper.queryByColumnId(column_id);
    }

    /*
        * @param null
        * @return
    */
    @Override
    public int addColumn(ColumnSelf column_self) {
        int insert = columnSelfMapper.insert(column_self);
        return insert;
    }

    /**
        * @param column_self
        * @return void
    */
    @Override
    public int updateColumn(ColumnSelf column_self) {
        int update = columnSelfMapper.update(column_self, null);
        return update;
    }

    /**
        * @param id
        * @return void
    */
    @Override
    public int deleteColumn(int id) {
        int column_id=id;
        discussPostMapper.updateByColumnId(column_id);//先处理外键约束，将所有专栏id为该id的博文的专栏id全部修改为-1
        return columnSelfMapper.deleteById(id);
    }

    /**
        * @param column_id
        * @return void
    */
    @Override
    public int deletePosts(int column_id) {
        int i = discussPostMapper.updateByColumnId(column_id);
        return i;
    }


    /**
        * @param column_id
        * @return void
    */
    @Override
    public int deleteBoth(int column_id) {
        int id=column_id;
        discussPostMapper.deleteByColumnId(column_id);//先删博文，外键约束
        int i = columnSelfMapper.deleteById(id);
        return i;
    }
}
