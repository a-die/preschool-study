<template>
  <div id="head">
    <span>
      可视化展板
    </span>
    <p>当前时间: {{ time }}</p>
  </div>
</template>

<script>
export default {
  data(){
    return {

    }
  },
  computed: {
    time(){
      return new Date().toLocaleString();
    }
  }
}
</script>

<style scoped>
#head{
  background: url('../assets/img/head_bg.png');
  background-position: bottom;
  background-repeat: no-repeat;
  background-size: cover;
  /* border: 1px solid red; */
  height: 8vh;
  position: relative;
  
}

span {
  color: #fff;
  /* margin:  100rem; */
  /* display: inline-block; */
  position: absolute;
  left: 0;right: 0;
  top: 50%;
  transform: translateY(-50%);
  text-align: center;
  font-size: 2.5rem;
  font-family: Georgia, 'Times New Roman', Times, serif;
  font-weight: 700;
}

p{
  position: absolute;
  top: 50%;right: 2vw;
  transform: translateY(-100%);
  color: #fff;
}

</style>