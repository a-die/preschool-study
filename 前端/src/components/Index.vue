<template>
<div id="index">
  <Header v-if="headFlag"></Header>
  
  <router-view class="page" v-if="blogAlive">
  </router-view>

  
</div>
</template>

<script>
import Header from '@/components/Header'
import { ElLoading } from 'element-plus'

export default {
  provide() {
    return {
      reload: this.reload,
      load: this.load
    }
  },
  data(){
    return {
      blogAlive: true
    }
  },
  components: {
    Header
  },
  created() {
  },
  computed: {
    headFlag(){return this.$store.state.headFlag}
  },
  methods: {
    reload(){
      this.blogAlive = false;
      this.$nextTick(() => (this.blogAlive = true))
    },
    load(){
      const loading = ElLoading.service({
        lock: true,
        text: 'Loading',
        background: 'rgba(0, 0, 0, 0.7)',
      })
      setTimeout(() => {
        loading.close()
      }, 500);
    }
  }
}
</script>

<style scoped >
</style>