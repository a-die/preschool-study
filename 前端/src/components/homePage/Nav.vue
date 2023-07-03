<template>
  <div id="nav">
      <ul>
          <li :class="type==0?'active':''" @click="gotoType(0)"><a @click.prevent="" href="#">笔记</a></li>
          <li :class="type==1?'active':''" @click="gotoType(1)"><a @click.prevent="" href="#">踩坑记录</a></li>
          <li :class="type==2?'active':''" @click="gotoType(2)"><a @click.prevent="" href="#">提问</a></li>
      </ul>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
    inject: ['reload'],
    data() {
        return {
            active: 0
        }
    },
    methods: {
        gotoType(type){

            this.$store.state.type=type;
            this.active=type;
            this.$router.push({name: 'home',params:{type: type}});
            // this.reload();
            // this.$router.push({name: 'blog', params:{id: type}});
        }
    },
    computed: {
        ...mapState([
            'type'
        ])
    },
    mounted(){

    },
    unmounted(){
        
    }
}
</script>

<style scoped="nav" lang="less">
#nav {
    // box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
    background: #fff;
    font-size: .9rem;
    ul{
        width: 50%;
        margin: 0 auto;
        display: flex;
        align-items: center;
        justify-content: space-around;
    }
}

li {
    padding: .6rem;
    border-radius: 1rem;
    margin-left: 1rem;
    a{
        color: var(--light-color); 
    }
}

li:hover {
    a{
        color: var(--blue);
    }
}
li.active {
    font-weight: bolder;
    a{
        color: var(--blue);
    }
}

</style>