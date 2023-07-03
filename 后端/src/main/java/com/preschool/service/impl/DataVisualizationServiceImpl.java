package com.preschool.service.impl;

import com.preschool.dao.*;
import com.preschool.pojo.*;
import com.preschool.service.DataVisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class DataVisualizationServiceImpl implements DataVisualizationService {
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ReplyMapper replyMapper;

    //[{name:'基础知识',children:[{name:'离散数学',value:50},{name:'高等数学',value:90},{name:'线性代数',value:100},{name:'概率论',value:50}]},
    //				{name:'技术知识',children:[{name:'Javaee',value:200},{name:'Html',value:300},{name:'JS',value:330},{name:'Vue',value:300}]},
    //				{name:'硬件知识',children:[{name:'电路分析',value:60},{name:'计算机组成原理',value:140},{name:'微型计算机原理',value:150},{name:'嵌入式',value:50}]}]
    @Override
    public List<Hot> getHotData() {
        Hot hot1 =new Hot();
        Hot hot2 =new Hot();
        Hot hot3 =new Hot();
        Hot hot4 =new Hot();
        Hot hot5 =new Hot();
        Hot hot6 =new Hot();
        Hot hot7 =new Hot();
        List<Subject> list;
        List<Hot> data=new ArrayList<>();
        int value;

        List<Children> l1=new ArrayList<>();
        list=subjectMapper.queryByCategory(1);
        for(Subject subject:list){
            Children children = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l1.add(children);
        }
        hot1.setChildren(l1);
        hot1.setName("必修知识");
        data.add(hot1);

        List<Children> l2=new ArrayList<>();
        list=subjectMapper.queryByCategory(2);
        for(Subject subject:list){
            Children children  = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l2.add(children);
        }
        hot2.setChildren(l2);
        hot2.setName("软件先修知识");
        data.add(hot2);

        List<Children> l3=new ArrayList<>();
        list=subjectMapper.queryByCategory(3);
        for(Subject subject:list){
            Children children = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l3.add(children);
        }
        hot3.setChildren(l3);
        hot3.setName("前端技术");
        data.add(hot3);

        List<Children> l4=new ArrayList<>();
        list=subjectMapper.queryByCategory(4);
        for(Subject subject:list){
            Children children = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l4.add(children);
        }
        hot4.setChildren(l4);
        hot4.setName("后端技术");
        data.add(hot4);

        List<Children> l5=new ArrayList<>();
        list=subjectMapper.queryByCategory(5);
        for(Subject subject:list){
            Children children  = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l5.add(children);
        }
        hot5.setChildren(l5);
        hot5.setName("硬件知识");
        data.add(hot5);

        List<Children> l6=new ArrayList<>();
        list=subjectMapper.queryByCategory(6);
        for(Subject subject:list){
            Children children = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l6.add(children);
        }
        hot6.setChildren(l6);
        hot6.setName("大数据知识");
        data.add(hot6);

        List<Children> l7=new ArrayList<>();
        list=subjectMapper.queryByCategory(7);
        for(Subject subject:list){
            Children children = new Children();
            children.setName(subject.getName());
            value=discussPostMapper.queryBySubject(subject.getId());
            children.setValue(value);
            l7.add(children);
        }
        hot7.setChildren(l7);
        hot7.setName("网络安全知识");
        data.add(hot7);

        return data;
    }

    //[
    //  {
    //    "subject": "axios",
    //    "count": 0
    //  },
    //  {
    //    "subject": "vue",
    //    "count": 2
    //  },
    //  {
    //    "subject": "react",
    //    "count": 0
    //  },
    //  {
    //    "subject": "其他",
    //    "count": 0
    //  }
    //]
    @Override
    public List<Rank> getRankData() {
        List<Rank> data=new ArrayList<>();
        List<Subject> list = subjectMapper.selectList(null);
        for(Subject l:list){
            Rank rank=new Rank();
            rank.setSubject(l.getName());
            rank.setCount(discussPostMapper.queryBySubject(l.getId()));
            data.add(rank);
        }
        return data;
    }

    //[
    //  {
    //    "category": "0",
    //    "count": 13
    //  },
    //  {
    //    "category": "1-3",
    //    "count": 4
    //  },
    //  {
    //    "category": "4-6",
    //    "count": 0
    //  },
    //  {
    //    "category": "7-9",
    //    "count": 0
    //  }
    //]
    @Override
    public List<Horizon> getHorizonData() {
        List<Integer> list=new ArrayList<>();
        List<Horizon> data=new ArrayList<>();
        List<User> users = userMapper.selectList(null);
        for(User user:users){
            list.add(discussPostMapper.queryCountByUserId(user.getId()));
        }
        int count;
        int[] c=new int[8];
        Horizon horizon1=new Horizon();
        horizon1.setCategory("0");
        Horizon horizon2=new Horizon();
        horizon2.setCategory("1-3");
        Horizon horizon3=new Horizon();
        horizon3.setCategory("4-6");
        Horizon horizon4=new Horizon();
        horizon4.setCategory("7-9");
        Horizon horizon5=new Horizon();
        horizon5.setCategory("10-12");
        Horizon horizon6=new Horizon();
        horizon6.setCategory("13-15");
        Horizon horizon7=new Horizon();
        horizon7.setCategory("16-18");
        Horizon horizon8=new Horizon();
        horizon8.setCategory("19以上");
        for(int i = 0;i<list.size();i++) {
            count=list.get(i);
            if(count==0){
                c[0]++;
            }
            if(count>0&&count<4){
                c[1]++;
            }
            if(count>3&&count<7){
                c[2]++;
            }
            if(count>6&&count<10){
                c[3]++;
            }
            if(count>9&&count<13){
                c[4]++;
            }
            if(count>12&&count<16){
                c[5]++;
            }
            if(count>15&&count<19){
                c[6]++;
            }
            if(count>=19){
                c[7]++;
            }
        }
        horizon1.setCount(c[0]);
        horizon2.setCount(c[1]);
        horizon3.setCount(c[2]);
        horizon4.setCount(c[3]);
        horizon5.setCount(c[4]);
        horizon6.setCount(c[5]);
        horizon7.setCount(c[6]);
        horizon8.setCount(c[7]);
        data.add(horizon1);
        data.add(horizon2);
        data.add(horizon3);
        data.add(horizon4);
        data.add(horizon5);
        data.add(horizon6);
        data.add(horizon7);
        data.add(horizon8);
        return data;
    }

    //    {category:['1月','2月','3月','4月','5月','6月','7月'],
//        data1:{key:'data1',title:'博文增加量趋势',data:[210,200,120,130,140,160,200]},
//        data2:{key:'data2',title:'评论增加量趋势',data:[300,320,360,300,280,390,330]},
//        data3:{key:'data3',title:'回复增加量趋势',data:[400,500,550,590,600,100,90]}}
    @Override
    public TrendData getTrendData() {
        int flag,count,i,j;
        TrendData trendData=new TrendData();
        List<String> category = new ArrayList<>();
        List<CountPerMonth> countPerMonths = discussPostMapper.queryCountPerMonth();
        List<CountPerMonth> countPerMonths1 = commentMapper.queryCountPerMonth();
        List<CountPerMonth> countPerMonths2 = replyMapper.queryCountPerMonth();
        Trend t1=new Trend();
        Trend t2=new Trend();
        Trend t3=new Trend();
        List<KeyValue> list=new ArrayList<>();
        List<Integer> l1=new ArrayList<>();
        List<Integer> l2=new ArrayList<>();
        List<Integer> l3=new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH )+1;//第一个月从0开始，所以得到月份＋1
        t1.setKey("data1");
        t2.setKey("data2");
        t3.setKey("data3");
        t1.setTitle("博文增加量趋势");
        t2.setTitle("评论增加量趋势");
        t3.setTitle("回复增加量趋势");
        count=0;
        while(count<7){
            count++;
            month--;
            if(month==1)
                month=13;
            list.add(new KeyValue(month,0));
        }
        //从后往前遍历
        //博文
        flag=0;
        for (i=countPerMonths.size()-1;flag<7&&i>=0;i--){
            flag++;
            int m = Integer.parseInt(countPerMonths.get(i).getDate().substring(5));
            for(j=0;j<list.size();j++){
                if(m==list.get(j).getMonth())
                    list.get(j).setValue(countPerMonths.get(i).getCount());
            }
        }
        //添加每月增加量到list是从近月开始，应该倒过来
        for(j=list.size()-1;j>=0;j--){
            l1.add(list.get(j).getValue());
        }
        //从后往前遍历
        //评论
        flag=0;
        for(j=0;j<list.size();j++){
            list.get(j).setValue(0);
        }
        for (i=countPerMonths1.size()-1;flag<7&&i>=0;i--){
            flag++;
            int m = Integer.parseInt(countPerMonths1.get(i).getDate().substring(5));
            for(j=0;j<list.size();j++){
                if(m==list.get(j).getMonth())
                    list.get(j).setValue(countPerMonths1.get(i).getCount());
            }
        }
        for(j=list.size()-1;j>=0;j--){
            l2.add(list.get(j).getValue());
        }
        //从后往前遍历
        //回复
        flag=0;
        for(j=0;j<list.size();j++){
            list.get(j).setValue(0);
        }
        for (i=countPerMonths2.size()-1;flag<7&&i>=0;i--){
            flag++;
            int m = Integer.parseInt(countPerMonths2.get(i).getDate().substring(5));
            for(j=0;j<list.size();j++){
                if(m==list.get(j).getMonth())
                    list.get(j).setValue(countPerMonths2.get(i).getCount());
            }
        }
        for(j=list.size()-1;j>=0;j--){
            l3.add(list.get(j).getValue());
        }
        t1.setData(l1);
        t2.setData(l2);
        t3.setData(l3);
        for(j=list.size()-1;j>=0;j--){
            category.add(list.get(j).getMonth()+"月");
        }
        trendData.setCategory(category);
        trendData.setData1(t1);
        trendData.setData2(t2);
        trendData.setData3(t3);
        return trendData;
    }

    //[
    //  {
    //    "name": "湖南",
    //    "value": 4
    //  },
    //  {
    //    "name": "广州",
    //    "value": 1
    //  },
    //  {
    //    "name": null,
    //    "value": 4
    //  }
    //]
    @Override
    public List<Children> getMapData() {
        return userMapper.queryGroup();
    }

}
