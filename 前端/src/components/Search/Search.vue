<template>
  <div id="search">

    <div class="head">
      <div class="search1">
        <input v-model="searchContent" id="searchInput" type="text" placeholder="搜索"><button @click="goSearch()" id="searchBtn"> 搜索</button>
      </div>
      <div class="search2">
        <!-- <input v-model="searchContent" id="searchInput" type="text" placeholder="搜索"><button @click="goSearch()" id="searchBtn"> 搜索</button> -->
        <el-select v-model="searchChoice" placeholder="Select">
          <el-option
            v-for="item in searchInput"
            :key="item.id"
            :label="item.value"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
    </div>

    <div class="container">
      <div class="left">
        <h2 v-if="articles.length===0" style="color: var(--light--gray);font-size: 2rem;margin: 2rem;">没有找到文章</h2>
        <img v-if="articles.length===0"  style="width: 95%;height: 65%;" src="@/assets/pagenotfound.png" alt="">
        <Article v-for="(item,index) in articles" :key="item.id" :index="index" :item="item" @click="gotoBlog(item.id)"></Article>
      </div>

      <div class="right">
        <div class="aboutSearch">
          <div class="title">
            相关搜索
          </div>
          <div class="content">
            <span @click="goSearchByAbout('前端发展')">前端发展</span>
            <span @click="goSearchByAbout('前端怎么学')">前端怎么学</span>
            <span @click="goSearchByAbout('Vue的生命周期')">Vue的生命周期</span>
            <span @click="goSearchByAbout('感受')">感受</span>
          </div>
        </div>

        <div class="recommend">
          <div class="title">
            推荐文章
          </div>
          <div class="content">
            <RightArticle  v-for="(item,index) in articles" :index="index" :key="item.id" :article="item"></RightArticle>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Article from '@/components/Search/Article'
import RightArticle from '@/components/Search/RightArticle'

import { ElMessageBox } from 'element-plus'

export default {
  inject: ['reload','load'],
  data(){
    return {
      searchContent: '',
      articles: [
        {
          btype: 1,
          column_id: 0,
          comment_count: 24,
          content: "# 一、盒子模型的概念及组成\n盒子模型：html页面把布局元素看做一个矩形的盒子，也就是一个盛装内容的容器。\n\n盒子模型包括外边距 margin、边框 border、内边距 padding、内容 content\n\nmargin：外边距，与其它盒子的距离\nborder：边框，盒子的边框\npadding：内边距，边框与内容的距离\ncontent：内容，一般设置了宽高\n\n盒子模型如下图\n![Description](https://img-blog.csdnimg.cn/img_convert/b8888fe9de137592c3576280ac84e237.png#pic_center)\n# 二、盒子的大小\n盒子的大小就是盒子的宽高，这个宽高并不是指content的宽和高。默认是包括content、padding、border和margin。\n\n>盒子占用的宽=左外边距 + 左边框 + 左内边距 + 内容的宽 + 右内边距 + 右边框 + 右外边距\n>盒子占用的高=上外边距 + 上边框 + 上内边距 + 内容的高 + 下内边距 + 下边框 + 下外边距\n## 1、标准盒子\n![Description](https://img-blog.csdnimg.cn/img_convert/ebbfdeb266fbfb9a434e94f311e48cb7.png#pic_center)\n在标准盒子中，css设置的width和height是content的宽高。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + border-left + padding-left + width + padding-right + border-right + margin-right\n盒子实际占用的高=margin-top + border-top + padding-top + height + padding-bottom + border-bottom + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+10+10+200+10+10+10=260 px,盒子实际占用的高=10+10+10+200+10+10+10 =260 px\n![Description](https://img-blog.csdnimg.cn/img_convert/93a7b28d1d4dba8e27b604082200ddb1.png)\n## 2、ie盒子模型\n![Description](https://img-blog.csdnimg.cn/img_convert/75720f87592f48efa90d85f35faab231.png#pic_center)\nie盒子比较特殊，就如上图，通过css设置width和height并不是只对content设置，而是对border+padding+content这三个属性设置。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + width + margin-right\n盒子实际占用的高=margin-top + height + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+200+10=220 px,盒子实际占用的高=10+200+10 =220 px\n（因为用不了ie所以没有测试了！）",
          create_time: "2021-12-06T13:02:02.000+00:00",
          id: 4,
          identify: 0,
          status: 0,
          subject: 1,
          title: "网页盒子模型",
          type: 0,
          up_count: 11,
          update_time: "2021-12-06T13:02:02.000+00:00",
          url: null,
          user_id: 985669637,
        },
        {
          btype: 1,
          column_id: 0,
          comment_count: 24,
          content: "# 一、盒子模型的概念及组成\n盒子模型：html页面把布局元素看做一个矩形的盒子，也就是一个盛装内容的容器。\n\n盒子模型包括外边距 margin、边框 border、内边距 padding、内容 content\n\nmargin：外边距，与其它盒子的距离\nborder：边框，盒子的边框\npadding：内边距，边框与内容的距离\ncontent：内容，一般设置了宽高\n\n盒子模型如下图\n![Description](https://img-blog.csdnimg.cn/img_convert/b8888fe9de137592c3576280ac84e237.png#pic_center)\n# 二、盒子的大小\n盒子的大小就是盒子的宽高，这个宽高并不是指content的宽和高。默认是包括content、padding、border和margin。\n\n>盒子占用的宽=左外边距 + 左边框 + 左内边距 + 内容的宽 + 右内边距 + 右边框 + 右外边距\n>盒子占用的高=上外边距 + 上边框 + 上内边距 + 内容的高 + 下内边距 + 下边框 + 下外边距\n## 1、标准盒子\n![Description](https://img-blog.csdnimg.cn/img_convert/ebbfdeb266fbfb9a434e94f311e48cb7.png#pic_center)\n在标准盒子中，css设置的width和height是content的宽高。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + border-left + padding-left + width + padding-right + border-right + margin-right\n盒子实际占用的高=margin-top + border-top + padding-top + height + padding-bottom + border-bottom + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+10+10+200+10+10+10=260 px,盒子实际占用的高=10+10+10+200+10+10+10 =260 px\n![Description](https://img-blog.csdnimg.cn/img_convert/93a7b28d1d4dba8e27b604082200ddb1.png)\n## 2、ie盒子模型\n![Description](https://img-blog.csdnimg.cn/img_convert/75720f87592f48efa90d85f35faab231.png#pic_center)\nie盒子比较特殊，就如上图，通过css设置width和height并不是只对content设置，而是对border+padding+content这三个属性设置。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + width + margin-right\n盒子实际占用的高=margin-top + height + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+200+10=220 px,盒子实际占用的高=10+200+10 =220 px\n（因为用不了ie所以没有测试了！）",
          create_time: "2021-12-06T13:02:02.000+00:00",
          id: 5,
          identify: 0,
          status: 0,
          subject: 1,
          title: "网页盒子模型",
          type: 0,
          up_count: 11,
          update_time: "2021-12-06T13:02:02.000+00:00",
          url: null,
          user_id: 985669637,
        }
      ],
      searchData: '',
      searchChoice: -1,
      searchInput: [
        {
          id: -1,
          value: '文章类型'
        },
        {
          id: 0,
          value: '笔记'
        },
        {
          id: 1,
          value: '踩坑记录'
        },
        {
          id: 2,
          value: '提问'
        },
      ]
    }
  },
  mounted(){
    this.$store.state.searchFlag=false;
    this.searchData=this.$route.params.content;
    this.searchContent=this.searchData;
    this.searchChoice = Number(this.$route.params.btype);
    if(this.searchChoice===-1){
      this.getArticle();
    }else{
      this.getArticleByBtype();
    }
    
  },
  unmounted(){
    this.$store.state.searchFlag=true;
  },
  components: {
    Article,
    RightArticle
  },
  methods: {
    gotoBlog(id){
      this.$router.push({name: 'blog', params:{id: id}});
    },
    async getArticle(){
      let data ={
        'content': this.searchContent
      }
  
      const {data: res} = await this.$http.post('/discussPost/getDiscussPostFuzzy',this.qs.stringify(data));
      this.articles=res.data.filter(item => item.identify===0);
    },
    goSearch(){
      if(this.searchContent===''){
        ElMessageBox.alert('请输入搜索内容','提示消息')
        .catch(() => {
          // catch error
        })
      }else{
        this.$router.push({name: 'search', params:{content: this.searchContent,btype: this.searchChoice}});
      }
    },
    goSearchByAbout(content){
      this.$router.push({name: 'search', params:{content: content,btype: this.searchChoice}});
    },
    async getArticleByBtype(){
      let data ={
        'btype': this.searchChoice,
        'content': this.searchContent
      }
      const {data: res} = await this.$http.post('/discussPost/getDiscussPostFuzzyByBtype',this.qs.stringify(data));
      this.articles=res.data;
    }
  },
  watch: {
    // 监听路由变化
    $route (to, from) {
      // 组件刷新
      this.load();
      this.reload()
    }
  }
}
</script>

<style scoped lang="less">
*{
  padding: 0;
  margin: 0;
}
#search{
  height: 200vh;
}
.head{
  
  background: #fff;
  padding: .5rem;
  display: flex;
  flex-wrap: wrap;
  .search1{
    flex: 7;
    text-align: end;
    margin-right: 1.5rem;
  }
  .search2{
    flex: 3;
    text-align: start;
  }
}

#searchInput{
  background: var(--background);
  width: 60%;
  /* height: 2rem; */
  padding: .65rem;
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  outline: none;
  border: 2px solid var(--background);
  font-size: 1rem;
}

#searchInput:focus{
  border-top: 2px solid var(--light--blue);
  border-bottom: 2px solid var(--light--blue);
  border-left: 2px solid var(--light--blue);
}

#searchBtn{
  /* height: 3rem; */
  padding: .6rem;
  width: 8%;
  font-size: 1rem;
  border: 1px solid var(--light--blue);
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  background: var(--light--blue);
  color: #fff;
}

#searchBtn:hover{
  background: var(--blue);
}

.container{
  
  margin: 1rem auto;
  width: 80%;
  // background: #fff;
  display: flex;
  height: 100vh;
}

.left{
  flex: 6;
  // padding: 1rem;
  // margin-right: 2rem;
}
.right{
  flex: 2;
}

.right>div{
  margin-bottom: 1rem;
}

.left,.right{
  /* position: relative; */
}

.aboutSearch,.recommend{
  background: #fff;
  min-height: 10rem;
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  .content{
    padding: .5rem;
    display: flex;
    justify-content: flex-start;
    flex-wrap: wrap;
    align-items: flex-start;
    span{
      cursor: pointer;
      margin-right: .5rem;
      margin-top: .5rem;
      display: inline-block;
      padding: .3rem;
      background: var(--background);
      border-radius: .2rem;
      border: 1px solid transparent;
    }
    span:hover{
      background: #fff;
      border: 1px solid var(--very-soft-black);
    }
  }
}

.title{
  // background: #fff;
  padding: 1rem;
  border-bottom: 1px solid var(--background);
}
li{
  padding-left: 1rem;
}

</style>