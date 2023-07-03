<template>
  <div id="homePage" ref="test">
    <Nav class="nav"></Nav>
    <LeftArticle  class="details"></LeftArticle>
    
  </div>
</template>

<script>
import Nav from '@/components/homePage/Nav.vue'
import LeftArticle from '@/components/homePage/LeftArticle.vue'
import { ElMessageBox } from 'element-plus'


export default {
  provide(){
    return {
      remind: this.remindPay,
      jumpp: this.jumpp,
    }
  },
  components: {
    Nav,
    LeftArticle,
  },
  data(){
    return {
      dialogVisible: false,
      index: 5
    }
  },
  mounted(){
    // this.$store.state.type=0;
    // this.$store.state.type=this.$route.params.type;
  },
  methods: {
    handleClose(){
      ElMessageBox.confirm('Are you sure to close this dialog?')
      .catch(() => {
        // catch error
      })
    },
    jumpp(){
      var anchor = this.$refs.test;

      if(anchor) {
        setTimeout(()=>{
          anchor.scrollIntoView({
            behavior: "smooth",  
            block:    "center"  
          }); 
        },50);
      } 
    },
    
  },
  unmounted(){
    // this.$store.state.type=0;
  }
}
</script>

<style scoped lang="less">

#homePage{
  transition: all .2s ease-out;
  margin: 20px auto;
}

.details{
  margin: 0 auto;
  
}
.nav{
  width: 100%;
  position: absolute;
  top: 4.05rem;
  z-index: 100 !important;
}

</style>