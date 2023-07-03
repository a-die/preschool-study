<template>
  <div id="setting-myPage">
    <h2>编辑主页信息</h2>
    <v-md-editor id="myPage" v-model="text" height="40rem"></v-md-editor>
    <div class="submit">
      <button @click="saveSelfIndex">提交修改</button>
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from 'element-plus'
export default {
  data(){
    return {
      text: ''
    }
  },
  mounted(){
	  this.getUserInfo()
  },
  methods:{
	  async getUserInfo(){
	    // /user/information
	    let data ={
	      'id': localStorage.getItem('id')
	    }

	    const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));

		this.text = res.selfIndex

	  },
	  async saveSelfIndex(){
		  let data ={
		    'id': sessionStorage.getItem('visit'),
			'selfIndex': this.text
		  }
		  
		  const {data: res} = await this.$http.post('/user/updateSelfIndex' , this.qs.stringify(data));

		  if(res.code===0){
		    ElMessageBox.alert('修改成功！','提示消息')
		    .catch(() => {
		      // catch error
		    })
		  }
	  }
  }
}
</script>

<style scoped lang="less">
#setting-myPage{
  padding: 1rem;
  h2{
    margin: 1rem;
  }
  #myPage{
    font-size: 2rem;
  }
  .submit{
    margin-top: 2rem;
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

</style>