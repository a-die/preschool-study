<template>
  <div id="app" :class="{'head':headFlag}">
    <Index></Index>
  </div>
</template>

<script>
import Index from '@/components/Index'
import { mapState } from 'vuex'
import click from '@/assets/js/click'

export default {
  name: 'App',
  components: {
    Index
  },
  created(){
    click.init();
    // 实现页面刷新，不丢失数据
    window.addEventListener('beforeunload',()=>{
       sessionStorage.setItem('list', JSON.stringify(this.$store.state))
    })
    try{
      sessionStorage.getItem('list') && this.$store.replaceState(Object.assign({},this.$store.state,JSON.parse(sessionStorage.getItem('list'))))
    }catch(err) {
      console.log(err);
    }
    sessionStorage.removeItem("list");
    if(this.$store.state.loginFlag===0){
      localStorage.removeItem('id');
    }
  },
  computed: {
    ...mapState([
      'headFlag'
    ])
  }
}
</script>

<style lang="less" scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  // border: 1px solid red;
  
  // width: 80%;
  margin: 0rem auto;
  // height: 200vh;
}
.head{
  padding-top: 4rem;
}

</style>
