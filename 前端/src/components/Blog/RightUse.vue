<template>
<div id="RightUse">
  <div class="UserMsg" @click="gotoUser()">
    <div>关于作者</div>

    <div class="last">
      <div class="user">
        <img :src="userInfo.url" alt="">
        <span>
          <p class="username">{{ userInfo.username }} <span class="level">Lv 5</span> </p>
          <p>{{ userInfo.signature }}</p>
        </span>
      </div>

      <div class="honor">
        <div>
          <span>
            <i class="fas fa-thumbs-up"></i>
          </span>&nbsp;&nbsp;&nbsp;&nbsp;获得点赞数: {{ userSupportsNum }}
        </div>
        <div>  
          <span>
            <i class="far fa-eye"></i>
          </span>&nbsp;&nbsp;&nbsp;&nbsp;获得评论数: {{ userCommentNum }}
        </div>
      </div>

    </div>
  </div>

  <div class="OtherArticle">
    <div>相关文章</div>
    <RightArticle  v-for="item in otherArticles" :key="item.id" :article="item"></RightArticle>
  </div>

</div>
</template>

<script>
import RightArticle from '@/components/Blog/RightArticle'
export default {
  props: {
    headImg: {
      type: String,
      default: require('@/assets/head.png')
    },
    userId: {
      type: Number,
      default: 0,
      required: true
    },
    blogID: {
      type: Number,
      default: 0,
      required: true
    },
    commentNum: {
      default: 0,
      required: true
    },
    supportNum: {
      default: 0,
      required: true
    }
  },
  data(){
    return {
      otherArticles: [
      ],
      userInfo: {},
      userCommentNum: 0,
      userSupportsNum: 0
    }
  },
  components: {
    RightArticle
  },
  methods: {
    async getUserInfo(){
      let data ={
        'id': this.userId
      }
      const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));
      this.userInfo=res;
    },
    async getUserArticle(){
      // /user/information

      let data ={
        'id': this.userId,
        'identify': 0
      }
      const {data: res} = await this.$http.post('/discussPost/showAllPosts' , this.qs.stringify(data));
      this.otherArticles=res.filter(item => item.identify==0).filter(item => item.id!=this.blogID).slice(0,5);
    },
    gotoUser(){
      sessionStorage.setItem('visit',this.userId);
      this.$router.push('/mine');
    },
    async getUserCommentsNum(){
      // /user/information
      let data ={
        'userId': this.userId
      }
      const {data: res} = await this.$http.post('/comment/getCommentNumByUserId' , this.qs.stringify(data));
      // this.user=res;

      this.userCommentNum=res.data;
    },
    // /support-blog/getSupportNumByUserId
    async getUserSupportsNum(){
      // /user/information
      let data ={
        'userId': this.userId
      }
      const {data: res} = await this.$http.post('/support-blog/getSupportNumByUserId' , this.qs.stringify(data));
      // this.user=res;
      this.userSupportsNum=res.data;
    },
  },
  mounted(){
    if(this.userId!=0) this.getUserInfo();
    this.getUserArticle();
    this.getUserCommentsNum();
    this.getUserSupportsNum();
  },
  watch: {
    userId(newVal, oldVal){
      this.getUserInfo();
      this.getUserArticle();
      this.getUserCommentsNum();
      this.getUserSupportsNum();
    },
    commentNum(){
      this.getUserCommentsNum();
    },
    supportNum(){
      this.getUserSupportsNum();
    }

  }
}
</script>

<style scoped lang="less">
#RightUse{
  
  width: 25%;
  // background: #fff;
  height: 20rem;

  .UserMsg{
    box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
    background: #fff;
  }

  .UserMsg>div:first-child{
    border-bottom: .1rem solid rgb(243, 244, 245);
    padding: .8rem;
    text-align: start;
    font-size: .9rem;
    color: #000;
  }

  .UserMsg .last{
    padding: .8rem;
    text-align: start;
    
    .user{
      cursor: pointer;
      display: flex;
      justify-content: space-around;
      // border-bottom: .15rem solid rgb(243, 244, 245);
      img{
        width: 50px;height: 50px;
        border-radius: 50%;
        flex: 1;
      }
      span{
        padding-left: .5rem;
        flex: 5;
        
        p{
          margin-bottom: .5rem;
          font-size: .85rem;
        }
        .username{
          position: relative;
          color: #000;
          font-size: 1rem;
          font-weight: 700;
          .level{
            position: absolute;
            top: 0;
            right: 0;
            padding: .3rem;
            font-size: 3rem;
            font-weight: 900;
            background: #FCDC74;
            border-radius: 1rem;
            color: #fff;
            text-align: start;
            transform: scale(.2) translateY(-190%) translateX(-100%);
          }
        }
      }
    }
  }

  .honor {
    margin-top: .5rem;
    padding-left: .5rem;
    // padding: .5rem;
    // font-weight: 400;
    // font-family: serif;
    font-size: .9rem;
    color: #000;
    div{
      margin-bottom: .5rem;
      span{
        display: inline-block;
        text-align: center;
        line-height: 25px;
        border-radius: 50%;
        font-size: .9rem;
        width: 25px;
        height: 25px;
        color: rgb(123, 184, 249);
        background: #d9f1ff;
      }
    }
  }

  .OtherArticle{
    box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
    margin-top: .5rem;
    background: #fff;
    div:first-child{
      border-bottom: .1rem solid rgb(243, 244, 245);
      padding: .8rem;
      text-align: start;
      font-size: .9rem;
      color: #000;
    }
  }
  


}
</style>