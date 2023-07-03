<template>
  <div id="test" :style="'background-image:' + backgroundImg + ';'">
    <div class="container right-panel-active">
      <!-- 注册 -->
      <div class="container_from container_signup">
        <form action="" class="from" id="form1">
          <h2 class="from_title">注册账号</h2>
          <input type="text" placeholder="UserName" class="input">
          <input type="password" placeholder="Password" class="input">
          <input type="email" placeholder="Email" class="input">
          <input type="number" placeholder="PhoneNumber" class="input">
          <span><input type="radio" name="sex" value="0">男 <input name="sex" type="radio" value="1">女</span>
          <input 
          class="input" 
          placeholder="Birthday" 
          ref="date" type="date"
          @focus="this.$refs.date.removeAttribute('placeholder')"
          @blur="this.$refs.date.setAttribute('placeholder')" > 
          <button class="btn">点击注册</button>
        </form>
      </div>

      <!-- 登录 -->
      <div class="container_from container_signin">
        <form action="" class="from" id="form2">
          <h2 class="from_title">欢迎登陆</h2>
          <input type="email" placeholder="Email" class="input">
          <input type="password" placeholder="Password" class="input">
          <a href="#" class="link">忘记密码？</a>
          <button class="btn">登录</button>
        </form>
      </div>

      <div class="container_overlay">
        <div class="overlay" :style="'background-image: ' + backgroundImg + ';'">
          <div class="overlay_panel overlay_left">
            <button class="btn" id="signin">已有账号，直接登录</button>
          </div>

          <div class="overlay_panel overlay_right">
            <button class="btn" id="signup">没有账号，点击注册</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { mapMutations } from 'vuex'
import { mapState } from 'vuex'

export default {
  data(){
    return {
      name: '',
      backgroundImg: "url(" + require("@/assets/background.jpg") + ")",
      form:{
        username: "",
        password: "",
        email: "",
        sex: '1',
        birthday: '',
        phone: ''
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
    ...mapMutations(['setHeadTrue','setHeadFalse'])
  },
  methods: {
    show(){
      this.$store.commit('setHeadTrue')
    },
    hide(){
      this.$store.commit('setHeadFalse')
    }
  }
}
</script>

<style scoped >


#test{
  /* height: calc(100vh - 4rem); */
  align-items: center;
  background-color: var(--white);
  /* background: url(../assets/background.jpg); */
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: grid;
  height: 100vh;
  place-items: center;
}

/* body{
  align-items: center;
  background-color: var(--white);
  background: url(../assets/background.jpg);
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: grid;
  height: 100vh;
  place-items: center;
} */

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

.input {
  background-color: #fff;
  border: none;
  padding: 0.9rem 0.9rem;
  margin: 0.5rem 0;
  width: 100%;
}

input[type="date"]:before {
	content: attr(placeholder); 
	color: #aaa; 
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