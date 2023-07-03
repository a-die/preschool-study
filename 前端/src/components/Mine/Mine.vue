<template>
  <div id="mine">
    <div class="container">
      <div class="backImg">

        <div class="backChoice" @click.stop.prevent="showBack()">
          <span><i class="fas fa-brush"></i></span>
        </div>

        <div class="img" :style="leftBg">

        </div>
        <div class="userInfo">
          <img :src="user.url" alt="">
          <div class="userInformation">
            <div class="userName">
              <span>{{ user.username }}</span>
              <span class="sex"><i :class="['fas',sex==1?'fa-mars':'fa-venus']"></i></span>
              <span class="level">lv 1</span>
            </div>
            <div class="person-signature">
              <div class="signature" v-if="changeSignature">{{ finalSignature }}</div>
              <button v-if="changeSignature" v-show="isMe" @click.stop="writeSignature()"><i class="fas fa-pen"></i>&nbsp;修改签名</button>
              <input type="text" ref="signature" v-else v-model.lazy="signature" @click.stop="">
            </div>
          </div>
        </div>
      </div>

      <div class="Mine-page">
        <div class="mine-nav">
          <ul>
            <li :class="{'active':(minePage==1)}" @click="changeMinePage(1)">
              <router-link to="/mine/mypage">主页</router-link>
            </li>
            <li :class="{'active':(minePage==2)}" @click="changeMinePage(2)">
              <router-link to="/mine/myarticle">文章</router-link>
            </li>
            <li :class="{'active':(minePage==3)}" @click="changeMinePage(3)">
              <router-link to="/mine/mycolumn">专栏</router-link>
            </li>
            <!-- <li :class="{'active':(minePage==4)}" @click="changeMinePage(4)">
              <router-link to="/mine/mystudy">学习</router-link>
            </li>
            <li :class="{'active':(minePage==5)}" @click="changeMinePage(5)">
              <router-link to="/mine/mytalking">说说</router-link>
            </li> -->
            <li v-if="isMe" :class="{'active':(minePage==4)}" @click="changeMinePage(4)">
              <router-link to="/mine/mysetting">设置</router-link>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <div>
      <router-view class="show">

      </router-view>
    </div>

    <div class="background" v-if="backFlag" @click.prevent>
      <div class="backCover">
      </div>

      <div class="backChoiceChange" @click.stop>
        <div @click="nowBack=index;" v-for="(item,index) in backUrl" :key="item.id" :style="`background: center / cover no-repeat url(`+item.url+`)`" >
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import { imageEmits } from 'element-plus'

// store
import { mapState } from 'vuex'
import { mapActions } from 'vuex'
import headPortrait from '@/assets/head.png'

export default {
  provide() {
    return {
      refreshUser: this.getUserInfo
    }
  },
  data(){
    return {
      // 头像
      headPortrait: headPortrait,
      sex: 1,
      signature: 'TA很懒，什么都没有留下',
      changeSignature: true,
      backFlag: false,
      backUrl:[
        {
          id: 1,
          url: require('@/assets/back1.jpg')
        },
        {
          id: 2,
          url: require('@/assets/back.png')
        },
        {
          id: 3,
          url: require('@/assets/back2.jpg')
        },
        {
          id: 4,
          url: require('@/assets/back3.jpg')
        },
        {
          id: 5,
          url: require('@/assets/back4.jpg')
        },
        {
          id: 6,
          url: require('@/assets/back5.jpg')
        }
      ],
      nowBack: 0,
      isMe: false,
      user: {
        birthday: null,
        createTime: "2021-12-05T13:32:57.000+00:00",
        email: "2638826623@qq.com",
        id: 985669637,
        password: "E10ADC3949BA59ABBE56E057F20F883E",
        phonenumber: "123456",
        portrait_id: 0,
        sex: 0,
        signature: null,
        status: 0,
        type: 0,
        updateTime: "2021-12-05T13:32:57.000+00:00",
		url: "",
        username: "alex"}
    }
  },
  computed: {
    leftBg() {
      return {
        background: " center / cover no-repeat url(" + this.nowBackUrl + ")"
      }
    },
    nowBackUrl(){
      return this.backUrl[this.nowBack].url;
    },
    finalSignature(){
      if(this.user.signature===null){
        return 'TA很懒，什么都没有留下';
      }else if(this.user.signature.length>20){
        
        return this.user.signature.slice(0,20)+'...';
      }
      return this.user.signature;
    },
    ...mapState([
      'minePage'
    ]),
  },
  methods: {
    writeSignature(){
      this.changeSignature=!this.changeSignature;
      
      // this.$refs.signature.focus();
    },
    changePage(page){
      this.minePage=page;
    },
    ...mapActions([
      'changeMinePage'
    ]),
    changeBackFlag(){
        // 设置背景选择框消失情况
      let that=this;
      document.addEventListener('click', (e) => {
        that.backFlag=false;
      })
    },
    showBack(){
      this.backFlag=true;
    },
    async getUserInfo(){
      // /user/information

      let data ={
        'id': sessionStorage.getItem('visit')
      }

      const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));

      this.user=res;
      this.signature=res.signature;

    },
    
    async updateSignature(){
      // /user/information

      let data ={
        'birthday': this.user.birthday,
        'email': this.user.email,
        'id': this.user.id,
        'password': this.user.password,
        'phonenumber': this.user.phonenumber,
        'portrait_id': this.user.portrait_id,
        'sex': this.user.sex,
        'signature': this.signature,
        'status': this.user.status,
        'type': this.user.type,
        'username': this.user.username,
      }
      const {data: res} = await this.$http.post('/user/update' , this.qs.stringify(data));
      this.otherArticles=res;
      if(res.code===0) this.getUserInfo();
    }
  },
  mounted() {
    // 设置点击周围，修改框消失
    let that=this;
    this.changeBackFlag();
    document.addEventListener('click', (e) => {
      if(that.changeSignature==false){
        that.changeSignature=!that.changeSignature;
      }
    });
    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
    this.getUserInfo();
  },
  unmounted() {
    // sessionStorage.removeItem('visit');
    this.$store.replaceState({
        portrait: '@/assets/back5.jpg',
        headFlag: true,
        minePage: 1,
        mineArticle: 4,
        mineStudy: 1,
        mineSetting: 1,
        loginFlag: this.$store.state.loginFlag,
        username: '',
        type: 0,
        // portrait: require('@/assets/head.png')
        portrait: '@/assets/back5.jpg',
        searchFlag: true,
        subjectIndex: 0,
        headImg: this.$store.state.headImg
      })
  },
  watch: {
    user: {
      deep: true
    },
    changeSignature(newVal,oldVal){
      if(newVal&&this.signature!='TA很懒，什么都没有留下'){
        this.updateSignature();
      }
    }
  }
}
</script>

<style scoped lang="less">
#mine{
  // background: var(--blue);
  margin: 0 auto;
  width: 80%;
  margin-bottom: 2rem;

  .background{
    position: absolute;
    bottom: 0;left: 0;right: 0;
    width: 100%;height: 100%;
    
    .backCover{
      background: var(--light-yellow);
      opacity: 0.1;
      width: 100%;height: 100%;
      position: absolute;
      bottom: 0;left: 0;right: 0;
    }
    .backChoiceChange{
      opacity: 0.9;
      background: rgb(211, 206, 135);
      height: 40%;width: 93%;
      margin: 0 auto;
      overflow:auto;
      position: absolute;
      bottom: 0;
      left: 0;right: 0;
      padding: 3rem;
      display: flex;
      flex-wrap: wrap;

      div{
        width: 47.5%;height: 150px;
        margin-right: 2rem;
        margin-bottom: 2rem;
        border-radius: .8rem;
        box-shadow: 0 1px 2px 0 rgb(0 0 5%);
      }
      div:hover{
        transform: scale(1.02);
      }

    }
  }
  
  .container{
    box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
  }

  .backImg{
    // 
    position: relative;
    // margin: 0 auto;
    width: 100%;
    height: 15rem;
    // 设置背景图片模糊
    // filter: blur(1px);
    // 设置背景颜色的透明度
    // opacity: 0.7;

    .backChoice{
      position: absolute;
      top: 1rem;right: 1rem;
      width: 4rem;height: 2rem;
      font-size: 1.5rem;
      border-radius: .8rem;
      background: rgb(230, 221, 221);color: rgb(170, 170, 170);
      opacity: 0.7;
      // border: 1px solid var(--light-yellow);
      span{
        opacity: 1;
        color: var(--light-red);
      }
    }
    .backChoice:hover{
      color: var(--blue);

      span{
        display: inline-block;
        opacity: 1;
        color: var(--light-blue);
      }
      
    }


    .img{
      z-index: -1 !important;
      position: absolute;
      width: 100%;
      height: 100%;
      // 设置背景图片模糊
      filter: blur(1px);
    // 设置背景颜色的透明度
      opacity: 0.7;
    }

    .userInfo{
      position: absolute;
      bottom: 1.5rem;left: 2rem;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      text-align: start;
      img{
        width: 80px;height: 80px;
        border-radius: 50%;
        border: 3px solid #fff;
      }
      .userInformation{
        margin-left: 1rem;
        color: #000;
      
        div{
          margin-top: .5rem;
        }
        .userName{
          font-size: 1.3rem;
          position: relative;
          // font-weight: 700;
          span{
            margin-right: .5rem;
          }
          .sex{
            display: inline-block;
            background: var(--blue);
            width: 20px;
            height: 20px;
            line-height: 20px;
            border-radius: 50%;
            text-align: center;
            color: #fff;
            font-size: .9rem;
          }
          .level{
            position: absolute;
            top: .35rem;
            right: 14rem;
            padding: .3rem;
            font-size: 3rem;
            font-weight: 900;
            background: #FCDC74;
            border-radius: 1rem;
            color: #fff;
            text-align: start;
            transform: scale(.2) translateY(-190%) translateX(-100%);
          }
        }

        .person-signature{
          display: flex;
          align-items: center;
          min-width: 24rem;
          max-width: 24rem;

          button{
            border-radius: .2rem;
            padding: .2rem;
            font-size: .6rem;
            color: #fff;
            background: var(--blue);
            border: none;
            margin-left: 1rem;
          }
          button:hover{
            background: var(--light--blue);
          }
          .signature{
            max-width: 15rem;
            font-size: .9rem;
            margin-top: 0;
          }
          input{
            width: 16rem;
            height: 1.3rem;
            font-size: .6rem;
            outline: none;
          }
          input:focus{
            outline: none ;
            border: .05rem solid #A4CCF4;
            cursor: pointer;
            resize: none;
            // border-radius: .4rem;
            background: #fff;
          }
        }

        
      }
    }
  }
  .Mine-page{
    // box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
    // background: #fff;
    // height: 200px;
    .mine-nav{

      ul{
        font-size: .9rem;
        background: #fff;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        li{
          font-size: 1rem;
          cursor: pointer;
          margin-left: 1rem;margin-right: 1rem;
          margin-bottom: .2rem;
          width: 60px;
          height: 50px;
          line-height: 50px;
          border-bottom: .15rem solid transparent;
          a{
            display: inline-block;
            width: 100%;height: 100%;
            color: var(--very-soft-black);
          }
        } 
        li.active{
          border-color: var(--light--blue);
          font-weight: 700;
          // background: var(--background);
          a{
            color: var(--light--blue);
          }
          color: var(--light--blue);
        }
        li:hover{
          // border-color: var(--light--blue);
          // background: var(--background);
          color: var(--light--blue);
          a{
            color: var(--light--blue);
          }
        }
      }
    }

  }
  .show{
    margin-top: 1rem;
  }
}
</style>