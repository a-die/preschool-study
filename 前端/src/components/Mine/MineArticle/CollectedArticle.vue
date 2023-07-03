<template>
  <div id="collected">
    <div class="unPublishar">
      <Article v-for="item in articles" :key="item.id" :article="item" :colectted="colectted">
        <template #articleDo v-if="isMe">
            <button @click.stop="cancleStar(item.discussPostId)">取消收藏</button>
        </template>
      </Article>
    </div>
  </div>
</template>

<script>
import Article from '@/components/Mine/MineArticle/Article'
export default {
  data(){
    return {
      isMe: false,
      articles: [],
      colectted: true
    }
  },
  components: {
    Article
  },
  computed: {
    
  },
  mounted(){

    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
    this.getStarArticle();
    
  },
  methods: {
    async getStarArticle(){
      // /favorite-blog/getFavoriteBolgDetailedByUserId
      this.starFlag=!this.starFlag;
      let data ={
        'discussPostId': this.id,
        'userId': Number(localStorage.getItem('id')),
      }
      const {data: res} = await this.$http.post('/favorite-blog/getFavoriteBolgDetailedByUserId',this.qs.stringify(data));

      this.articles = res.data;
    },
    async cancleStar(discussPostId){
      let data ={
          'discussPostId': discussPostId,
          'userId': Number(localStorage.getItem('id')),
        }
        const {data: res} = await this.$http.post('/favorite-blog/deleteFavoriteBlog',this.qs.stringify(data));

      if(res.code===0) this.getStarArticle();
    }
  }
}
</script>

<style scoped lang="less">
#collected{
  padding: 1rem;
  button{
    margin-right: 1rem;
    width: 60px;height: 30px;
    background:  var(--blue);
    border: none;
    color: #fff;
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
    border-radius: .2rem;
  }
  button:hover{
    background: var(--light--blue);
  }
}
</style>