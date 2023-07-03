<template>
  <div id="reply">
    <div class="left">
      <img :src="reply.userUrlFrom" alt="">
    </div>
    <div class="msg right" >
      <div class="msg-information">
        <span class="name">{{ reply.usernameFrom }} <span v-if="reply.user_id_from===blogOwn" class="host">楼主</span> </span>
        <span>回复</span>
        <span class="name">{{ reply.usernameTo }} <span v-if="reply.user_id_to===blogOwn" class="host">楼主</span> </span>
        <span>|</span>
        <span>
          {{ replyDate }}
        </span>
      </div>
      <div class="msg-content">
        <span>{{ reply.content }}</span>
      </div>
      <div class="msg-do">
        <span @click.stop="giveRemark()" :class="{'blue':sendFlag}"><i class="far fa-comment-alt"></i>&nbsp;</span>
      </div>
      <div class="msg-sendReply">
        <SendReply @updated="newReply"  v-if="sendFlag"  :back="false" :id="remarkId" :userIdFrom="reply.user_id_from"></SendReply>
      </div>
    </div>
  </div>
</template>

<script>
import SendReply from '@/components/Blog/SendReply'
export default {
  props: {
    reply: {
      type: Object,
      default: {
        comment_id: 3,
        context: null,
        create_time: "2021-12-09T06:56:27.000+00:00",
        id: 3,
        user_id_from: 985669637,
        user_id_to: 985669637,
        usernameFrom: "alex",
        usernameTo: "alex",
      },
      required: true
    },
    remarkId: 0,
    blogOwn: 0
  },
  emits: ['updated'],
  data(){
    return {
      sendFlag: false,
      replyNum: 12,
      replying: '取消回复',
      praise: 12,
    }
  },
  computed: {
    replyDate(){
      let t = new Date(this.reply.create_time);
      let y=t.getFullYear();let m=t.getMonth()+1;let d=t.getDate();
      return y+'-'+m+'-'+d;
    }
  },
  methods: {
    giveRemark(){
      this.sendFlag=!this.sendFlag;
      if(this.sendFlag){
        let ans=this.replyNum;
        this.replyNum=this.replying;
        this.replying=ans;
      }else{
        let ans=this.replyNum;
        this.replyNum=this.replying;
        this.replying=ans;
      }
    },
    newReply(){
      this.giveRemark();
      this.$emit('updated');
    }
  },
  components: {
    SendReply
  },
  mounted(){
    // 设置回复评论框消失情况
    let that=this;
    document.addEventListener('click', (e) => {
      that.sendFlag=false;
      if(typeof that.replying == 'number'){
        let ans=this.replyNum;
        this.replyNum=this.replying;
        this.replying=ans;
      }
    })
  },
  watch: {
    blogOwn(newVal,oldVal){
      
    }
  }
}
</script>

<style scoped lang="less">
#reply{
  margin: 0;
  // background: var(--light--blue);
  padding: 1rem;
  background: var(--background);
  border-bottom: .1rem solid var(--border--color);

  display: flex;
  justify-content: flex-start;

  .left{
    flex:1;
  }
  .right{
    flex: 13;
  }

  img{
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }

  .msg{
    div{
      margin-bottom: .2rem;
    }
    .msg-information span{
      margin-right: 1rem;
      font-size: .85rem;
    }
    .msg-information .name{
      font-size: 0.9rem;
      color: #000;
      font-weight: 700;
      cursor: pointer;

      .host{
        display: inline-block;
        font-size: .3rem;
        padding: .1rem;
        color: var(--blue);
        // background: var(--blue);
        border-radius: .4rem;
        margin-right: 0rem;
      }
    }

    .msg-content{
      font-size: .9rem;
    }

    .msg-do{
      font-size: .85rem;
      color: var(--gray);
      font-weight: 400;
      transform: translateY(10px);
      span{
        margin-right: 1rem;
      }
      span:hover{
        cursor: pointer;
        color: var(--blue);
      }
      i{
        font-size: 1rem;
      }
    }

    .msg-sendReply{
      margin-top: 1rem;
    }
    
  }

  
}
.blue{
  color: var(--blue);
}
</style>