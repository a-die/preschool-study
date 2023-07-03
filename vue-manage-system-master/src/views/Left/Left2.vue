<template>
  <div id="left2">
    <div class="top"></div>
    <div class="container">
		<Horizon></Horizon>
    </div>
    <div class="bottom"></div>
  </div>
</template>

<script>
import Horizon from '../../components/Horizon.vue'
export default {
	components:{
		Horizon,
	}
}
</script>

<style scoped>
#left2{
  flex: 1;
  /* border: 1px solid #fff; */
  /* border-radius: .4rem; */
  position: relative;
  margin-top: .5rem;
}
#left2::before{
  content: '';
  height: 100%;
  position: absolute;
  top: 0;left: 0;right: 0;
  background: #fff;
  /* border-radius: .4rem; */
  opacity: .08;
}
.top{
  position: relative;
}
.top::before{
  content: '';
  position: absolute;
  top: 0;left: 0;
  width: 10px;height: 10px;
  border-top: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.top::after{
  content: '';
  position: absolute;
  top: 0;right: 0;
  width: 10px;height: 10px;
  border-top: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.bottom{
  position: absolute;
  bottom: 0;left: 0;right: 0;
}
.bottom::before{
  content: '';
  position: absolute;
  bottom: 0;left: 0;
  width: 10px;height: 10px;
  border-bottom: 2px solid #02a6b5;
  border-left: 2px solid #02a6b5;
}
.bottom::after{
  content: '';
  position: absolute;
  bottom: 0;right: 0;
  width: 10px;height: 10px;
  border-bottom: 2px solid #02a6b5;
  border-right: 2px solid #02a6b5;
}
.container{
	padding: 0px;
	border: none;
	background: transparent;
	height: 100%;
	position: relative;
}
</style>