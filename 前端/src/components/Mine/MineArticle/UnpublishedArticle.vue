<template>
  <div id="unpublished">
    <div class="gotoWrite" v-if="articles.length===0">
      写一写文章，试一试吧！&nbsp;&nbsp;<a href="#" @click.stop.prevent="gotoWrite()">点击发表文章！</a>
    </div>
    <div class="unPublishar">
      <Article v-for="item in articles" :key="item.id" :article="item" @update="refreshArticle" >
      </Article>
    </div>
  </div>
</template>

<script>
import Article from '@/components/Mine/MineArticle/Article'
export default {
  data(){
    return {
      articles: []
    }
  },
  components: {
    Article
  },
  methods: {
    // 获得未审核博文
    async getUserArticle(){
      let data ={
        'id': this.userId,
        'identify': 1
      }
      const {data: res} = await this.$http.post('/discussPost/showAllPosts' , this.qs.stringify(data));
      this.articles=res;
    },
    refreshArticle(){
      this.getUserArticle();
    }
  },
  mounted(){
    this.userId=localStorage.getItem('id');
    this.getUserArticle();
  },
  computed: {
  }
}
</script>

<style scoped lang="less">
#unpublished{
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
  span{
    margin-right: 1rem;
  }
}
</style>