<template>
  <div id="columnIndex">
    <div class="left">
      <div class="head"> 
        <div class="title">
          <h2>专栏列表</h2>
        </div>
      </div>

      <div class="content">
        <ul>
          <li v-for="item in columns" :key="item.id" :class="columnId==item.id?'active':'' " @click="changeIndex(item.id)" >{{item.name}} <span></span></li>
        </ul>
      </div>
    </div>
    <div class="right">
      <div class="riHead">
        <img style="width: 7rem;height: 7rem;border-radius: .5rem;" src="@/assets/head1.jpg" alt="">
        <div class="columnInfo">
          <h3 v-if="columns.length>0" >{{ columnInfo.name }}</h3>
          <span>文章数：{{ articles.length }}</span>
        </div>

        <div class="columnDo" v-if="isMe">
          <button @click="goManage()">管理文章</button>
          <button @click="clearColumn()" class="delete">清空专栏</button>
          <button @click="deleteColumn()" class="delete">删除专栏</button>
        </div>

      </div>

      <div class="riContent">
        <Article v-for="item in articles" :key="item.id" :article="item" ></Article>
      </div>
    </div>
  </div>
</template>

<script>
import Article from '@/components/Column/Article.vue'
import { ElMessageBox } from 'element-plus'
export default {
  data(){
    return {
      columnId: 0,
      userId: 0, 
      columnInfo: {},
      articles: [
        
      ],
      index: 0,
      columns: [],
    }
  },
  mounted(){
    this.columnId=this.$route.params.column;
    this.userId=this.$route.params.userId;
    this.getAllColumns();
    // this.getColumnArticle();
    this.getColumnInfo();

    window.addEventListener('click',() => {
      this.changeTitle=true;
    })

  },
  methods: {
    async getUserArticle(){
      let data ={
        'id': this.userId,
        'columnId': this.columnId
      }
      const {data: res} = await this.$http.post('/discussPost/showAllPosts' , this.qs.stringify(data));
      this.articles=res;
    },
    change(index){
      this.index=index;
    },
    changeIndex(id){
      this.columnId=id;
    },
    // 删除专栏
    async deleteColumn(){
      let data ={
        'id': this.columnId,
      }
      const {data: res} = await this.$http.post('/column-self/delColumn' , this.qs.stringify(data));
      this.getAllColumns();
      // this.articles=res;
    },
    async getColumnArticle(){
      let data ={
        'column_id': this.columnId,
      }
      const {data: res} = await this.$http.post('/column-self/showPosts' , this.qs.stringify(data));
      this.articles=res;
    },
    // 从专栏中删除某文章
    
    // 清空专栏
    async clearColumn(){
      if(this.articles.length===0){
        ElMessageBox.alert('该专栏已经是空的了！','提示消息')
        .catch(() => {
          // catch error
        })
      }else{
        ElMessageBox.confirm(
        '确认清空专栏？',
        '确认消息',
        )
        .then(() => {
          this.doClear();
        })
        .catch(() => {
          return ;
        })
      }
    },
    async doClear(){
      let data ={
        'column_id': this.columnId,
      }

      const {data: res} = await this.$http.post('/column-self/delPosts' , this.qs.stringify(data));

      // this.articles=res;
      this.getColumnArticle();
    }
    ,
    async getNoColumnArticles(){
      let data ={
        'id': this.userId,
        'identify': 1
      }

      const {data: res} = await this.$http.post('/discussPost/showAllPosts' , this.qs.stringify(data));

      this.articles=res;
    },
    addAr(){
      this.add=!this.add;
    },
    queryAr(){
      this.add=!this.add;
      this.getColumnArticle();
    },
    async getColumnInfo(){
      let data ={
        'id': this.columnId,
      }

      const {data: res} = await this.$http.post('/column-self/getColumnSelfById' , this.qs.stringify(data));

      this.columnInfo=res.data[0];
    },
    async updateColumn(){
      let data ={
        'id': this.columnId,
        'name': this.columnInfo.name,
        'columnId': this.columnId
      }

      const {data: res} = await this.$http.post('/column-self/update' , this.qs.stringify(data));

      this.articles=res;
    },
    //
    async getAllColumns(){
      let data ={
        'id': this.userId
      }

      const {data: res} = await this.$http.post('/column-self/showAll' , this.qs.stringify(data));

      this.columns=res;
      if(this.columns.length===0) this.articles=[];
    },
    goManage(){
      if(this.columns.length!=0){
        this.$router.push({name: 'columnManage',params: {column: this.columnId}});
      }else{
        ElMessageBox.alert('请创建专栏','提示消息')
        .catch(() => {
          // catch error
        })
      }
    }
  },
  components: {
    Article
  },
  watch: {
    columnId(newVal){
      this.getColumnInfo();
      this.getColumnArticle();
    }
  },
  computed: {
    isMe(){
      if(localStorage.getItem('id')===sessionStorage.getItem('visit')) return true;
      return false;
    }
  }
}
</script>

<style scoped lang="less">
#columnIndex{
  display: flex;
  max-height: 92vh;
  justify-content: space-between;
  padding: 1rem;
  .left{
    width: 20vw;
    .head{
      border-bottom: 1px solid var(--light--gray);
    }
    .content{
      min-height: 50vh;
      background: #fff;
      ul{
        
        li{
          position: relative;
          padding: .5rem 1rem;
          text-align: start;
          color: var(--gray);
          font-size: 1.2rem;
          cursor: pointer;
          span{
            position: absolute;
            top: 50%;right: 1rem;
            transform: translateY(-50%);
            color: var(--light--gray);
            font-size: .8rem;
          }
        }
        li:hover{
          color: var(--light--blue);
        }
        li.active{
          color: var(--light--blue);
        }
      }
    }
  }
  .right{
    background: #fff;
    width: 74vw;
    padding: 1rem;
    overflow: hidden;
  }
}
.title{
  display: flex;
  align-items: flex-end;
  justify-content: space-around;
  background: #fff;
  padding: 1rem;
  h2{
    font-size: 1.5rem;
    font-weight: 300;
  }
}

.doColumn{
  display: flex;
  justify-content: space-around;
}

.riHead{
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-bottom: 2px solid var(--background);
  div{
    min-height: 4rem;
    line-height: 4rem;
    span{
      color: var(--light--gray);
    }
  }
  .columnInfo{
    flex: 1;
  }
  .columnDo{
    flex: 5;
    text-align: end;
  }
  button{
    margin-left: 1rem;
    cursor: pointer;
    border: 1px solid var(--light--blue);
    border-radius: 1rem;
    color: var(--light--blue);
    background: #fff;
    padding: .5rem 1rem;
  }
  button:hover{
    background: var(--very-light-blue);
  }
  .delete{
    border: 1px solid var(--red);
    color: var(--red);
  }
  .delete:hover{
    background: var(--light-red );
  }
}
</style>