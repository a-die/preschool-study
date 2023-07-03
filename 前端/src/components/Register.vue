<template>
  <div id="register">
      
      <el-form ref="form" :model="form" label-width="6rem" class="form">
        <h3 class="title">注册</h3>
        <el-form-item label="username：">
            <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="password：">
            <el-input v-model="form.password" placeholder="请输入密码" type="password"></el-input>
        </el-form-item>
        <el-form-item label="email：">
            <el-input v-model="form.email" placeholder="请输入邮箱" type="email"></el-input>
        </el-form-item>
        <el-form-item label="phone：">
            <el-input v-model="form.phone" placeholder="请输入手机号" type="email" ></el-input>
        </el-form-item>
        <el-form-item label="sex：">
            <el-radio v-model="form.sex" label="0">男</el-radio>
            <el-radio v-model="form.sex" label="1">女</el-radio>
        </el-form-item>
        <el-form-item label="birthday">
          <el-date-picker
            v-model="form.birthday"
            type="datetime"
            placeholder="Select date and time">
          </el-date-picker>
        </el-form-item>
        <el-button type="primary" plain @click="register()">注册</el-button>
        <el-button type="success" plain @click="goto()">登录</el-button>
      </el-form>
  </div>
</template>

<script>
export default {
    data(){
        return {
            form:{
                username: "",
                password: "",
                email: "",
                sex: '1',
                birthday: '',
                phone: ''
            }
        }
    },
    methods: {
        submit() {
            console.log(this.username,this.password);
        },
        goto(){
            this.$router.push('/login')
        },
        async register(){
          let params = {
            'username': this.form.username,
            'password': this.form.password,
            'email': this.form.email,
            'sex': Number(this.form.sex),
            // 'birthday': this.form.birthday,
            'phonenumber': this.form.phone
          }
          const {data: res} = await this.$http.post('/user/register' ,this.qs.stringify(params));
        }
    },
}
</script>

<style scoped lang="less">
    :root {
        --blue: #39BAE8;
        --black: #403E4E;
        --border: 1px solid #39BAE8;
    }
    #register{
        text-align: center;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-wrap: wrap;
        background: #fff;
        margin-top: 1rem;
        padding: 2rem;
    }
    .form{
        width: 24rem;
        font-size: 4rem;
        // border: var(--border);
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
        border-radius: 1rem;
        padding: 2rem;
    }
    .title{
        font-family: serif;
        font-size: 5rem;
        color: #409EFF;
        margin: 2rem;
    }
</style>