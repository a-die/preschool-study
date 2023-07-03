<template>
  <div id="Log-Reg" :style="'background-image:' + backgroundImg + ';'">
    <div ref="container" class="container">
      <!-- 注册 -->
      <div class="container_from container_signup">
        <form action="" class="from" id="form1" autocomplete="off">
          <h2 class="from_title">注册账号</h2>
          <span>
            <input type="text" placeholder="UserName" v-model="registerData.username" class="input">
            <span class="check">
              <span class="wrong" v-show="registerFlag.UserNameFlag==1"><i class="far fa-times-circle"></i></span>
              <span class="correct" v-show="registerFlag.UserNameFlag==2"><i class="far fa-check-circle"></i></span>
            </span>
            
          </span>
          <span >
            <input type="password" placeholder="Password" v-model="registerData.password" class="input">
            <span class="check">
              <span class="wrong" v-show="registerFlag.PasswordFlag==1"><i class="far fa-times-circle"></i></span>
              <span class="correct" v-show="registerFlag.PasswordFlag==2"><i class="far fa-check-circle"></i></span>
            </span>
          </span>
          <span>
            <input type="email" placeholder="Email" v-model="registerData.email" class="input">
            <span class="check">
              <span class="wrong" v-show="registerFlag.EmailFlag==1"><i class="far fa-times-circle"></i></span>
              <span class="correct" v-show="registerFlag.EmailFlag==2"><i class="far fa-check-circle"></i></span>
            </span>
          </span>
          <span>
            <input type="text" placeholder="PhoneNumber" v-model.lazy="registerData.phonenumber" class="input">
            <span class="check">
              <span class="wrong" v-show="registerFlag.PhoneNumberFlag==1"><i class="far fa-times-circle"></i></span>
              <span class="correct" v-show="registerFlag.PhoneNumberFlag==2"><i class="far fa-check-circle"></i></span>
            </span>
          </span>
          <button class="btn" @click.stop.prevent="register()">点击注册</button>
        </form>
      </div>

      <!-- 登录 -->
      <div class="container_from container_signin">
        <form action="" class="from" id="form2" autocomplete="off">
          <h2 class="from_title">欢迎登陆</h2>
          <span >
            <input type="text" v-model="loginData.phonenumber" placeholder="PhoneNumber" class="input">
            <span class="check">
              <span class="wrong" v-show="loginFlag.PhoneNumberFlag==1"><i class="far fa-times-circle"></i></span>
              <span class="correct" v-show="loginFlag.PhoneNumberFlag==2"><i class="far fa-check-circle"></i></span>
            </span>
          </span>
          <span >
            <input type="password" v-model="loginData.password" placeholder="Password" class="input">
            <span class="check">
              <span class="wrong" v-show="loginFlag.PasswordFlag==1"><i class="far fa-times-circle"></i></span>
              <span class="correct" v-show="loginFlag.PasswordFlag==2"><i class="far fa-check-circle"></i></span>
            </span>
          </span>
          <a href="#" class="link">忘记密码？</a>
          <button class="btn" @click.stop.prevent="login()">登录</button>
        </form>
      </div>

      <div class="container_overlay">
        <div class="overlay" :style="'background-image: ' + backgroundImg + ';'">
          <div class="overlay_panel overlay_left">
            <button class="btn" @click="goLogin()" id="signin">已有账号，直接登录</button>
            <button style=" background: linear-gradient(to left, #24c6dc, #514a9d);margin-top: 2rem;" class="btn" @click="goHome()" id="signin">返回主页，随便逛逛</button>
          </div>

          <div class="overlay_panel overlay_right">
            <button class="btn" @click="goRegister()" id="signup">没有账号，点击注册</button>
            <button style="  background: linear-gradient(to left, #24c6dc, #514a9d); margin-top: 2rem;" class="btn" @click="goHome()" id="signin">返回主页，随便逛逛</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { mapMutations } from 'vuex'
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import { ElMessageBox } from 'element-plus'

export default {
  data(){
    return {
      name: '',
      backgroundImg: "url(" + require("@/assets/back5.jpg") + ")",
      loginData:{
        phonenumber: "",
        password: "",
      },
      registerData:{
        username: "",
        password: "",
        email: "",
        phonenumber: ''
      },
      loginFlag: {
        PhoneNumberFlag: 0,
        PasswordFlag: 0
      },
      registerFlag: {
        UserNameFlag: 0,
        PasswordFlag: 0,
        EmailFlag: 0,
        PhoneNumberFlag: 0
      }
    }
  },
  computed: {
    ...mapState(['headFlag']),
    ...mapMutations(['setHeadTrue','setHeadFalse']),
    phonenumber(){
      return this.registerData.phonenumber;
    }
  },
  methods: {
    ...mapActions([
      'changeLoginFlag',
      'changeUserName',
      'changePortrait'
    ]),
    goRegister(){
      this.registerFlag.UserNameFlag=0;
      this.registerFlag.PasswordFlag=0;
      this.registerFlag.EmailFlag=0;
      this.registerFlag.PhoneNumberFlag=0;
      this.$refs.container.classList.add("right-panel-active");
    },
    goLogin(){
      this.loginFlag.PhoneNumberFlag=0;
      this.loginFlag.PasswordFlag=0;
      this.$refs.container.classList.remove("right-panel-active");
    },
    async login(){// 登录方法
      if(this.loginData.password===''||this.loginData.phonenumber===''){
        ElMessageBox.alert('信息不全！请填写完整！','提示消息')
        .catch(() => {
        })
        return ;
      }
      let data ={
          'password': this.loginData.password,
          'phonenumber': this.loginData.phonenumber
      }

      const {data: res} = await this.$http.post('/user/login',this.qs.stringify(data));
      if(res.code===0){
        // 设置全局域的用户信息
        localStorage.setItem('id',res.data.id);
		    localStorage.setItem('url',res.data.url);
        this.getUserInfo();
        this.changeLoginFlag(1);
        this.changeUserName(res.data.username);
        this.goHome();
        this.$store.state.headImg = res.data.url;
      }else{
        ElMessageBox.alert('账号或密码错误','提示消息')
        .catch(() => {
        })
        this.loginData.password = ''
      }
    },
    async getUserInfo(){
      let data ={
        'id': localStorage.getItem('id')
      }
      const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));
      localStorage.setItem('ccNum',res.ccNum);
    },
    async register(){
      var reg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      if(this.registerData.username===''||this.registerData.password===''||this.registerData.phonenumber===''){
        ElMessageBox.alert('信息不全','提示消息')
        .catch(() => {
        })
        return ;
      }
      if(!reg.test(this.registerData.email)){
        ElMessageBox.alert('邮箱格式错误！','提示消息')
        .catch(() => {
        })
        return ;
      }

      let params = {
        'username': this.registerData.username,
        'password': this.registerData.password,
        'email': this.registerData.email,
        'phonenumber': this.registerData.phonenumber
      }
      const {data: res} = await this.$http.post('/user/register' ,this.qs.stringify(params));
      ElMessageBox.confirm(
        '注册成功',
        '确认消息',
      )
      .then(() => {
        setTimeout(() => {
          this.goLogin()
        },1000);
      })
      .catch(() => {
        return ;
      })
      
    },
    goHome(){
      setTimeout(() => {
        this.$router.replace('/');
      },1000);
    },
    async checkPhone(){
      let params = {
        'phonenumber': this.registerData.phonenumber
      }
      const {data: res} = await this.$http.post('/user/checkPhone' ,this.qs.stringify(params));
      if(res.code===0) this.registerFlag.PhoneNumberFlag=2;
      else this.registerFlag.PhoneNumberFlag=1;
    },
  },
  watch: {
    registerData: {
      phonenumber(newVal, oldVal){
        this.checkPhone();
      },
      deep: true
    },
    phonenumber(newVal, oldVal){
      this.checkPhone();
    }
  },
  mounted(){
    this.$store.commit('setHeadFalse');
  },
  unmounted() {
    this.$store.commit('setHeadTrue');
  },
}
</script>

<style scoped >
#Log-Reg{
  height: 100vh;
  align-items: center;
  background-color: var(--white);
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: grid;
  place-items: center;
}

.from_title{
  font-weight: 300;
  margin: 0;
  margin-bottom: 1.25rem;
}

.link {
  color: var(--gray);
  font-size: 0.9rem;
  margin: 1.5rem 0;
  text-decoration: none;
}

.container{
  background-color: var(--white);
  border-radius: var(--button-radius);
  box-shadow: 0 0.9rem 1.7rem rgba(0,0,0,0.25);
  height: var(--max-height);
  max-width: var(--max-width);
  overflow: hidden;
  position: relative;
  width: 100%;
}

.container_from{
  height: 100%;
  position: absolute;
  top: 0;
  transition: all 0.6s ease-in-out;
}

.container_signin{
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.right-panel-active .container_signin{
  transform: translateX(100%);
}

.container_signup{
  left: 0;
  opacity: 0;
  width: 50%;
  z-index: 1;
}

.container.right-panel-active .container_signup{
  animation: show 0.6s;
  opacity: 1;
  transform: translateX(100%);
  z-index: 5;
}

.container_overlay{
  height: 100%;
  left: 50%;
  overflow: hidden;
  position: absolute;
  top: 0;
  transition: transform 0.6s ease-in-out;
  width: 50%;
  z-index: 100;
}

.container.right-panel-active .container_overlay{
  transform: translateX(-100%);
}

.overlay{
  background-color: var(--lightblue);
  /* background: url(background.jpg); */
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  /* background-size: contain; */
  height: 100%;
  left: -100%;
  position: relative;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
  width: 200%;
}

.container.right-panel-active .overlay{
  transform: translateX(50%);
}

.overlay_panel{
  align-items: center;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  position: absolute;
  text-align: center;
  top: 0;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
  width: 50%;
}

.overlay_left{
  transform: translateX(-20%);
}

.container.right-panel-active .overlay_left{
  transform: translateX(0);
}

.overlay_right{
  right: 0;
  transform: translateX(0);
}

.container.right-panel-active .overlay_right{
  transform: translateX(20%);
}

.btn{
  background-color: var(--bluee);
  background-image: linear-gradient(90deg,var(--bluee) 0%, var(--lightblue) 74%);
  border-radius: 20px;
  border: 1px solid var(--bluee);
  color: var(--white);
  cursor: pointer;
  font-size: 0.8rem;
  font-weight: bold;
  letter-spacing: 0.1rem;
  padding: 0.9rem 4rem;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

.form > .btn {
  margin-top: 1.5rem;
}

.btn:active {
  transform: scale(0.95);
}

.btn:focus {
  outline: none;
}

.from {
  background-color: var(--white);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 3rem;
  height: 100%;
  text-align: center;
}

span{
  width: 100%;
}

.wrong{
  color: var(--red);
}
.correct{
  color: var(--green);
}
.check{
  display: inline-block;
  width: .1rem;
}

.input {
  color: var(--soft-black);
  background-color: #fff;
  border: none;
  padding: 0.9rem 0.9rem;
  margin: 0.5rem 0;
  /* margin-right: 1rem; */
  width: 70%;
}

/* 去除数字框的增加减少键 */
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none;
}
input[type="number"]{
    -moz-appearance: textfield;
}


@keyframes show {
  0%,
  49.99%{
    opacity: 0;
    z-index: 1;
  }
  50%,
  100%{
    opacity: 1;
    z-index: 5;
  }
}


</style>