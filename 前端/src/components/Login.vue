<template>
  <div id="login">
      <el-form ref="form" :model="form" label-width="6rem" class="form">
        <h3 class="title">登录</h3>
        <el-form-item label="password">
            <el-input v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="phonenumber">
            <el-input v-model="form.phonenumber" ></el-input>
        </el-form-item>
        <el-button type="primary" plain @click.stop="login()">登录</el-button>
        <el-button type="success" plain @click.stop="goto()">注册</el-button>
      </el-form>
  </div>
</template>

<script>
export default {
    data(){
        return {
            form:{
                phonenumber: "",
                password: ""
            }
        }
    },
    methods: {
        submit() {
            console.log(this.phonenumber,this.password);
        },
        goto(){
            this.$router.push('/register');
        },
        async login(){// 登录方法
            var that=this;
            let data ={
                'password': this.form.password,
                'phonenumber': this.form.phonenumber
            }

            const d = await this.$http.post('/user/login',this.qs.stringify(data),
            );
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
    #login{
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