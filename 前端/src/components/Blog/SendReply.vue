<template>
  <div :class="[{'back':back}]" id="sendReply" @click.stop>
    <div>
      <textarea v-model="comment" placeholder="请输入回复" ref="sendReply" name="sendReply" id="sendReply-input" cols="20" rows="1" ></textarea>
    </div>
    <div class="sendReply-do">
      <div></div>
      <button @click.prevent.stop="sendReply()">发表回复</button>
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from 'element-plus'
export default {
  inject: ['load'],
  props: ['back','id','userIdFrom'],
  emits: ['updated'],
  data(){
    return {
      comment: ''
    }
  },
  mounted(){
    this.$refs.sendReply.focus();
    // console.log(this.back);
  },
  methods: {
    async sendReply(){
      let data;

      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')==null){
        ElMessageBox.alert('请先登录！','提示消息')
        .catch(() => {
          // catch error
        })
        return ;
      }else if(this.comment=='') {
        ElMessageBox.alert('请输入内容','提示消息')
        .catch(() => {
          // catch error
        });
        return;
        }
      else if(this.back){
        data ={
          'comment_id': this.id,
          'user_id_to': this.userIdFrom,
          'user_id_from': Number(localStorage.getItem('id')),
          'content': this.comment
        }
      }else{
        data ={
          'comment_id': this.id,
          'user_id_to': this.userIdFrom,
          'user_id_from': Number(localStorage.getItem('id')),
          'content': this.comment
        }
      }
      const {data: res} = await this.$http.post('/comment-reply/reply',this.qs.stringify(data));

      if(res.code===0){
        this.comment='';
        this.$emit('updated');
      }
      this.load();
    }
    
  }
}
</script>

<style scoped lang="less">
#sendReply{
  background: #fff;
  padding: 1rem;

  #sendReply-input{
    outline: none ;
    width: 96%;
    border: .15rem solid #A4CCF4;
    cursor: pointer;
    resize: none;
    padding: .2rem;
    font-size: .8rem;
    border-radius: .4rem;
    background: #fff;
    height: 3rem;
    // overflow-y: hidden;
  }

  
  .sendReply-do{
    width: 97%;
    display: flex;
    justify-content: space-between;
    button{
      padding: .45rem;
      font-size: .6rem;
      color: #fff;
      background: var(--blue);
      border: none;
    }
    button:hover{
      background: var(--light--blue);
    }
  }
}
#sendReply.back{
  background: var(--background);
}
</style>