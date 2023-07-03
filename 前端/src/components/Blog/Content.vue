<template>
  <div class="content">
    <h1 class="title">{{ article.title }}</h1>

    <div class="information">
      <span class="aboutMsg">
        <span><img :src="article.userUrl" alt=""></span>
        <span>{{article.username}}</span><span>|</span>
        <span>分类:&nbsp;{{type}}</span>
        <span></span><span></span><span></span>
      </span>
      <span class="time">最后修改时间：{{time}}</span>
    </div>

    <div class="message">
      <v-md-preview :text="article.content"></v-md-preview>
    </div>

    <div class="footer">
      <span>
        标签：
        <div class="tag">
          <span>{{ type }}</span>
        </div>
      </span>
    </div>

    
  </div>
</template>

<script>
export default {
  props: {
    article: {
      type: Object,
      defalut: {
        btype: 1,
        column_id: 0,
        comment_count: 0,
        content: "# 一、盒子模型的概念及组成\n盒子模型：html页面把布局元素看做一个矩形的盒子，也就是一个盛装内容的容器。\n\n盒子模型包括外边距 margin、边框 border、内边距 padding、内容 content\n\nmargin：外边距，与其它盒子的距离\nborder：边框，盒子的边框\npadding：内边距，边框与内容的距离\ncontent：内容，一般设置了宽高\n\n盒子模型如下图\n![Description](https://img-blog.csdnimg.cn/img_convert/b8888fe9de137592c3576280ac84e237.png#pic_center)\n# 二、盒子的大小\n盒子的大小就是盒子的宽高，这个宽高并不是指content的宽和高。默认是包括content、padding、border和margin。\n\n>盒子占用的宽=左外边距 + 左边框 + 左内边距 + 内容的宽 + 右内边距 + 右边框 + 右外边距\n>盒子占用的高=上外边距 + 上边框 + 上内边距 + 内容的高 + 下内边距 + 下边框 + 下外边距\n\n## 1、标准盒子\n![Description](https://img-blog.csdnimg.cn/img_convert/ebbfdeb266fbfb9a434e94f311e48cb7.png#pic_center)\n\n在标准盒子中，css设置的width和height是content的宽高。\n这里的width和height指的是css样式中的。\n> 盒子实际占用的宽=margin-left + border-left + padding-left + width + padding-right + border-right + margin-right\n> 盒子实际占用的高=margin-top + border-top + padding-top + height + padding-bottom + border-bottom + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+10+10+200+10+10+10=260 px,盒子实际占用的高=10+10+10+200+10+10+10 =260 px\n\n![Description](https://img-blog.csdnimg.cn/img_convert/93a7b28d1d4dba8e27b604082200ddb1.png)\n\n## 2、ie盒子模型\n![Description](https://img-blog.csdnimg.cn/img_convert/75720f87592f48efa90d85f35faab231.png#pic_center)\n\nie盒子比较特殊，就如上图，通过css设置width和height并不是只对content设置，而是对border+padding+content这三个属性设置。\n这里的width和height指的是css样式中的。\n\n>盒子实际占用的宽=margin-left + width + margin-right\n>盒子实际占用的高=margin-top + height + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+200+10=220 px,盒子实际占用的高=10+200+10 =220 px\n~~（因为用不了ie所以没有测试了!）~~",
        create_time: "2021-12-03T13:49:33.000+00:00",
        id: 1877401621,
        identify: 1,
        status: 0,
        subject: 1,
        title: "网页盒子模型",
        type: 0,
        up_count: 10,
        update_time: "2021-12-03T13:49:33.000+00:00",
        url: null,
        user_id: -1075408895
      }
    }
  },
  data(){
    return {
      watch: 2000,
      remark: 205,
    }
  },
  computed: {
    time(){
      let t = new Date(this.article.update_time);
      let y=t.getFullYear();let m=t.getMonth()+1;let d=t.getDate();
      return y+'-'+m+'-'+d;
    },
    type(){
      if(this.article.btype===0) return '笔记';
      else if(this.article.btype===1) return '踩坑记录';
      else return '提问';
    }
  },
  methods: {
    // 获取博客信息
    async showOnePost(id){
      let data ={
        'id': this.id
      }
      const {data: res} = await this.$http.post('/discussPost/showOnePost' , this.qs.stringify(data));
      this.article=res;
    },
  }
}
</script>

<style scoped lang="less">
.content{
  background: #fff;

  font-size: .9rem;
  color: var(--light-color);

// 居中
  // margin: 1rem auto;
  padding: 1.35rem;

  div{
    margin-top: 2rem;
  }
  .title{
    text-align: start;
    color: #000;
    font-family: serif;
  }

  .footer{
    text-align: start;
    .tag{
      display: inline-block;

      span{
        display: inline-block;
        padding: .2rem;
        border-radius: .4rem;
        font-size: .8rem;
        background: #BCBCBC;
        color: #fff;
        margin-left: .5rem;
      }

      span:hover{
        background: #06B4F4;
      }
    }
  }

}
.information{
  display: flex;
  justify-content: space-between;
  span{
    height: 2rem;
    line-height: 2rem;
    img{
      width: 25px;
      height: 25px;
      border-radius: 50%;
    }
  }
  .aboutMsg{
    flex: 1;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .time{
    flex: 2;
    text-align: end;
  }
}

.message{
  text-align: start;
  font-size: 1rem;
  // height: 500px;
  color: var(--black)
}
</style>