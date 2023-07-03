<template>
  <div id="mine-talking">
    <div class="inputTalking" v-if="isMe">
      <textarea name="sendTalk" id="sendTalk" cols="30" rows="8" v-model.trim="talk" placeholder="请输入想要记录的文字！"></textarea>
      <button @click="addTalk()"><i class="far fa-edit"></i> 发布说说</button>
    </div>
    <div class="myTalks">
      <Talking :isMe="isMe" v-for="item in talkings" :key="item.id" :talking="item"></Talking>
    </div>
  </div>
</template>

<script>
import Talking from '@/components/Mine/MineTalking/Talking'
export default {
  data(){
    return {
      talk: '',
      talkings: [
        {
          id: 0,
          talk: '艾薇儿的tomorrow很好听！',
          time: new Date(),
          visibility: 1
        },{
          id: 1,
          talk: '艾薇儿的tomorrow很好听！',
          time: new Date(),
          visibility: 1
        },{
          id: 2,
          talk: '艾薇儿的tomorrow很好听！',
          time: new Date(),
          visibility: 1
        }
      ],
      isMe: false
    }
  },
  methods: {
    init(){

    },
    addTalk(){

    }
  },
  components: {
    Talking
  },
  mounted(){
    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
  }
}
</script>

<style lang="less" scoped>
#mine-talking{

  .inputTalking{
    position: relative;
    width: 100%;
    background: #fff;
    // padding: 1rem;

    #sendTalk{
      width: 90%;
      margin: 2rem;
      border: .1rem solid var(--gray);
      cursor: pointer;
      resize: none;
      padding: 1rem;
      font-size: 1rem;
      border-radius: .4rem;
      background: #fff;
    }
    #sendTalk:hover{
      box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.15);
    }
    #sendTalk:focus{
      border: .1rem solid #A4CCF4;
      // 去除选中后的框
      outline: none;
      background: #fff;
      box-shadow: var(--box-shadow);
    }

    button{
      position: absolute;
      bottom: 4rem;
      right: 5rem;
      padding: .6rem;
      font-size: .9rem;
      color: #fff;
      background: var(--blue);
      border: none;
    }
    button:hover{
      background: var(--light--blue);
    }

  }

  .myTalks{
    padding: 3rem;
    position: relative;
    // border: var(--border);
    // height: 1000px;
    background: #fff;
  }
  .myTalks::before{
    content: '';
    position: absolute;
    left: 4rem;top: 2rem;
    height: calc(~"100% - 4rem");width: .25rem;
    background: #e6e9eb;
    z-index: 0;
    // border: 4px solid var(--gray);

  }

  
}
</style>