<template>
  <div id="header">
      <a @click.stop.prevent="goHome()" class="logo" ><span><i class="fas fa-graduation-cap"></i></span> Preschool.</a>

      <div class="box">
        <div class="animate" @mouseover="mouseover()" @mouseleave="mouseleave()" ref="node">{{ sentence }}</div></div>
      <div class="search" >
        <input v-if="searchFlag" v-model="searchContent" type="text" id="searchIn" placeholder="搜索"><button v-if="searchFlag" @click="goSearch()" id="searchBtn"><i class="fas fa-search"></i>&nbsp;搜索</button>
      </div>

      <div class="loginContainer">
          
        <div class="user"  v-if="loginFlag!=0">
          <span>
            <span style="font-size: 1.2rem;">
              <el-col :span="8">
                <el-dropdown>
                  <span class="el-dropdown-link">
                    <el-badge is-dot class="item" >
                      <i class="fas fa-exclamation-circle"></i>
                    </el-badge>
                    <!-- <i class="fas fa-exclamation-circle" v-else ></i> -->
                  </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item v-for="item in report" :key="item.id" :icon="Plus" @click="gotoBlog(item.reportIdTo,item.id,1)"><span v-if="item.id!==0" style="color: var(--red);"><i class="fas fa-exclamation"></i></span> {{ item.reportReason }}</el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </el-col>
            </span>
          </span>
          
          <span>
            <span style="font-size: 1.2rem;">
              <el-col :span="8">
                <el-dropdown>
                  
                    <span class="el-dropdown-link">
                      <el-badge is-dot class="item" >
                        <i class="fas fa-envelope"></i>
                      </el-badge>
                      <!-- <i class="fas fa-envelope" v-else></i> -->
                    </span>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <el-dropdown-item v-for="item in order" :key="item.id" :icon="Plus" @click="gotoBlog(item.discussPostId,item.id,2)"><span style="color: var(--yellow);"><i class="fab fa-bitcoin"></i>  </span> {{ item.id==0?'清空':'有人购买了你的博文!' }} </el-dropdown-item>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </el-col>
            </span>
          </span>

          <span @click.stop="showList()" ><img :src="headImg" alt=""></span>
        </div>
        <div class="btnContainer" v-else>
          <button class="login" @click="goLogin()">登录</button>
        </div>
        <MineList @hide="hide" @click.stop class="list" v-show="loginFlag!=0&&listFlag"></MineList>
      </div>

  </div>
</template>

<script>
import { mapState } from 'vuex'
import { mapActions } from 'vuex'

import MineList from '@/components/MineList'

import { ElMessageBox } from 'element-plus'

export default {
  data(){
    return {
      loginState: false,
      listFlag: false,
      searchContent: '',
	    url: '',
      report: [],
      order: [
        
      ],
      timer: 0,
      sentence: '‘煎’和‘熬’是可以变美味的方式，加油也是喔'
    }
  },
  computed: {
    ...mapState([
      'loginFlag',
      'username',
      'portrait',
      'searchFlag',
      'headImg'
    ])
  },
  created(){
    let state = localStorage.getItem("user");
	  this.url = localStorage.getItem("url");
    this.getSentence();
    document.addEventListener('click',() => {
      this.listFlag=false;
    });
    this.getReport();
    this.getOrder();
    setInterval(() => {
      this.getReport();
      this.getOrder();
    },10000);
  },
  methods: {
    goLogin(){
      this.$router.push('/log-reg');
    },
    goRegister(){
      this.$router.push('/register');
    },
    ...mapActions([
      'changeLoginFlag'
    ]),
    logout(){
      this.changeLoginFlag(0);
    },
    showList(){
      this.listFlag=true;
    },
    goSearch(){
      if(this.searchContent===''){
        ElMessageBox.alert('请输入搜索内容','提示消息')
        .catch(() => {
        })
      }else{
        this.$router.push({name: 'search', params:{content: this.searchContent,btype: -1}});
        this.searchContent = '';
      }
    },
    hide(){
      this.listFlag=false;
    },
    goHome(){
      this.$store.state.type=0;
			this.$store.state.subjectIndex=0;
      this.$router.push({name: 'home',params: {type: 0,subject: 0}})
    },
    async getReport(){
      let data ={
        user_id: Number(localStorage.getItem('id'))
      }
      const {data: res} = await this.$http.post('/reporting/getAll',this.qs.stringify(data));
      this.report=[];
      if(res.data.length>0) this.report.push({
        createTime: null,
        id: 0,
        processStatus: null,
        readed: null,
        reportIdFrom: null,
        reportIdTo: 8,
        reportInformation: null,
        reportReason: "清空"
      })
      this.report=this.report.concat(res.data);
    },
    async getOrder(){
      let data ={
        user_id: Number(localStorage.getItem('id'))
      }
      const {data: res} = await this.$http.post('/orders/getAll',this.qs.stringify(data));
      this.order=[];
      if(res.data.length>0) this.order.push({
        id: 0,
        discussPostId: 1,
      })
      this.order = res.data;
    },
    gotoBlog(blogID,id,n){
      if(id===0&&n===1){
        this.clearReport();
        this.getReport();
        return;
      }else if(id===0&&n===2){
        this.clearOrder();
        this.getOrder();
        return;
      }
      if(n==1){
        this.updateReport(id);
        this.getReport();
      }
      else{
        this.updateOrder(id);
        this.getOrder();
      }
      this.$router.push({name: 'blog', params:{id: blogID}});
    },
    // http://poetry.apiopen.top/sentences
    async getSentence(){
      const {data: res} = await this.$http.get('https://api.oick.cn/dutang/api.php');
      this.sentence=res;
    },
    async clearReport(){
      let data ={
        user_id: Number(localStorage.getItem('id'))
      }
      const {data: res} = await this.$http.post('/reporting/clearAll',this.qs.stringify(data));
      this.getReport();  
    },
    async clearOrder(){
      let data ={
        user_id: Number(localStorage.getItem('id'))
      }
      const {data: res} = await this.$http.post('/orders/clearAll',this.qs.stringify(data));
      this.getOrder();
    },
    async updateReport(id){
      let data ={
        id: id
      }
      const {data: res} = await this.$http.post('/reporting/updateReaded',this.qs.stringify(data));
      
    },
    async updateOrder(id){
      let data ={
        id: id
      }
      const {data: res} = await this.$http.post('/orders/updateReaded',this.qs.stringify(data));
      
    }
  },
  components: {
    MineList
  }
}
</script>

<style scoped lang="less">

#header {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;

    position: fixed;
    left: 0;right: 0;top: 0;
    z-index: 1000 !important;

    color: #2c3e50;
    height: 4rem;
    /* border: 1px solid red; */

    display: flex;
    justify-content: space-around;
    align-items: center;

    /* box-shadow: 0 .5rem 1.5rem rgba(0, 0, 0 , .1); */
    background: #fff;
    border-bottom: .08rem solid #EDECF4;
}
.logo {
  cursor: pointer;
  flex: 2;
  color: var(--black);
  font-size: 1.7rem;
}
.logo span{
  font-size: 2.5rem;
  color: var(--blue);
}
/* nav{
  transform: translateX(-50%);
} */
nav a {
  color: var(--light-color);
  font-size: 1.3rem;
  margin-right: 2rem;
}

nav a:hover {
  color: var(--blue);
}

.logo{
  flex: 1;
}
.loginContainer{
  flex: 0.5;
}

.loginContainer{
  position: relative;
  .list{
    margin-top: .5rem;
    position: absolute;
    top: 110%;
    right: 50%;
    transform: translateX(50%);
  }
}

nav {
  flex: 3.5;
}

.register{
  margin-left: 2rem;
}

.user{
  display: flex;
  justify-content: flex-start;
  align-items: center;
  img{
    width: 40px;height: 40px;
    border-radius: 50%;
  }
  span{
    margin-right: 1rem;
    cursor: pointer;
  }

  
}

.search{
  flex: 2;
}

#searchIn{
  border: none;
  background: var(--background);
  color: #000;
  font-size: 0.9rem;
  width: 15rem;
  height: 1rem;
  padding: .7rem;
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  outline: none;
  border: 1px solid transparent;
  border-top-left-radius: 1.7rem;
  border-bottom-left-radius: 1.7rem;
}

#searchIn:focus{
  border-top: 1px solid var(--light--blue);
  border-bottom: 1px solid var(--light--blue);
  border-left: 1px solid var(--light--blue);
}

#searchBtn{
  height: 2.5rem;
  width: 5rem;
  padding: .5rem;
  border: 1px solid transparent;
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  background: var(--blue);
  color: #fff;
  border-end-end-radius: .2rem;
  border-start-end-radius: .2rem;
  border-top-right-radius: 1.7rem;
  border-bottom-right-radius: 1.7rem;
}

#searchBtn:hover{
  background: var(--light--blue);
}

.login{
  padding: .5rem;
  font-size: .9rem;
  width: 5rem;
  background: var(--blue);
  border: none;
  box-shadow: 0 .1rem .1rem rgba(0, 0, 0 , .1);
  color: #fff;
}
.login:hover{
  background: var(--light--blue);
}
.el-dropdown-link{
  font-size: 1.2rem;
  color: var(--blue);
}

.box {
  flex: 1;
  margin: 0 auto;
  overflow: hidden;
}

.animate {
  overflow: hidden;
  padding-left: 20px;
  font-size: 1.2rem;
  color: #fff;
  font-family: serif;
  background-image: linear-gradient(to right ,#000046,#1CB5E0,#20e3b2);
  background-clip: text;
  -webkit-text-fill-color: transparent;
  display: inline-block;
  white-space: nowrap;
  animation: 20s wordsLoop linear infinite normal;
}


@keyframes wordsLoop {
  0% {
      transform: translateX(100%);
      -webkit-transform: translateX(100%);
  }
  100% {
      transform: translateX(-100%);
      -webkit-transform: translateX(-100%);
  }
}


</style>