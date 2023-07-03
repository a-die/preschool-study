<template>
  <div class="comment">
    <div class="textDiv">
      <textarea v-model.trim="comment" class="go-remark" name="" id="sendComment" cols="20" rows="10" placeholder="请输入评论"></textarea>
    </div>
    <div class="btnDiv">
      <span>总共有{{ commentLength }}条评论</span>
      <button @click="sendComment()"><i class="far fa-edit"></i> 发表评论</button>
    </div>
    <div class="commentDiv">
      <Remark :blogOwn="userId" v-for="item in remarks" :remark="item" :key="item.id"></Remark>
    </div>
  </div>
</template>

<script>
import Remark from '@/components/Blog/Remark';
import { ElMessageBox } from 'element-plus'

export default {
  inject: ['refreshBlog','load'],
  props: ['item','commentLength','blogOwn'],
  data(){
    return {
      remark: 5,
      id: 0,
      remarks: [
        {
          id: 1,
          img: require('@/assets/logo.png'),
          name: 'alex',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },{
          id: 2,
          img: require('@/assets/logo.png'),
          name: 'alex',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },{
          id: 3,
          img: require('@/assets/logo.png'),
          name: 'alex',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },{
          id: 4,
          img: require('@/assets/logo.png'),
          name: 'alex',
          date: () => new Date().toLocaleString,
          content: '我十分认同你的观点！！'
        },

      ],
      comment: '',
      userId: 0
    }
  },
  mounted(){
    // this.showReplies(this.item)
    this.getComment();
  },
  created(){
    this.id=this.$route.params.id;
  },
  methods: {
    // 获得博文所有的评论
    async getComment(){
      let data ={
        'discussPostId': this.id
      }
      const {data: res} = await this.$http.post('/comment/showComments',this.qs.stringify(data));
      this.remarks = res;
      
    },

    async sendComment(){

      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')==null){
        ElMessageBox.alert('请先登录！','提示消息')
        .catch(() => {
          // catch error
        })
      }else if(this.comment==''){
        ElMessageBox.alert('请输入内容','提示消息')
        .catch(() => {
          // catch error
        })
      }
      else{
        // /comment/comment
        let data ={
          'content': this.comment,
          'discussPostId': this.id,
          'userIdFrom': localStorage.getItem('id')
        }
        const {data: res} = await this.$http.post('/comment/comment',this.qs.stringify(data));
        if(res.code===0){
          this.comment='';
          this.refreshBlog();
          this.getComment();
        }
        this.load();
      }
      
    },
    // 回复评论
    async reply(comment_id){
      const {data: res} = await this.$http.get('/reply' , {
        params: {// 后续补充
          // user_id_from: this.id,
          // user_id_to: this.user_id_from,
          // content: this.content,
          // comment_id: 1
        }
      });
      //   console.log(res);
    },
  },
  components: {
    Remark
  },
  watch: {
    blogOwn(newVal,oldVal){
      this.userId=newVal;
    }
  }
}
</script>

<style scoped lang="less"> 
.comment{
  background: #fff;
  padding: 1rem;
  // padding-top: 5rem;

  #sendComment{
    width: 96%;
    border: .15rem solid transparent;
    cursor: pointer;
    resize: none;
    padding: 1rem;
    font-size: 1rem;
    border-radius: .4rem;
    background: rgb(243, 244, 245);
  }
  #sendComment:hover{
    box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.15);
  }
  #sendComment:focus{
    border: .15rem solid #A4CCF4;
    // 去除选中后的框
    outline: none;
    background: #fff;
  }
  .btnDiv{
    display: flex;
    justify-content: space-between;
    align-items: center;
    button{
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
}
.commentDiv{
  margin-top: 2rem;
  border-top: .15rem solid rgb(243, 244, 245);
}
</style>