<template>
  <div id="setting-info">
    <div>
      <label for="headImg">修改头像</label>
      <input class="headI" name="headImg" id="headImg"  type="file" accept="image/*" @change="getFile($event)"/>
    </div>
    <div>
      <label for="username">用户昵称</label>
      <input name="username" id="username"  type="text" v-model="user.username" >
    </div>
    <div>
      <label for="account">生日</label>
      <input name="date" id="account"  type="date" v-model="user.birthday"  >
    </div>
    <div>
      <label for="sex">性别</label>
      <select id="sex" v-model="user.sex">
        <option>请选择性别</option>
        <option value="0" :aria-checked="sex==1">男</option>
        <option value="1" >女</option>
      </select>
    </div>
    <div>
      <label for="signature">个性签名</label>
      <input name="signature" id="signature" type="text" v-model.trim="user.signature">
    </div>
    <div class="submit">
      <button @click="updateUserInfo()">提交修改</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { ElMessageBox } from 'element-plus'
export default {
  inject: ['refreshUser'],
  data(){
    return {
      headImg: '',
      username: 'alex',
      birthday: '123456',
      sex: 0,
      signature: '前端很重要',
	  file: null,
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
        username: "alex",
		url: ''
      },
	  ischange: 0,
    }
  },
  computed: {
    
  },
  methods: {
    async getUserInfo(){
      // /user/information
      let data ={
        'id': localStorage.getItem('id')
      }

      const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));

      this.user=res;

    },
    async submitChange(){

    },
    async updateUserInfo(){
      // /user/information
	if(this.ischange === 1){
		const formData = new FormData();
		formData.append('image', this.file);
		var that = this;
		axios.post('http://1.117.51.169:8000/uploadImg', formData)
			.then(function(resp){
				that.$store.state.headImg = resp.data.info
				that.ischange = 0;
				let data ={
          'birthday': that.user.birthday,
          'email': that.user.email,
          'id': that.user.id,
          'password': that.user.password,
          'phonenumber': that.user.phonenumber,
          'portrait_id': that.user.portrait_id,
          'sex': that.user.sex,
          'signature': that.user.signature,
          'status': that.user.status,
          'type': that.user.type,
          'username': that.user.username,
					'place': that.user.place,
					'url': resp.data.info
				  }
				  axios.post('http://localhost:8081/user/update' , that.qs.stringify(data))
					.then(function(res){
						if(Number(res.data.code)===0){
						  that.getUserInfo();
						  that.password='';
						  that.refreshUser();
              ElMessageBox.alert('修改成功！','提示消息')
              .catch(() => {
                // catch error
              })
						}
					})
				  
				  
			});
	}
	else{
		let data ={
		    'birthday': this.user.birthday,
		    'email': this.user.email,
		    'id': this.user.id,
		    'password': this.user.password,
		    'phonenumber': this.user.phonenumber,
		    'portrait_id': this.user.portrait_id,
		    'sex': this.user.sex,
		    'signature': this.user.signature,
		    'status': this.user.status,
			'place': this.user.place,
		    'type': this.user.type,
		    'username': this.user.username
		  }

		  const {data: res} = await this.$http.post('/user/update' , this.qs.stringify(data));

		  if(res.code===0){
		    this.getUserInfo();
		    this.password='';
		    this.refreshUser();
        ElMessageBox.alert('修改成功！','提示消息')
        .catch(() => {
          // catch error
        })
		  }
	}
	
    
    },
	getFile(event){
		this.ischange = 1;
		this.file = event.target.files[0];

	},
  },
  
  mounted() {
    this.getUserInfo();
  }
}
</script>

<style scoped lang="less">
#setting-info{
  padding: 1rem;
  div{
    margin-bottom: 1.5rem;
    text-align: start;
    font-size: .9rem;
    label{
      display: block;
      margin-bottom: .5rem;
    }
    .headI{
      border: 1px solid transparent;
      padding: 0;
    }
    input{
      border-radius: .4rem;
      outline: none;
      width: 95%;
      padding: .5rem;
      border: 1px solid var(--border--color);
    }
    select{
      border-radius: .4rem;
      outline: none;
      width: 97%;
      padding: .5rem;
      border: 1px solid var(--border--color);
    }
    input:focus,select:focus {
      border: 1px solid var(--light--blue);
      box-shadow: 0px 0px 5px rgba(0, 0, 255,.4);
    }
    button{
      // margin: auto 0;
      width: 20%;height: 2.5rem;
      font-size: 1rem;
      color: #fff;
      background: var(--blue);
      border: none;
      border-radius: .4rem;
    }
    button:hover{
      background: var(--light--blue);
    }
  }
  .submit{
    text-align: center;
  }
}
</style>