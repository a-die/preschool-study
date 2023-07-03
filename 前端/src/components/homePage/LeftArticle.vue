<template>
  <div class="RightArctle">
    <Article @good="good" v-for="(item,index) in article" :key="item.id" :index="index" :item="item" @click="checkPay(item.id,index)"></Article>
    <RightContent class="list"></RightContent>

    <!-- <el-button type="text" @click="outerVisible = true">点击提醒付费</el-button> -->

    <el-dialog v-model="outerVisible" title="提醒" width="35%">
      <span>阅读该文章需要支付 {{ article[payIndex].price }} CC币</span>
      
      <template #footer>
        <div class="dialog-footer" >
          <el-button @click="outerVisible = false">取消</el-button>
          <el-button type="primary" @click="remindPay()">支付CC币</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="innerVisible" title="支付结果" width="30%">
      <span>{{ payResult }}</span>
      <template #footer>
        <div class="dialog-footer" >
          <el-button v-if="payFlag" type="primary" @click="goBl()">进入</el-button>
          <el-button type="primary" @click="outerVisible = false;innerVisible = false;">关闭</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import Article from '@/components/homePage/Article.vue'
import RightContent from '@/components/homePage/RightContent.vue'
import { mapState } from 'vuex'

import { ElMessageBox } from 'element-plus'

export default {
  inject: ['remind','load'],
  data(){
    return {
      article: [
      ],
      outerVisible: false,
      innerVisible: false,
      payResult: '',
      payIndex: 0,
      payFlag: false
    }
  },
  computed: {
    ...mapState([
      'type',
      'subjectIndex'
    ])
  },
  components:{
    Article,
    RightContent
  },
  methods: {
    async getArticle(){
      let data ={
        'btype': this.type,
        'subject': this.subjectIndex
      }

      const {data: res} = await this.$http.post('/discussPost/showPosts',this.qs.stringify(data));

      this.article=res;
    },
    gotoBlog(id,index){
      // 判断是否已经购买
      if(this.article[index].isFree===0){  
        // console.log(this.);
        this.outerVisible = true;
      }
      else{
        this.$router.push({name: 'blog', params:{id: id}});
      }
      // console.log("blog");
    },
    good(index,n){

      this.article[index].up_count+=n;

    },
    async remindPay(){
      this.innerVisible = true;this.outerVisible = false;
      // if(this.article[payIndex].ccNum>)
      let data ={
        'userId': localStorage.getItem('id'),
        'discussPostId': this.article[this.payIndex].id
      }
      const {data: res} = await this.$http.post('/orders/addOrders',this.qs.stringify(data));

      if(res.code===0){
        this.payResult='支付成功！';
        this.payFlag=true;
      }else{
        this.payResult='支付失败，请多多赚取CC币！';
      }
    },
    goBl(){
      this.outerVisible = false;this.innerVisible = false;
      this.$router.push({name: 'blog', params:{id: this.article[this.payIndex].id}});
    },
    async checkPay(id,index){
      this.payIndex=index;
      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')===null){
        ElMessageBox.alert('该文章需支付CC币,请登录！','提示消息')
        .catch(() => {
          // catch error
        })
      }else{
        // this.innerVisible = true;this.outerVisible = false;
        // if(this.article[payIndex].ccNum>)
        let data ={
          'userId': localStorage.getItem('id'),
        }
        const {data: res} = await this.$http.post('/orders/getOrdersByUserId',this.qs.stringify(data));
        
        let len = res.data.filter(item => item.discussPostId===id).length;

        if(len>0||(this.article[index].user_id===Number(localStorage.getItem('id')))) this.$router.push({name: 'blog', params:{id: id}});
        else{
          this.gotoBlog(id,index);
        }
      }
    },
  }
  ,
  mounted(){
    // 请求某一类型的博文

    this.getArticle();
  },
  watch:{
    subjectIndex(){
      this.article=[];
      setTimeout(() => {this.getArticle()},1000);
      this.load();
    },
    type(){
      this.article=[];
      setTimeout(() => {this.getArticle()},1000);
      this.load();
    }
  }
}
</script>

<style scoped lang="less">
.RightArctle{
  position: relative;
  padding-top: 2.5rem;
  display: flex;
  justify-content:flex-start;
  flex-wrap: wrap;
  width: 80%;
}
.list{
  position: absolute;
  top: 2.5rem;
  right: 0;
  width: 22%;
}
</style>