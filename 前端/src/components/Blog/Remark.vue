<template>
  <div id="remark" >
    <div>
      <img :src="remark.url" alt="">
    </div>
    <div class="msg">
      <div class="msg-information">
        <span class="name">{{ remark.username }} <span v-if="remark.userIdFrom===blogOwn" class="host">楼主</span> </span>
        <span>|</span>
        <span>
          {{ time }}
        </span>
      </div>
      <div class="msg-content">
        <span>{{ remark.content }}</span>
      </div>
      <div class="msg-do">
        <span @click.stop="giveRemark()" :class="{'blue':sendFlag}"><i class="far fa-comment-alt"></i>&nbsp;</span>
      </div>
      <div class="msg-sendReply">
        <SendReply @updated="refresh" v-if="sendFlag" :back="true" :id="remark.id" :userIdFrom="remark.userIdFrom"></SendReply>
      </div>
      <div class="msg-reply">
        <Reply :blogOwn="userId" @updated="refresh" v-for="item in replys" :remarkId="remark.id" :key="item.id" :reply="item"></Reply>
      </div>
    </div>
  </div>
</template>

<script>
import Reply from '@/components/Blog/Reply';
import SendReply from '@/components/Blog/SendReply';
export default {
  inject: ['refreshBlog'],
  props: {
    remark: {
      type: Object,
      default: {
        content: "really good！！",
        create_time: null,
        discussPostId: 5,
        id: 3,
        userIdFrom: 985669637,
        username: "alex",
      },
      required: true
    },
    blogOwn: 0
  },
  data(){
    return {
      sendFlag: false,
      replyNum: 12,
      replying: '取消回复',
      praise: 12,
      thumbFlag: false,
      replys: [
        {
          id: 1,
          img: require('@/assets/logo.png'),
          name: 'alex1',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },{
          id: 2,
          img: require('@/assets/logo.png'),
          name: 'alex2',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },{
          id: 3,
          img: require('@/assets/logo.png'),
          name: 'alex3',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },{
          id: 4,
          img: require('@/assets/logo.png'),
          name: 'alex4',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },

      ],
      userId: 1
    }
  },
  computed: {
    remarkDate(){
      let s = new Date().toLocaleString();
      return s;
    },
    time(){
      let t = new Date(this.remark.create_time);
      let y=t.getFullYear();let m=t.getMonth()+1;let d=t.getDate();
      return y+'-'+m+'-'+d;
    }
  },
  components: {
    Reply,
    SendReply
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
        // 获取评论的回复
    async showReplies(){

      let data ={
        'comment_id': this.remark.id
      }
      const {data: res} = await this.$http.post('/comment-reply/showReplies',this.qs.stringify(data));
      this.replys=res;
    },

    async giveThumbs(){
      this.thumbFlag = !this.thumbFlag;
      let that = this;
      if(this.thumbFlag){
        let data ={
          'id': this.remark.id
        }
        const {data: res} = await this.$http.post('/discussPost/up',this.qs.stringify(data));
        this.$emit('good',that.index,1);
      }else{
        this.$emit('good',that.index,-1);
      }
      
      // /discussPost/up

    },

//  评论刷新
    refresh(){
      if(this.sendFlag){
        this.giveRemark();
      }
      this.refreshBlog();
      this.showReplies();
    }

  },
  mounted(){
    this.userId=this.blogOwn;
    this.showReplies();

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
    remark(){
      deep: true
      // this.showReplies()
    },
    blogOwn(newVal,oldVal){
      this.userId=newVal;
    }
  }
}
</script>

<style scoped lang="less">
#remark{
  
  position: relative;
  margin: .5rem auto;
  width: 100%;
  background: #fff;
  // min-height: 150px;
  display: flex;
  border-bottom: .1rem solid var(--border--color);

  div:first-child{
    flex: 1;
  }

  .msg{
    flex: 15;
    text-align: start;
    padding: 1rem;
    padding-top: 2rem;
    .msg-information span{
      font-size: .9rem;
      margin-right: 2rem;
    }
    .msg-information .name{
      font-size: 1.1rem;
      color: #000;
      font-weight: 700;
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
      margin-top: 1rem;
      // min-height: 100px;
      padding-bottom: 1rem;
    }
    .msg-do{
      font-size: .85rem;
      color: var(--gray);
      font-weight: 400;
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
    .msg-reply{
      margin-top: .5rem;
    }
    .msg-sendReply{
      margin-top: .5rem;
    }
  }

  img{
    width: 40px;
    height: 40px;
    margin-top: 2rem;
    border-radius: 50%;
  }
}

.blue{
  color: var(--blue);
}

</style>