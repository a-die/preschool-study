<template>
  <div id="body">
    <Left></Left>
    <Medium></Medium>
    <Right></Right>
  </div>
</template>

<script>
import Left from './Left.vue'
import Medium from './Medium.vue'
import Right from './Right.vue'
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
    Left,
    Medium,
    Right
  }
}
</script>

<style>
#body{
  color: #fff;
  display: flex;
  /* box-sizing: border-box; */
  /* padding: 1rem; */
  padding: 0 1rem;
  /* margin-top: .5rem; */
}
</style>