<template>
  <div id="mine-index">
    <div class="left">
		<v-md-preview :text="mainArticle"></v-md-preview>
    </div>

    <div class="right">
      <div class="achievement">
        <div>
          <p class="number">{{userArticlesNum}}</p>
          <p>文章</p>
        </div>
        <div>
          <p class="number">{{ userCommentNum }}</p>
          <p>评论</p>
        </div>
      </div>
      <div class="mineInfo">
        <span class="infoTitle"> <i class="fas fa-book-open"></i>&nbsp;&nbsp;个人信息</span>
        <ul>
          <li><span class="svg" style="color: var(--blue);"><i class="fas fa-user"></i></span>&nbsp;&nbsp;&nbsp;&nbsp;用户名：<span>{{ user.username }}</span></li>
          <li><span class="svg" style="color: var(--green);"><i class="fas fa-chess-queen"></i></span>&nbsp;&nbsp;&nbsp;&nbsp;等级：<span>12</span></li>
          <li><span class="svg" style="color: var(--light-yellow);"><i class="fab fa-bitcoin"></i></span>&nbsp;&nbsp;&nbsp;&nbsp;cc币：<span>{{ user.ccNum }}</span></li>
          <li><span class="svg" style="color: var(--light-red);"><i class="fas fa-clock"></i></span>&nbsp;&nbsp;&nbsp;&nbsp;注册时间：<span>{{ time }}</span></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return {
      mainArticle: '',
      username: 'Alex',
      level: 1,
      ccCoin: 250,
      registerTime: new Date(),
      user: {},
      userArticlesNum: 0,
      userCommentNum: 0
    }
  },
  computed: {
    registerTimed(){
      return this.registerTime.toLocaleString();
    },
    time(){
      let t = new Date(this.user.createTime);
      let y=t.getFullYear();let m=t.getMonth()+1;let d=t.getDate();
      return y+'-'+m+'-'+d;
    }
  },
  methods: {
    async getUserInfo(){
      // /user/information
      let data ={
        'id': sessionStorage.getItem('visit')
      }

      const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));

      if(res.selfIndex!=null) 
      this.mainArticle = res.selfIndex;
      this.user=res;

    },
    async getUserArticlesNum(){
      // /user/information
      let data ={
        'userId': sessionStorage.getItem('visit')
      }
      const {data: res} = await this.$http.post('/discussPost/getBlogNumByUserId' , this.qs.stringify(data));
      // this.user=res;
      this.userArticlesNum=res.data;
    },
    async getUserCommentsNum(){
      // /user/information
      let data ={
        'userId': sessionStorage.getItem('visit')
      }
      const {data: res} = await this.$http.post('/comment/getCommentNumByUserId' , this.qs.stringify(data));
      // this.user=res;
      this.userCommentNum=res.data;
    },
  }
  ,
  mounted(){
    this.getUserInfo();
    this.getUserArticlesNum();
    this.getUserCommentsNum();
  }

}
</script>

<style scoped lang="less">
#mine-index{
  margin-bottom: 2rem;
  
  display: flex;

  .left{
    flex: 8;
    margin-right: 1rem;
    background: #fff;
    min-height: 20rem;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
  }
  .right{
    flex: 2.5;
    
    div{
      background: #fff;
    }

    .achievement{
      color: #787A7B;
      margin-bottom: 1rem;
      box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
      padding: 1.5rem;
      font-size: .9rem;
      display: flex;
      justify-content: space-around;
      border-radius: .2rem;
      .number{
        color: var(--soft-black);
        font-size: 1.5rem;
        margin-bottom: .7rem;
        font-weight: 430;
      }
    }
    .mineInfo{
      box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.05);
      padding-left: 1.5rem;padding-right: 1.5rem;
      border-radius: .2rem;  
      ul{
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
        font-size: 1rem;
        li{
          color: var(--very-soft-black);
          text-align: start;
          padding: .7rem;
          width: 100%;
          .svg{
            font-size: 1.2rem;
          }
        }
      }
      .infoTitle{
        display: block;
        padding: .7rem;
        font-size: 1.2rem;
        color: var(--soft-black);
        text-align: start;
        border-bottom: #e9e9e9 solid 1px;
      }
    }
  }

}
</style>