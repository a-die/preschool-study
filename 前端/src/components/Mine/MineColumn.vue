<template>
  <div id="mineColumn">
    <div class="columnDo" v-if="isMe">
      <div class="addColumn" @click.stop="changeFlag()">
        <i class="fas fa-plus"></i> 添加专栏
      </div>
      <div class="addColumnNow" v-if="addFlag" @click.stop>
        <input type="text" v-model="NewColumn.title" placeholder="请输入专栏名">
        <div class="addColumnInfo">
          <div>
            <span @click="deleteColumn()"><i class="far fa-trash-alt"></i>&nbsp;&nbsp;删除</span>
            <span @click="addColumn()"><i class="fas fa-plus"></i>&nbsp;&nbsp;创建</span>
          </div>
          <span><i class="fas fa-unlock"></i>&nbsp;&nbsp;所有人可见</span>
        </div>
      </div>
    </div>

    <div class="Columns">
      <Column @click="gotoColumn(item.id)" @updateColumn="update" v-for="item in columns" :key="item.id" :column="item"></Column>
    </div>
    
  </div>
</template>

<script>
import Column from '@/components/Mine/MineColumn/Column'
export default {
  data(){
    return {
      columns: [],
      NewColumn: {
        title: '',
        watch: 0,
        visibility: 1
      },
      addFlag: false,
      isMe: false
    }
  },
  methods: {
    init(){
      // 设置回复评论框消失情况
      let that=this;
      document.addEventListener('click', (e) => {
        that.addFlag=false;
      })
    },

    async getColumn(){
      // /user/information
      let data ={
        'id': sessionStorage.getItem('visit'),
      }

      const {data: res} = await this.$http.post('/column-self/showAll' , this.qs.stringify(data));
      this.columns=res;
    },
    async addColumn(){

      
      // /user/information
      let data ={
        'user_id': sessionStorage.getItem('visit'),
        'name': this.NewColumn.title
      }

      const {data: res} = await this.$http.post('/column-self/add' , this.qs.stringify(data));
      if(res.code===0){
        this.addFlag=false;
        this.NewColumn.title=''
        this.getColumn();
      }
    },
    changeFlag(){
      this.addFlag=true;
    },
    deleteColumn(){

      this.addFlag=false;
    },
    update(){
      this.getColumn();
    },
    gotoColumn(id){
      this.$router.push({name:'column',params: {column: id,userId: sessionStorage.getItem('visit')}});
    }
  },
  components: {
    Column
  },
  mounted(){
    this.getColumn();
    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
  }
}
</script>

<style scoped lang="less">
#mineColumn{
  min-height: 20rem;
  background: #fff;
  padding: 1rem;

  .Columns{
    display: flex;
    flex-wrap: wrap;
    div{
      margin: .5rem;
    }
  }

  .addColumn{
    margin: .5rem;
    padding: .5rem;
    padding-left: 1rem;padding-right: 1rem;
    cursor: pointer;
    width: 20%;height: 4rem;
    border: 1px dashed var(--gray);
    // background: var(--background);
    line-height: 4rem;
    border-radius: .8rem;
  }
  .addColumn:hover{
    border-color: var(--light--blue);
    background: var(--background);
    font-weight: 700;
  }

  .columnDo{
    display: flex;

    .addColumnNow{
      margin: .5rem;
      padding: .5rem;
      color: #fff;
      padding-left: 1rem;padding-right: 1rem;
      cursor: pointer;
      width: 30%;height: 4rem;
      border-radius: .8rem;

      background: linear-gradient(to right, #12c2e9, #c471ed, #f64f59);

      input{
        outline: none;
        width: 90%;height: 30%;
        border-radius: .4rem;
        border: 1px solid var(--background);
        padding: .1rem;
      }
      input:focus{
        border: 1px solid var(--blue);
      }

      .addColumnInfo{
        font-size: .8rem;
        margin-top: 1.5rem;
        display: flex;
        justify-content: space-between;
        span{
          margin-right: 1rem;
        }
        span:hover{
          color: var(--blue);
        }
      }
    }
  }

  

}
</style>