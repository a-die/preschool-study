<template>
  <div :class="['column',{'showOnly':(column.id%2===1)}]">
    <h4>{{ column.name }}</h4>
    <div class="columnInfo">
      <span><i class="fas fa-eye"></i>&nbsp;&nbsp;{{ column.watch }}</span>
      <span v-if="isMe" @click.stop.prevent="deleteColumn()"><i class="far fa-trash-alt"></i>&nbsp;&nbsp;删除</span>
      <span><i :class="['fas', column.id%2===1?'fa-lock':'fa-unlock']"></i> {{ visible }}</span>
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from 'element-plus'
export default {
  emits: ['updateColumn'],
  props: {
    column: {
      type: Object,
      default: {
        id: 1,
        name: "去⇧",
        user_id: 985669637
      },
      required: true
    }
  },
  data(){
    return {

    }
  },
  computed: {
    visible(){
      if(this.column.id%2===1) return '仅自己可见';
      return '所有人可见'
    },
    isMe(){
      if(sessionStorage.getItem('visit')===localStorage.getItem('id')) return true;
      else return false;
    }
  },
  methods: {
    async deleteColumn(){
      // /user/information
      ElMessageBox.confirm(
        '确认删除专栏？',
        '确认消息',
      )
      .then(() => {
        this.deleteCo();
      })
      .catch(() => {
        return ;
      })
    }
    ,
    async deleteCo(){
      let data ={
        'id': this.column.id,
      }

      const {data: res} = await this.$http.post('/column-self/delColumn' , this.qs.stringify(data));
      // this.columns=res;
      if(res.code===0) this.$emit('updateColumn');
    }
  },
}
</script>

<style scoped lang="less">
.column{
  color: #fff;
  padding: .5rem;
  padding-left: 1rem;padding-right: 1rem;
  cursor: pointer;
  width: 20%;height: 4rem;
  border-radius: .8rem;

  background: linear-gradient(to right, #16a085, #f4d03f);

  h4{
    text-align: start;
    font-size: 1rem;
    font-weight: 450;
  }
  // animation: 3s ease-in 1s 2 reverse both paused slidein;
  
  .columnInfo{
    font-size: .9rem;
    margin-top: .8rem;
    display: flex;
    justify-content: space-between;
    span{
      margin-right: 1rem;
      font-size: .8rem;
    }
  }
}

.column:hover{
  border-color: var(--light--blue);
  background: #3f3f3f;
  // color: #000;
}

// @keyframes dynamics {
//     0%{
//         background-position: 0% 0%;
//     }
//     50%{
//         background-position: 50% 100%;
//     }
//     100%{
//         background-position: 100% 0%;
//     }
//   }

.showOnly{
  background: linear-gradient(to right, #cc95c0, #dbd4b4, #7aa1d2);
}



</style>