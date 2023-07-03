<template>
  <div id="mine-article">
    <div class="ar-left">
      <ul>
        <li @click="change(4)" :class="{'active':(mineArticle===4)}"><a to="/mine/myarticle/publishedNotes"><span :class="{'blue':(mineArticle==4)}"><i class="fas fa-egg"></i></span>&nbsp;&nbsp;&nbsp;已发布笔记</a></li>
        <li @click="change(1)" :class="{'active':(mineArticle===1)}"><a to="/mine/myarticle/published"><span :class="{'blue':(mineArticle==1)}"><i class="fas fa-bolt"></i></span>&nbsp;&nbsp;&nbsp;已发布雷点</a></li>
        <li @click="change(5)" :class="{'active':(mineArticle===5)}"><a to="/mine/myarticle/publishedQuiz"><span :class="{'blue':(mineArticle==5)}"><i class="fas fa-rocket"></i></span>&nbsp;&nbsp;已发布提问</a></li>
        <li v-if="isMe" @click="change(2)" :class="{'active':(mineArticle===2)}"><a to="/mine/myarticle/unpublished"><span :class="{'red':(mineArticle==2)}" ><i class="fas fa-eraser"></i></span>&nbsp;&nbsp;未发布文章</a></li>
        <li @click="change(3)" :class="{'active':(mineArticle===3)}"><a to="/mine/myarticle/collected"><span :class="{'yellow':(mineArticle==3)}"><i class="fas fa-star"></i></span>&nbsp;&nbsp;收藏管理</a></li>
      </ul>
    </div>

    <div class="ar-right">
      <router-view>

      </router-view>
    </div>
  </div>
</template>

<script>
// store
import { mapState } from 'vuex'
import { mapActions } from 'vuex'

export default {
  data(){
    return {
      isMe: false,
      userID: 1
    }
  },
  computed: {
    ...mapState([
      'mineArticle'
    ])
  },
  methods: {
    ...mapActions([
      'changeMineArticle'
    ]),
    change(n){
      this.changeMineArticle(n);
      if(n===4) this.$router.push('/mine/myarticle/publishedNotes');
      else if(n===1) this.$router.push('/mine/myarticle/published');
      else if(n===5) this.$router.push('/mine/myarticle/publishedQuiz');
      else if(n===2) this.$router.push('/mine/myarticle/unpublished');
      else this.$router.push('/mine/myarticle/collected');
    }

    // /discussPost/getBlogByIdentify
    
  },
  mounted(){

    this.userId=sessionStorage.getItem('visit');
    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
  },
  unmounted(){
    this.$store.commit('changeMineArticle',4);
  }
}
</script>

<style scoped lang="less">
#mine-article{
  display: flex;

  .ar-left{
    flex: 2;
    background: #fff;
    min-height: 15rem;
    padding: .4rem;
    border-right: 1px solid rgba(229, 230, 235,.7);
    li{
      cursor: pointer;
      font-size: .95rem;
      color: var(--soft-black);
      padding: 1rem;
      text-align: start;
      .blue{
        color: var(--blue);
      }
      .red{
        color: var(--light-red);
      }
      .yellow{
        color: var(--light-yellow);
      }
      a{
        color: var(--soft-black);
        display: inline-block;
      }
    }
    li:hover{
      background: var(--very-light-blue);
      a{
        display: inline-block;
        color: var(--blue);
      }
    }
    .active{
      background: var(--very-light-blue);
      color: var(--blue);
      a{
        color: var(--blue);
      }
    }
  }
  .ar-right{
    flex: 7;
    background: #fff;
  }
}
</style>