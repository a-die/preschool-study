<template>
  <div id="manageColumn">
    <div class="left">
      <button @click="goWhite()">发布文章</button>
      <div class="columns">
        <ul>
          <li v-for="(item,index) in Mycolumns" :key="item.id" :class="columnIndex==item.id?'active':''" @click="changeID(item.id,index)" >{{item.name}}</li>
        </ul>
      </div>
    </div>
    <div class="right">
      <div class="head">
        {{ Mycolumns[cIndex].name }} <span>&gt;&nbsp;管理文章</span>
      </div>
      <div class="doChoice">
        <el-select v-model="doOne" placeholder="请选择博文类型">
          <el-option
            v-for="item in options"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>

        <button @click="apply()" v-if="doOne!=1">应用</button>
      </div>
      <div class="article">
        <p v-if="doOne!==1" style="padding: .5rem;"  >全选&nbsp;&nbsp;<input v-model="allCheck" type="checkbox" @change.stop="checkAllChecks()"></p>
        <ul>
          <li class="EveryArticle" v-for="(item,index) in articles" :key="item.id">
            <div><span v-if="doOne!==1"><input   type="checkbox" :value="item.id" v-model="checkArticle"></span><span v-else>::</span>{{ item.title }}</div>
            <div v-if="doOne===1">
              <span style="color: var(--blue);font-size:.8rem;" @click="$router.push({name: 'blog',params:{id: item.id}})">查看</span>
              <span>|</span>
              <span style="color: var(--red);font-size:.8rem;" @click="deleteArFromColumn(index)">删除</span>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from 'element-plus'
export default {
  data(){
    return {
      Mycolumns: [
        {
          id: 5,
          name: "wawa",
          user_id: 985669637
        }
      ],
      columnIndex: 5,
      doOne: 1,
      options: [
        {
          id: 1,
          name: '批量操作'
        },
        {
          id: 2,
          name: '添加文章'
        }
      ],
      articles: [
        {
          btype: 0,
          column_id: 0,
          comment_count: 2,
          content: "# 一、盒子模型的概念及组成\n盒子模型：html页面把布局元素看做一个矩形的盒子，也就是一个盛装内容的容器。\n\n盒子模型包括外边距 margin、边框 border、内边距 padding、内容 content\n\nmargin：外边距，与其它盒子的距离\nborder：边框，盒子的边框\npadding：内边距，边框与内容的距离\ncontent：内容，一般设置了宽高\n\n盒子模型如下图\n![Description](https://img-blog.csdnimg.cn/img_convert/b8888fe9de137592c3576280ac84e237.png#pic_center)\n# 二、盒子的大小\n盒子的大小就是盒子的宽高，这个宽高并不是指content的宽和高。默认是包括content、padding、border和margin。\n\n>盒子占用的宽=左外边距 + 左边框 + 左内边距 + 内容的宽 + 右内边距 + 右边框 + 右外边距\n>盒子占用的高=上外边距 + 上边框 + 上内边距 + 内容的高 + 下内边距 + 下边框 + 下外边距\n## 1、标准盒子\n![Description](https://img-blog.csdnimg.cn/img_convert/ebbfdeb266fbfb9a434e94f311e48cb7.png#pic_center)\n在标准盒子中，css设置的width和height是content的宽高。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + border-left + padding-left + width + padding-right + border-right + margin-right\n盒子实际占用的高=margin-top + border-top + padding-top + height + padding-bottom + border-bottom + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+10+10+200+10+10+10=260 px,盒子实际占用的高=10+10+10+200+10+10+10 =260 px\n![Description](https://img-blog.csdnimg.cn/img_convert/93a7b28d1d4dba8e27b604082200ddb1.png)\n## 2、ie盒子模型\n![Description](https://img-blog.csdnimg.cn/img_convert/75720f87592f48efa90d85f35faab231.png#pic_center)\nie盒子比较特殊，就如上图，通过css设置width和height并不是只对content设置，而是对border+padding+content这三个属性设置。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + width + margin-right\n盒子实际占用的高=margin-top + height + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+200+10=220 px,盒子实际占用的高=10+200+10 =220 px\n（因为用不了ie所以没有测试了！）",
          create_time: "2021-12-11T02:02:53.000+00:00",
          id: 4,
          identify: 0,
          status: 0,
          subject: 1,
          title: "盒子模型",
          type: 0,
          up_count: 0,
          update_time: "2021-12-11T02:02:53.000+00:00",
          url: null,
          user_id: 985669637,
        }
      ],
      cIndex: 0,
      allCheck: false,
      checkArticle: []
    }
  },
  methods: {
    changeID(id,index){
      this.columnIndex=id;
      this.cIndex=index;
      this.$router.push({name: 'columnManage',params: {column: id}});
    },
    async deleteArFromColumn(index){
      ElMessageBox.confirm(
        '确认删除？',
        '确认消息',
      )
      .then(() => {
        this.deleteAr(index);
      })
      .catch(() => {
        return ;
      })
      // this.articles=res;
    },
    async deleteAr(index){
      let data ={
        'btype': this.articles[index].btype,
        'column_id': -1,
        'comment_count': this.articles[index].comment_count,
        'content': this.articles[index].content,
        'id': this.articles[index].id,
        'identify': this.articles[index].identify,
        'status': this.articles[index].status,
        'subject': this.articles[index].subject,
        'title': this.articles[index].title,
        'type': this.articles[index].type,
        'up_count': this.articles[index].up_count,
        'url': this.articles[index].url,
        'user_id': this.articles[index].user_id,
      }

      const {data: res} = await this.$http.post('/discussPost/updateOnePost' , this.qs.stringify(data));

      if(res.code===0){
        this.getColumnArticle();
        ElMessageBox.alert('删除成功','提示消息')
        .catch(() => {
        })
      }
    },

    async getAllColumns(){
      let data ={
        'id': Number(localStorage.getItem('id'))
      }
      const {data: res} = await this.$http.post('/column-self/showAll' , this.qs.stringify(data));

      for(let i=0;i<res.length;i++) {  if(res[i].id===this.columnIndex) {this.cIndex=i;break;}}
      this.Mycolumns=res;
    },
    async getColumnArticle(){
      let data ={
        'column_id': this.columnIndex,
      }
      const {data: res} = await this.$http.post('/column-self/showPosts' , this.qs.stringify(data));
      this.articles=res;
    },
    async getNoColumnArticles(){
      let data ={
        'id': Number(localStorage.getItem('id')),
        'identify': 0
      }
      const {data: res} = await this.$http.post('/discussPost/showAllPosts' , this.qs.stringify(data));
      this.articles=res.filter(item => item.column_id===-1);
    },
    async apply(){
      if(this.articles.length===0){
        ElMessageBox.alert('没有空闲文章！','提示消息')
        .catch(() => {
        })
      }else if(this.checkArticle.length===0){
        ElMessageBox.alert('请选择文章','提示消息')
        .catch(() => {
        })
      }else{
        let data ={
          'articles': JSON.stringify(this.checkArticle),
          'columnId': this.columnIndex
        }
        const {data: res} = await this.$http.post('/column-self/addArticlesForColumn' , this.qs.stringify(data));
        if(res.code===0) {
          this.getNoColumnArticles();
          ElMessageBox.alert('添加成功','提示消息')
          .catch(() => {
          })
        }
        this.allCheck=false;
        this.checkArticle=[];
      }
    },
    checkAllChecks(){
      if(this.allCheck){
        this.checkArticle = this.articles.map(item => item.id);
      }else{
        this.checkArticle = []
      }
    },
    goWhite(){
      this.$router.push({name: 'write'});
    }
  },
  mounted(){
    this.columnIndex=Number(this.$route.params.column);
    this.getAllColumns();
    this.getColumnArticle();
  },
  watch: {
    doOne(newVal){
      if(newVal===1){
        this.getColumnArticle();
      }else if(newVal===2){
        this.getNoColumnArticles();
      }
      else{
        this.getColumnArticle();
      }
    },
    columnIndex(){
      this.doOne=1;
      this.getColumnArticle();
    },
    checkArticle(){
      if(this.checkArticle.length !== this.articles.length){
        this.allCheck=false;
      }else{
        this.allCheck=true;
      }
    }
  }
}
</script>

<style scoped>
#manageColumn{
  padding: 1rem;
  display: flex;
}
.active{
  color: var(--blue);
}
.left{
  padding: 1rem;
  flex: 2;
  margin-right: 1rem;
  min-height: 80vh;
}
button{
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  height: 2.5rem;
  width: 15rem;
  padding: .5rem;
  border: 1px solid transparent;
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  background: var(--blue);
  color: #fff;
  border-end-end-radius: .2rem;
  border-start-end-radius: .2rem;
  border-radius: 1.7rem;
}

button:hover{
  background: var(--light--blue);
}
.columns{
  margin-top: 2rem;
}
.columns ul>li{
  cursor: pointer;
  font-size: 1.2rem;
  padding: .3rem 0;
}
.columns ul>li.active{
  color: var(--light--blue);
  font-weight: 500;
}
.columns ul>li:hover{
  background: var(--light--gray);
}

.right{
  background: #fff;
  flex: 8;
  padding: 1rem;
  text-align: start;
}
.right .head{
  color: var(--black);
  font-weight: 500;
  font-size: 1.5rem;
}
.right .head span{
  color: var(--very-soft-black);
  font-weight: 400;
  font-size: .9rem;
}

.right .doChoice{
  margin-top: 1rem;
  background: var(--background);
  padding: 1rem;
}

.right .doChoice>button{
  margin-left: 2rem;
  border-radius: .4rem;
  font-weight: 400;
  font-size: .9rem;
  background: #fff;
  border: 1px solid var(--blue);
  width: 5rem;
  color: var(--blue);
}

.right .doChoice>button:hover{
  background: var(--blue);
  color: #fff;
}

.EveryArticle{
  padding: 1.5rem;
  cursor: pointer;
  display: flex;
  justify-content: space-between;
  border-bottom: 1px dashed var(--light--gray);
}
.EveryArticle:hover{
  background: var(--hover-back);
}

.EveryArticle span{
  margin-right: 1rem;
}

</style>