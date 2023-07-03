<template>
  <div id="blog" >
    <div id="left-use">
      <Content :article="article" ></Content>
      <Comment :blogOwn="user_id" :commentLength="article.comment_count" ></Comment>
    </div>
    <RightUse :commentNum="article.comment_count" :supportNum="article.up_count"  class="fixed" :blogID="id" :userId="user_id"></RightUse>
    <div class="do" v-if="article.identify===0">
      <ul>
        <li :class="{'prase': praseFlag}" @click="praseChange()">
          <i class="fas fa-thumbs-up"></i>
          <span v-if="article.up_count>0">{{ article.up_count }}</span>
        </li>
        <li @click="jump()">
          <i class="fas fa-comment-alt"></i>
          <span v-if="article.comment_count>0">{{ article.comment_count }}</span>
        </li>
        <li :class="{'star': starFlag}" @click="starChange()">
          <i class="fas fa-star"></i>
        </li>
        <li @click="showReport()">
          <i class="fas fa-exclamation-triangle"></i>
        </li>
      </ul>

      <span class="divide">分享</span>

      <ul>
        <li>
          <i class="fab fa-qq"></i>
        </li>
        <li>
          <i class="fab fa-weixin"></i>
        </li>
        <li>
          <i class="fab fa-weibo"></i>
        </li>
      </ul>

    </div>

    <div class="report" v-if="reportShow">
      <div class="reportContainer">
        <span class="close" @click="showReport()"><i class="fas fa-times"></i></span>
        <div class="title">举报反馈</div>
        <div>
          <p style="text-align: start;color: var(--light-color);">举报类型(必填)</p>
          <div class="reportType">
            <span v-for="(item,index) in reportReason" :key="item.id" :class="{'active':reportType===item.id}" @click="changeType(index,item.id)">{{ item.msg }}</span>
          </div>
        </div>
        <div>
          <p style="text-align: start;color: var(--light-color);">举报详情(必填)</p>
          <textarea v-model.trim="reportInformation" class="go-remark" name="" cols="20" rows="5" placeholder="请输入评论"></textarea>
        </div>
        <div>
          <button @click="addReport()">确认提交</button>
            <button class="backHome"  @click="showReport()">取消</button>
        </div>
      </div>
      <div class="back">
      </div>
    </div>
  </div>
</template>

<script>
// 评论组件
import Comment from '@/components/Blog/Comment'
// 博文内容
import Content from '@/components/Blog/Content';

import RightUse from '@/components/Blog/RightUse';  

import { ElMessageBox, ElMessage } from 'element-plus'


export default {
  inject: ['load'],
  provide() {
    return {
      refreshBlog: this.refresh,
      report: this.showReport
    }
  },
  data(){
    return {
      id: 0,
      user_id_from: 0, // 保存在 localstorage 内的用户信息
      discuss_post: {},
      comments: [1,2,3],
      replys: [],
      content: '', // 评论的内容
      praseFlag: false, // 点赞博客
      starFlag: false, // 收藏博客
      article: {
      },
      user_id: 0,
      reportType: 0,
      reportIndex: -1,
      reportReason: [
        {
          id: 1,
          msg: '样式问题'
        },
        {
          id: 2,
          msg: '侮辱谩骂'
        },
        {
          id: 3,
          msg: '涉嫌广告'
        },
        {
          id: 4,
          msg: '内容抄袭'
        },
        {
          id: 5,
          msg: '政治相关'
        },
        {
          id: 6,
          msg: '内容涉黄'
        },
        {
          id: 7,
          msg: '内容侵权'
        },
        {
          id: 8,
          msg: '其他'
        },
      ],
      reportShow: false,
      reportInformation: ''
    }
  },
  computed: {
  },
  created(){
    setTimeout(()=>{
      this.id=this.$route.params.id;
      // 获取博客的相关信息
      this.showOnePost(this.id);
      this.checkThumbs();
      if(localStorage.getItem('id')!=undefined&&localStorage.getItem('id')!=null){
        this.starGet();
      }
    },1000);
    this.load();
    

  },
  beforeCreate(){
  },
  methods: {
    // 获取博客信息
    async showOnePost(){
      let data ={
        'id': this.id
      }
      const {data: res} = await this.$http.post('/discussPost/showOnePost' , this.qs.stringify(data));
      this.article=res;
      this.user_id = res.user_id;
    },
    // 获取评论信息
    async showComments(discuss_post_id){
      const {data: res} = await this.$http.get('/showComments' , {
        params: {discuss_post_id: discuss_post_id}
      });
    },
    async send(){
      let data ={
        'id': this.id
      }
      const {data: res} = await this.$http.post('/discussPost/showOnePost' , this.qs.stringify(data));
      this.article=res;
      this.user_id = res.user_id;
    },

//  点赞
    async praseChange(){
      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')==null){
        ElMessageBox.alert('请先登录！','提示消息')
        .catch(() => {
        })
      }else if(this.comment=='') {
        ElMessageBox.alert('请输入内容','提示消息')
        .catch(() => {
        })
      }
      else if(!this.praseFlag){
        this.praseFlag=!this.praseFlag;
        let data ={
          'discussPostId': this.id,
          'userId': Number(localStorage.getItem('id')),
          'op': 1
        }
        const {data: res} = await this.$http.post('/discussPost/updateUpNum',this.qs.stringify(data));
        this.article.up_count+=1;
      }else{
        let data ={
          'discussPostId': this.id,
          'userId': Number(localStorage.getItem('id')),
          'op': -1
        }
        const {data: res} = await this.$http.post('/discussPost/updateUpNum',this.qs.stringify(data));
        this.praseFlag=!this.praseFlag;
        this.article.up_count-=1;
      }
    },

//  收藏
    async starChange(){
      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')==null){
        ElMessageBox.alert('请先登录！','提示消息')
        .catch(() => {
        })
      }else if(!this.starFlag){
        ElMessageBox.confirm(
        '确认收藏该文章？',
        '确认消息',
        )
        .then(() => {
          this.collectBlog();
        })
        .catch(() => {
          return ;
        })
      }else{
        ElMessageBox.confirm(
        '确认取消收藏该文章？',
        '确认消息',
        )
        .then(() => {
          this.cancleBlog();
        })
        .catch(() => {
          return ;
        })
        
      }
    },
    async collectBlog(){
      this.starFlag=!this.starFlag;
      let data ={
        'discussPostId': this.id,
        'userId': Number(localStorage.getItem('id')),
      }
      const {data: res} = await this.$http.post('/favorite-blog/addFavoriteBlog',this.qs.stringify(data));
    },
    async cancleBlog(){
      let data ={
        'discussPostId': this.id,
        'userId': Number(localStorage.getItem('id')),
      }
      const {data: res} = await this.$http.post('/favorite-blog/deleteFavoriteBlog',this.qs.stringify(data));
      this.starFlag=!this.starFlag;
    },
    async starGet(){
      let data ={
        'userId': Number(localStorage.getItem('id')),
      }
      const {data: res} = await this.$http.post('/favorite-blog/getFavoriteBolgDetailedByUserId',this.qs.stringify(data));
      if(res.data.filter(item => item.discussPostId===this.article.id).length!=0){
        this.starFlag=true;
      }
    },
    async checkThumbs(){
      let data ={
          'discussPostId': this.id,
          'userId': Number(localStorage.getItem('id'))
        }
        const {data: res} = await this.$http.post('/support-blog/checkSupportStatus',this.qs.stringify(data));
        if(res.code===0) this.praseFlag=true;
    },

    jump(){
      var anchor = this.$el.querySelector('.go-remark');
      if(anchor) {
        setTimeout(()=>{
          anchor.scrollIntoView({
            behavior: "smooth",  
            block:    "center"  
          }); 
        },50);
      } 
    },
    refresh(){
      this.showOnePost();
    },
    open(){
      ElMessageBox.alert('This is a message', 'Title', {
        confirmButtonText: 'OK',
      });
    },
    changeType(index,n){
      this.reportType=n;
      this.reportIndex=index;
    },
    showReport(){
      this.reportShow=!this.reportShow;
    },
    async addReport(){
      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')==null){
        this.reportShow=!this.reportShow;
        this.open('请先登录');
        return ; 
      }else if(this.reportInformation===''||this.reportIndex===-1){
        this.reportShow=!this.reportShow;
        this.open();return;
      }
      let data ={
        reportIdFrom: Number(localStorage.getItem('id')),
        reportIdTo: this.article.id,
        reportReason: this.reportReason[this.reportIndex].msg,
        reportInformation: this.reportInformation
      }
      const {data: res} = await this.$http.post('/reporting/addReport',this.qs.stringify(data));
      if(res.code===0){
        this.reportShow=false;
        this.reportType=0;
        this.reportIndex=0;
        this.reportInformation='';
        ElMessageBox.alert('感谢你为社区和谐做的贡献！','提示消息')
        .catch(() => {
        })
      }else{
        ElMessageBox.alert('举报失败！','提示消息')
        .catch(() => {
        })
      }
    }
    

  },
  components: {
    Comment,
    Content,
    RightUse
  },
}
</script>

<style scoped lang="less">
#blog{
    width: 80%;
    margin: 20px auto;
    position: relative;
    
    display: flex;
    justify-content: space-between;

    #left-use{
      width: 74%;
    }

    .do{
      width: 40px;
      height: 40px;
      padding: .4rem;
      position: fixed;
      top: 10rem;left: 8rem;
      transform: translateX(-120%);
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: space-around;
      font-size: .87rem;
      text-align: center;

      .divide{
        margin-bottom: .8rem;
        margin-top: .8rem;
        color: var(--gray);
        font-size: .9rem;
      }

      ul li{
        width: 38px;
        height: 38px;
        background: #fff;
        border-radius: 50%;
        margin-bottom: .5rem;
        line-height: 38px;
        color: var(--light--gray);
        position: relative;
        span{
          position: absolute;
          top: 0;
          left: 75%;
          padding: 0.1rem 0.4rem;
          font-size: 1rem;
          font-weight: 700;
          text-align: center;
          line-height: 1;
          white-space: nowrap;
          color: #fff;
          background-color: var(--light--gray);
          border-radius: 0.7rem;
          transform-origin: left top;
          transform: scale(.6);
        }
        a{
          display: inline-block;
          position: absolute;
          z-index: 1000 !important;
          top: 0;
          left: 0;
          width: 100%;
          height: 100%;
        }
      }

      ul li:hover{
        color: #545454;
      }

      ul li.prase{
        color: rgb(108, 189, 69);
        span{
          background: rgb(108, 189, 69);
        }
      }
      ul li.star{
        color: #FCA706;
      }
    }
    .fixed{
      position: fixed;
      top: 5.3rem;
      right: 4.5rem;
    }
}

.report{
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 10000;
}

.back{
  position: absolute;
  z-index: 9999;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: #000;
  opacity: 0.1;
}

.reportContainer{
  position: absolute;
  top: 50%;left: 50%;
  transform: translate(-50%,-50%);
  z-index: 10001;
  width: 30vw;
  background: #fff;
  padding: 1rem;
}

.title{
  font-size: 1.1rem;
  color: var(--soft-black);
  text-align: start;
}

.reportType{
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.reportType>span{
  cursor: pointer;
  padding: .5rem;
  width: 4rem;
  text-align: center;
  background: var(--background);
  color: var(--light-color);
  margin-left: .5rem;
  margin-top: .5rem;
  font-size: .9rem;
}
.reportType>span.active{
  color: #000;
}

.go-remark{
  margin-top: .5rem;
  width: 94%;
  border: .15rem solid transparent;
  cursor: pointer;
  resize: none;
  padding: .5rem;
  font-size: .9rem;
  border-radius: .4rem;
  background: #fff;
  border: 2px solid var(--light--gray);
}
.go-remark:focus{
  border: 2px solid #A4CCF4;
  outline: none;
  background: #fff;
}

  .reportContainer>div{
    margin-top: 1rem;
  }

button{
  cursor: pointer;
  width: 20%;height: 2rem;
  font-size: .9rem;
  flex: 1;
  color: #fff;
  background: var(--blue);
  border: none;
  margin-left: .8rem;
}
button:hover{
  background: var(--light--blue);
}
.backHome{
  background: #fff;
  color: var(--light--blue);
  border: 1px solid var(--light--blue);
}
.backHome:hover{
  color: var(--blue);
  background: #fff;
}

.close{
  position: absolute;
  right: .5rem;top: .5rem;
  text-align: end;
  font-size: 1.2rem;
  color: var(--light--gray);
  cursor: pointer;
}

</style>