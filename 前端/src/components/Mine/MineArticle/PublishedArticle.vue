<template>
  <div id="published">
    <div class="gotoWrite" v-if="isMe">
      写一写文章，试一试吧！&nbsp;&nbsp;<a href="#" @click.stop.prevent="gotoWrite()">点击发表文章！</a>
    </div>
    <div class="publishar">
      <Article v-for="item in articles" :key="item.id" :article="item" @update="refreshArticle"></Article>
    </div>
  </div>
</template>

<script>
import Article from '@/components/Mine/MineArticle/Article'

export default {
  data(){
    return {
      userId: 1,
      articles: []
    }
  },
  components: {
    Article
  },
  methods: {
    gotoWrite(){
      this.$store.commit('setHeadFalse');
      this.$router.push('/write');
    },
    // /discussPost/showAllPosts
    // 获得已审核博文
    async getUserArticle(){

      let data ={
        'id': Number(this.userId),
        'btype': 1
      }

      const {data: res} = await this.$http.post('/discussPost/showSomePosts' , this.qs.stringify(data));

      this.articles=res.filter(item => item.identify===0);
    },
    refreshArticle(){
      this.getUserArticle();
    }
  },
  mounted(){
    this.userId=sessionStorage.getItem('visit');
    // console.log(sessionStorage.getItem('visit') );
    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
    this.getUserArticle();
  }
}
</script>

<style scoped lang="less">
#published{
  padding: 1rem;
  
  .gotoWrite{
    padding: 1rem;
    text-align: end;
    font-size: .85rem;
    background: var(--background);
    height: 20%;
    border-radius: .4rem;
    a{
      text-decoration: underline;
      color: var(--light--blue);
    }
  }
  .publishar{
    margin-top: 1rem;
  }
}
</style>