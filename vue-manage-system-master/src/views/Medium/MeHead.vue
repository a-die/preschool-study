<template>
  <div id="meHead">
    <div class="show">
      <div class="numberShow">
        <span>
          {{userCount}}
        </span>
        <span>
          {{postCount}}
        </span>
      </div>
      <div class="wordsShow">
        <span>
          当前用户人数
        </span>
        <span>
          当前博文数量
        </span>
      </div>
    </div>
    </div>
	<div class="container">
		<Map></Map>
	</div>
</template>

<script>
import Map from '../../components/Map.vue'
export default {
	data () {
	  return {
	    userCount:0,
		postCount:0,
	  }
	},
	mounted(){
		this.$socket.registerCallBack("Header",this.getData)
		this.getData()
	},
	destroyed(){
		this.$socket.unRegisterCallBack("Header")
	},
	methods:{
		getData(){
			var that=this
			this.$axios.get('http://localhost:8081/discussPost/getDiscussPostNum')
			.then(function(res){
				that.postCount=res.data;
			})
			.catch(function(err){
				//请求失败，回调
				console.log(err)
			})
			this.$axios.get('http://localhost:8081/user/getUserNum')
			.then(function(res){
				that.userCount=res.data;
			})
			.catch(function(err){
				//请求失败，回调
				console.log(err)
			})
		}
	},
	components:{
		Map,
	}
}
</script>

<style scoped>
#meHead{
  display: flex;
  padding: .5rem;
  height: 20%;
  position: relative;
}
.show{
  width: 100%;
}
.numberShow{
  position: relative;
  width: 100%;
  height: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid rgba(226, 225, 228, .3);
}
.numberShow::before{
  content: '';
  position: absolute;
  top: 0;left: 0;
  width: 20px;height: 10px;
  border-top: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.numberShow::after{
  content: '';
  position: absolute;
  bottom: 0;right: 0;
  width: 20px;height: 10px;
  border-bottom: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.numberShow span{
  flex: 1;
  text-align: center;
  font-size: 1.8rem;
  color: gold;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
}
.numberShow span:nth-child(1){
  border-right: 1px solid #fff;
}
.wordsShow{
  margin-top: .9rem;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.wordsShow span{
  flex: 1;
  text-align: center;
}

#meHead::before{
  content: '';
  height: 100%;
  position: absolute;
  top: 0;left: 0;right: 0;
  background: #fff;
  border-radius: .4rem;
  opacity: .08;
}
.container{
	padding: 0px;
	border: none;
	background: transparent;
	height: 78%;
	position: relative;
}
</style>