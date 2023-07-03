<template>
  <div id="myList">
    <ul>
      <li @click="goWrite()"><span><i class="fas fa-pen"></i></span>&nbsp;&nbsp;写文章</li>
      <li class="divide"></li>
      <li @click="goMine()"><span><i class="fas fa-user"></i></span>&nbsp;&nbsp;我的主页</li>
      <li @click="goMyStar()"><span><i class="fas fa-star"></i></span>&nbsp;我的收藏</li>
      <li @click="goToPu()"><span><i class="fas fa-thumbs-up"></i></span>&nbsp;&nbsp;学科拓扑图</li>
      <li class="divide"></li>
      <li @click="goMySetting()"><span><i class="fas fa-cog"></i></span>&nbsp;&nbsp;设置</li>
      <li @click="logout()"><span><i class="fas fa-sign-out-alt"></i></span>&nbsp;&nbsp;退出</li>

    </ul>
  </div>
</template>

<script>
export default {
  inject: ['reload'],
  emit: ['hide'],
  data(){
    return {

    }
  },
  methods: {
    logout(){
      this.$store.replaceState({
        headFlag: true,
        minePage: 1,
        mineArticle: 1,
        mineStudy: 1,
        mineSetting: 1,
        loginFlag: 0,
        username: '',
        // portrait: require('@/assets/head.png')
        portrait: '@/assets/back5.jpg',
        searchFlag: true
      })
      // sessionStorage.setItem(,res.data.id);
      localStorage.removeItem('id');
      this.$router.push('/');
    },
    goWrite(){
      this.$store.commit('setHeadFalse');
      this.$emit('hide');
      this.$router.push('/write');
    },
    goMine(){
      sessionStorage.setItem('visit',localStorage.getItem('id'));
      this.$emit('hide');
      this.$router.push('/mine');
      this.reload();
      // this.$router.push('/mine',{params:{}});
    },
    goMyStar(){
      sessionStorage.setItem('visit',localStorage.getItem('id'));
      this.$emit('hide');
      this.$store.commit('setMinePage',2);
      this.$store.commit('changeMineArticle',3);
      this.$router.push('/mine/myarticle/collected');
      this.reload();
    },
    goMySetting(){
      this.$emit('hide');
      this.$store.commit('setMinePage',4);
      this.$router.push('/mine/mysetting');
      this.reload();
    },
    goToPu(){
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
#myList{
  border: 1px solid var(--background);
  background: #fff;
  padding: .2rem;
  width: 9rem;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
}
.divide{
  border-bottom: 2px solid var(--background);
  margin: .5rem 0;
  padding: 0;
}
li{
  color: var(--very-soft-black);
  font-size: .95rem;
  text-align: start;
  padding: .3rem;
  cursor: pointer;
}
li:hover{
  background: var(--light--blue);
}

</style>