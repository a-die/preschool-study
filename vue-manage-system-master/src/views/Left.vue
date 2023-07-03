<template>
  <div id="Left">
    <Left1></Left1>
    <Left2></Left2>
  </div>
</template>
 
<script>
import Left1 from './Left/Left1.vue'
import Left2 from './Left/Left2.vue'
export default {
  data() {
    return {

    }
  },
  computed: {

  },
  components: {
    Left1,
    Left2
  }
}
</script>

<style scoped>
#Left{
  flex: 1.4;
  /* border: 1px solid #fff; */
  /* color: red; */
  height: 77vh;

  display: flex;
  /* flex-wrap: wrap; */
  flex-direction: column;
}
</style>