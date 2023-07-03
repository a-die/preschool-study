<template>
  <div id="Index">
    <Header></Header>
    <Body></Body>
  </div>
</template>

<script>
import Header from './Header.vue'
import Body from './Body.vue'
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
    Body,
    Header
  }
}
</script>

<style scoped>
#Index{
  width: 100%;height: 100%;
  /* display: flex; */
  /* background-color: red; */
  background:#001845;
/*  background-position: bottom;
  background-repeat: no-repeat;
  background-size: cover; */
}
</style>