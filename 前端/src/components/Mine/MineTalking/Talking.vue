<template>
  <div class="talking">
    <div class="ta-content">
      {{ talk.talk }}
    </div>
    <div class="ta-do">
      <span v-if="visibility==1" class="hide" @click="showTalk()"><i  class="far fa-eye-slash"></i>&nbsp;&nbsp;{{ visible }}</span>
      <span v-else class="hide" @click="showTalk()"><i  class="far fa-eye"></i>&nbsp;&nbsp;{{ visible }}</span>
      <span class="hide" v-if="isMe" @click="deleteTalk()"><i class="far fa-trash-alt"></i>&nbsp;&nbsp;删除</span>
      <span><i class="far fa-clock"></i>&nbsp;&nbsp;{{ createTime }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    talking: {
      type: Object,
      default: {
        talk: '艾薇儿的tomorrow很好听！',
        time: new Date(),
        visibility: 1
      },
      require: true
    },
    isMe: {
      type: Boolean,
      default: false
    }
  },
  data(){
    return {
      visibility: 1,
      talk: {}
    }
  },
  computed: {
    visible(){
      if(this.visibility===1) return '已隐藏';
      return '已公开';
    },
    createTime(){
      return this.talk.time.toLocaleString();
    }
  },
  created(){
    this.talk = this.talking;
    this.visibility = this.talk.visibility
  }
  ,
  methods: {
    showTalk(){

      if(this.visibility===1){
        this.talk.visibility=2;
        this.visibility=2;
      }
      else{
        this.talk.visibility=1;
        this.visibility=1;
      }

    }
  },
  watch: {
    talk: {
      deep: true,
      immediate: true,
      handler(newVal,oldVal){
      }
    }
  }
}
</script>

<style scoped lang="less">
.talking{
  cursor: pointer;
  margin-bottom: 1rem;
  position: relative;
  // border: 1px solid red;
  background: #fff;
  padding-left: 3rem;padding-right: 3rem;
  padding-top: 1.5rem;padding-bottom: 1.5rem;
  box-shadow: 0 5px 10px 0 rgba(0,0,0,0.1);;
  .ta-content{
    // padding: 3rem;
    text-align: start;
    font-size: .9rem;
    color: var(--gray);
  }
  .ta-do{
    margin-top: 1rem;
    text-align: end;
    font-size: .9rem;
    color: var(--gray);
    span{
      cursor: pointer;
      margin-right: 1rem;
      color: var(--light-color);
    }
    .hide{
      display: none;
    }
    .hide:hover{
      color: var(--light--blue);
    }
  }
}
.talking:hover{
  .hide{
    display: inline-block;
  }
}

.talking::before{
  content: '';
  position: absolute;
  top: 33%;left: 2rem;
  transform: translate(-50%,-50%) scale(.1);
  width: 5rem;height: 5rem;
  background: var(--blue);
  border: 1.5rem solid var(--light--blue);


  border-radius: 50%;
}

.talking:hover{
  background: var(--background);
}
</style>