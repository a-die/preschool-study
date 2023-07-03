<template>
  <div id="Right">
    <Right1></Right1>
    <Right2></Right2>
  </div>
</template>

<script>
import Right1 from './Right/Right1.vue'
import Right2 from './Right/Right2.vue'
export default {
  data(){
    return {

    }
  },
  methods: {
    
  },
  computed: {

  },
  components: {
    Right1,
    Right2
  }
}
</script>

<style scoped>
#Right{
  flex: 1.4;
  /* border: 1px solid #fff; */
  /* color: red; */
  height: 77vh;

  display: flex;
  /* flex-wrap: wrap; */
  flex-direction: column;
}
</style>