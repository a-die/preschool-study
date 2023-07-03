<template>
  <div id="setting-safe">
    <table>
      <tr>
        <td v-if="user.phonenumber!=null" class="green"><i class="fas fa-check-circle"></i></td>
        <td v-else class="red"><i class="fas fa-fire"></i></td>
        <td>手机号</td>
        <td>{{ user.phonenumber }}</td>
        <td><a href="#" @click.prevent>点击复制</a></td>
      </tr>
      <tr>
        <td v-if="user.email!=null" class="green"><i class="fas fa-check-circle"></i></td>
        <td v-else class="red"><i class="fas fa-fire"></i></td>
        <td>邮箱</td>
        <td>{{ user.email }}</td>
        <td><a href="#" @click.prevent>已绑定</a></td>
      </tr>
      <tr>
        <td v-if="user.password!=null" class="green"><i class="fas fa-check-circle"></i></td>
        <td v-else class="red"><i class="fas fa-fire"></i></td>
        <td>密码</td>
        <td v-if="changePsw" >{{ psw }}</td>
        <td v-else ><input v-model.lazy="password" type="password"></td>
        <td v-if="changePsw"><a href="#" @click.prevent="changePswFlag()">修改密码</a></td>
        <td v-else><a href="#" @click.prevent="changePswFlag()">提交修改</a></td>
      </tr>
    </table>
  </div>
</template>

<script>
import { ElMessageBox } from 'element-plus'
export default {
  data(){
    return {
      account: '123456',
      email: '123456@qq.com',
      password: '',
      changePsw: true,
      user: {
        birthday: null,
        createTime: "2021-12-08T15:28:29.000+00:00",
        email: "2638826623@qq.com",
        id: 985669637,
        password: "E10ADC3949BA59ABBE56E057F20F883E",
        phonenumber: "17707322825",
        portrait_id: 0,
        sex: 0,
        signature: null,
        status: 0,
        type: 0,
        updateTime: "2021-12-08T15:28:29.000+00:00",
        username: "alex"
      }
    }
  },
  computed: {
    psw(){
      let len = this.user.password.length<15?this.user.password.length:15;
      return '*'.repeat(len);
    }
  },
  methods: {
    async getUserInfo(){
      // /user/information
      let data ={
        'id': localStorage.getItem('id')
      }

      const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));
      //   console.log(res);
      this.user=res;
      // console.log(this.user);
    },
    changePswFlag(){
      let f;
      if(!this.changePsw){
        this.updateUserInfo();
      }
      this.changePsw=!this.changePsw;
      
    },
    async updateUserInfo(){
      // /user/information

      if(this.password===this.user.password){
        ElMessageBox.alert('请输入新密码！','提示消息')
        .catch(() => {
          // catch error
        })
        return ;
      }

      let data ={
        'birthday': this.user.birthday,
        'email': this.user.email,
        'id': this.user.id,
        'password': this.password,
        'phonenumber': this.user.phonenumber,
        'portrait_id': this.user.portrait_id,
        'sex': this.user.sex,
        'signature': this.user.signature,
        'status': this.user.status,
        'type': this.user.type,
        'username': this.user.username
      }
      const {data: res} = await this.$http.post('/user/update' , this.qs.stringify(data));

      if(res.code===0){
        this.getUserInfo();
        this.password='';
        ElMessageBox.alert('修改成功！','提示消息')
        .catch(() => {
          // catch error
        })
      }
    }
  },
  mounted(){
    this.getUserInfo();
  }
}
</script>

<style scoped lang="less">
#setting-safe{
  padding: 1.2rem;
  font-size: .9rem;
  table{
    width: 100%;
    border-collapse: collapse;
    tr,td{
      height: 4rem;
    }
    tr{
      border-bottom: 2px solid var(--background);
    }
    td{
      text-align: start;
      a{
        color: var(--light--blue);
        text-decoration: underline;
      }
      a:hover{
        color: var(--pearl-gray);
        text-decoration: underline;
      }
      input{
      border-radius: .4rem;
      outline: none;
      width: 95%;
      padding: .5rem;
      border: 1px solid var(--border--color);
    }
    }
  }
  .green{
    color: var(--mint-green);
  }
  .red{
    color: var(--maple-red);
  }
}
</style>