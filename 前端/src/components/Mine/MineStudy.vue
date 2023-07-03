<template>
  <div id="mine-study">
    <div class="st-left">
      <ul>
        <li @click="changeMineStudy(1)" :class="{'active':(mineStudy==1)}"><router-link to=""><span :class="{'blue':(mineStudy==1)}"><i class="fas fa-route"></i></span>&nbsp;&nbsp;学习足迹</router-link></li>
        <li @click="changeMineStudy(2)" :class="{'active':(mineStudy==2)}"><router-link to=""><span :class="{'red':(mineStudy==2)}" ><i class="fas fa-eraser"></i></span>&nbsp;&nbsp;******</router-link></li>
      </ul>
    </div>

    <div class="st-right">
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
      isMe: false
    }
  },
  computed: {
    ...mapState([
      'mineStudy'
    ])
  },
  methods: {
    ...mapActions([
      'changeMineStudy'
    ])
  },
  mounted(){

    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
  },
  unmounted(){
    this.$store.commit('changeMineStudy',1);
  }
}
</script>

<style scoped lang="less">
#mine-study{
  display: flex;

  .st-left{
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
      a{
        position: relative;
        color: var(--soft-black);
        display: inline-block;
        width: 100%;height: 100%;
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
  .st-right{
    flex: 7;
    background: #fff;
  }
}
</style>