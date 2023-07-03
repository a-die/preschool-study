<template>
  <div id="mine-setting">
    <div class="se-left">
      <ul>
        <li @click="changeMine(1)" :class="{'active':(mineSetting==1)}"><a>账号安全</a></li>
        <li @click="changeMine(2)" :class="{'active':(mineSetting==2)}"><a>修改资料</a></li>
        <li @click="changeMine(3)" :class="{'active':(mineSetting==3)}"><a>编辑主页</a></li>
      </ul>
    </div>

    <div class="se-right">
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

    }
  },
  computed: {
    ...mapState([
      'mineSetting'
    ])
  },
  methods: {
    ...mapActions([
      'changeMineSetting'
    ]),
    changeMine(n){
      this.changeMineSetting(n);
      if(n===1) this.$router.push('/mine/mysetting/settingSafe');
      else if(n===2)  this.$router.push('/mine/mysetting/settingInfo');
      else this.$router.push('/mine/mysetting/settingMyPage');
    }
  },
  unmounted(){
    this.$store.commit('changeMineSetting',1);
  }
}
</script>

<style scoped lang="less">
#mine-setting{
  display: flex;

  .se-left{
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
        position: relative;
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
  .se-right{
    flex: 7;
    background: #fff;
  }
}
</style>