// 写博客的页面
<template>
  <div id="write">
    <div class="HeadTitle">
      <input type="text" v-model.trim="title" placeholder="请输入标题">
      <div class="btnContainer">
        <button class="backHome" @click="backHome()">返回首页</button>
        <button v-if="blogId===-1"  @click="changeSubmit()">提交文章</button>
        <button class="modification"   v-else @click="updateDiscuss()">保存修改</button>
        <div class="img">
          <img :src="user.url" alt="">
        </div>
      </div>
    </div>
    
    <v-md-editor 
	  id="write"
	  v-model.trim="text"
	  :disabled-menus="[]"
	  @upload-image="handleUploadImage"
	  height="100%"
	/>
    
    <div class="type" v-if="typeFlag">
      <div class="typeChoices">
        <div class="title">文章信息选择</div>
        <div class="choice">
          <div>
            博文类型:&nbsp;&nbsp;
            <el-select v-model="btype" placeholder="请选择博文类型">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>

          <div>
            学科类型:&nbsp;&nbsp;
            <el-select v-model="subject" placeholder="请选择博文涉及的学科">
              <el-option
                v-for="item in subjects"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>

          <div>
            专栏选择:&nbsp;&nbsp;
            <el-select :disabled="columns.length<=1" v-model="column" placeholder="请选择想添加进的专栏">
              <el-option
                v-for="item in columns"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </div>

          <div v-if="btype==0" >
            是否付费:&nbsp;&nbsp;
            <el-switch v-model="isFree" active-color="#13ce66" inactive-color="#ff4949" />
            <el-input v-if="isFree" v-model="price" placeholder="输入价格" type="number" style="margin-left: .5rem;width: 9.5rem;" />
            <span  v-else style="display: inline-block;width: 9.5rem;height: 40px;margin-left: .5rem;"></span>
          </div>

          <div>
            <button @click="addDiscuss()">确认提交</button>
            <button class="backHome"  @click="changeSubmit()">返回修改</button>
          </div>
        </div>
      </div>
    </div>


  </div>
  
</template>

<script>
import axios from "axios";
import { ElMessageBox } from 'element-plus'
export default {
  data(){
    return {
      text: '',
      title: '',
      blogId: -1,
      article: {},
      typeFlag: false,
      updateFlag: false,
      isFree: 1,
      price: 0,
      options: [
        {
          id: 0,
          name: '笔记'
        },
        {
          id: 1,
          name: '踩坑记录'
        },
        {
          id: 2,
          name: '提问'
        },
      ],
      btype: 0,
      subject: 0,
      column: -1,
      subjects: [{
							id: 0,
							name: "高等数学",
							symbolSize: 40,
							x: 0,
							y: 0,
							category: 0 //数据项所在类目的 index
						}, {
							id: 1,
							name: "线性代数",
							symbolSize: 40,
							x: -2,
							y: 16,
							category: 0
						}, {
							id: 2,
							name: "离散数学",
							symbolSize: 40,
							x: 0,
							y: 32,
							category: 0
						}, {
							id: 3,
							name: "C语言",
							symbolSize: 32,
							x: -2,
							y: 48,
							category: 0
						}, {
							id: 4,
							name: "大学物理",
							symbolSize: 40,
							x: 0,
							y: 64,
							category: 4
						}, {
							id: 5,
							name: "电路分析",
							symbolSize: 40,
							x: 0,
							y: 80,
							category: 4
						},{
							id: 7,
							name: "概率论",
							symbolSize: 40,
							x: 38,
							y: 10,
							category: 0
						}, {
							id: 8,
							name: "Java语言",
							symbolSize: 40,
							x: 38,
							y: 30,
							category: 0
						}, {
							id: 9,
							name: "数据结构",
							symbolSize: 48,
							x: 38,
							y: 48,
							category: 0
						}, {
							id: 11,
							name: "计算机组成原理",
							symbolSize: 40,
							x: 65,
							y: -12,
							category: 0
						}, {
							id: 12,
							name: "操作系统",
							symbolSize: 40,
							x: 65,
							y: -2,
							category: 0
						}, {
							id: 13,
							name: "模拟电子技术",
							symbolSize: 40,
							x: 36,
							y: 66,
							category: 4
						}, {
							id: 14,
							name: "数字电子技术",
							symbolSize: 40,
							x: 38,
							y: 80,
							category: 4
						}, {
							id: 15,
							name: "计算机网络",
							symbolSize: 40,
							x: 65,
							y: 10,
							category: 0
						}, {
							id: 16,
							name: "微型计算机原理",
							symbolSize: 40,
							x: 65,
							y: 25,
							category: 0
						}, {
							id: 17,
							name: "Mysql",
							symbolSize: 40,
							x: 64,
							y: 40,
							category: 1
						}, {
							id: 18,
							name: "JavaWeb",
							symbolSize: 40,
							x: 64,
							y: 49,
							category: 1
						}, {
							id: 19,
							name: "算法",
							symbolSize: 48,
							x: 64,
							y: 60,
							category: 0
						}, {
							id: 20,
							name: "Linux",
							symbolSize: 40,
							x: 64,
							y: 70,
							category: 0
						}, {
							id: 22,
							name: "ES6",
							symbolSize: 40,
							x: 84,
							y: 33,
							category: 2
						}, {
							id: 23,
							name: "axios",
							symbolSize: 40,
							x: 95,
							y: 32,
							category: 2
						}, {
							id: 24,
							name: "vue",
							symbolSize: 34,
							x: 108,
							y: 31,
							category: 2
						}, {
							id: 25,
							name: "react",
							symbolSize: 40,
							x: 108,
							y: 41,
							category: 2
						}, {
							id: 26,
							name: "element-ui",
							symbolSize: 40,
							x: 108,
							y: 23,
							category: 2
						}, {
							id: 27,
							name: "ssm框架",
							symbolSize: 40,
							x: 84,
							y: 48,
							category: 3
						}, {
							id: 28,
							name: "springboot",
							symbolSize: 40,
							x: 97,
							y: 54,
							category: 3
						}, {
							id: 29,
							name: "springcloud",
							symbolSize: 40,
							x: 112,
							y: 53,
							category: 3
						}, {
							id: 30,
							name: "mybatis-plus",
							symbolSize: 40,
							x: 126,
							y: 53,
							category: 3
						}, {
							id: 31,
							name: "redis",
							symbolSize: 40,
							x: 138,
							y: 50,
							category: 3
						}, {
							id: 32,
							name: "Kafka",
							symbolSize: 40,
							x: 151,
							y: 54,
							category: 3
						}, {
							id: 33,
							name: "RabbitMQ",
							symbolSize: 40,
							x: 163,
							y: 54,
							category: 3
						}, {
							id: 34,
							name: "单片机",
							symbolSize: 40,
							x: 65,
							y: 82,
							category: 4
						}, {
							id: 35,
							name: "嵌入式",
							symbolSize: 40,
							x: 85,
							y: 82,
							category: 4
						}, {
							id: 36,
							name: "通信原理",
							symbolSize: 40,
							x: 103,
							y: 82,
							category: 4
						}, {
							id: 37,
							name: "网络安全",
							symbolSize: 40,
							x: 88,
							y: 15,
							category: 6
						},{
							id: 39,
							name: "人工智能",
							symbolSize: 40,
							x: 118,
							y: 5,
							category: 5
						}, {
							id: 40,
							name: "大数据",
							symbolSize: 40,
							x: 118,
							y: -6,
							category: 5
						}, {
							id: 41,
							name: "云计算",
							symbolSize: 40,
							x: 118,
							y: -15,
							category: 5
						},{
							id: "42",
							name: "安卓",
							symbolSize: 42,
							x: 82,
							y: 60,
							category: 2
						},{
              id: 43,
              name: '其他',
              symbolSize: 43,
              x: 83,
              y: 62,
              category: 8
            }],
      columns: [
        {
          id: 1,
          name: '前端',
          user_id: 12,
        },
        {
          id: 2,
          name: '后端',
          user_id: 12,
        }
      ],
      article: {

      },
      user: {}
    }
  },
  methods: {
    async addDiscuss(){
      var isnum = /^\d+$/;
      if(this.isFree===1) {this.price=0;}
      if(!isnum.test(this.price)){
        ElMessageBox.alert('请输入正确的价格!','提示消息')
        .catch(() => {
          // catch error
        });
        this.price=0;
        return ;
      }
      let data ={
        'btype': this.btype,
        'content': this.text,
        'title': this.title,
        'user_id': localStorage.getItem('id'),
        'subject': this.subject,
        'column_id': this.column,
        'isFree': this.isFree?0:1,
        'price': this.price
      }

      const {data: res} = await this.$http.post('/discussPost/addPost' ,this.qs.stringify(data));

      if(res.code==0){
        ElMessageBox.alert('提交成功','提示消息')
        .catch(() => {
          // catch error
        })
        this.title='';
        this.text='';
        this.typeFlag=!this.typeFlag;
      }else if(res.code==123){
        ElMessageBox.alert('内容重复，请重新编写','提示消息')
        .catch(() => {
          // catch error
        })
      }
      else{
        ElMessageBox.alert('提交失败','提示消息')
        .catch(() => {
          // catch error
        })
      }
    },
    backHome(){
      this.$store.state.type=0;
			this.$store.state.subjectIndex=0;
      this.$router.push({name: 'home',params: {type: 0,subject: 0}})
    },
    // 获取博客信息
    async showOnePost(){
      let data ={
        'id': this.blogId
      }
      const {data: res} = await this.$http.post('/discussPost/showOnePost' , this.qs.stringify(data));

      this.article = res;
      this.text=res.content;
      this.title=res.title;
    },
    async getColumn(){
      // /user/information
      let data ={
        'id': Number(localStorage.getItem('id')),
      }

      const {data: res} = await this.$http.post('/column-self/showAll' , this.qs.stringify(data));
      this.columns=res;
      if(this.columns.length!==0){
        this.columns.push({id: -1,name: '不加入专栏'});
      }else{
        this.columns.push({id: -1,name: '无专栏'});
      }
    },
    changeSubmit(){
      if(!this.typeFlag){
        if(this.title===undefined||this.title=='') {
          ElMessageBox.alert('请输入标题！','提示消息')
          .catch(() => {
            // catch error
          })
        }else if(this.text===undefined||this.text==''){
          ElMessageBox.alert('请输入内容','提示消息')
          .catch(() => {
            // catch error
          })
        }else if(this.text.length<=20) {
          ElMessageBox.alert('内容过少','提示消息')
          .catch(() => {
            // catch error
          })
        }else{
          this.typeFlag=!this.typeFlag;
        }
        // this.typeFlag=!this.typeFlag;
      }else{
        this.typeFlag=!this.typeFlag;
      }
    },
    async updateDiscuss(){
      if(this.title===undefined||this.title==''){
        ElMessageBox.alert('请输入标题！','提示消息')
        .catch(() => {
          // catch error
        })
      }else if(this.text===undefined||this.text==''){
        ElMessageBox.alert('请输入内容','提示消息')
        .catch(() => {
          // catch error
        })
      }else if(this.text.length<=20){
        ElMessageBox.alert('内容过少','提示消息')
        .catch(() => {
          // catch error
        })
      }else{
        let data ={
          'id': this.article.id,
          'user_id': this.article.user_id,
          'title': this.title,
          'content': this.text,
          'type': this.article.type,
          'status': this.article.status,
          'comment_count': this.article.comment_count,
          'up_count': this.article.up_count,
          'btype': this.article.btype,
          'subject': this.article.subject,
          'column_id': this.article.column_id,
          'identify': this.article.identify,
        }

        const {data: res} = await this.$http.post('/discussPost/updateOnePost' ,this.qs.stringify(data));

        if(res.code==0){
          ElMessageBox.alert('提交成功','提示消息')
          .catch(() => {
            // catch error
          })
          this.title='';
          this.text='';
          // this.typeFlag=!this.typeFlag;
        }else{
          ElMessageBox.alert('提交失败','提示消息')
          .catch(() => {
            // catch error
          })
        }
      }
        
    },
	handleUploadImage(event, insertImage, files) {
	      // 拿到 files 之后上传到文件服务器，然后向编辑框中插入对应的内容

		  const formData = new FormData();
		  let urls;
		  formData.append('image', files[0]);
		  axios.post('http://1.117.51.169:8000/uploadImg', formData)
		  	.then(function(res){

				urls = res.data.info
				insertImage({
				  url:
				    urls,
				  desc: files[0].name,
				  // width: 'auto',
				  // height: 'auto',
				});
		  	});
	      // 此处只做示例
	      
	},
	async getUser(){
		let data ={
		  'id': localStorage.getItem('id')
		}

		const {data: res} = await this.$http.post('/user/information' , this.qs.stringify(data));

		this.user=res;

	}
  },
  unmounted(){
    this.$store.commit('setHeadTrue');
    sessionStorage.setItem('blogId',-1);
  },
  mounted(){
    this.$store.commit('setHeadFalse');
    this.blogId=sessionStorage.getItem('blogId');

    if(this.blogId!=-1&&this.blogId!=null&&this.blogId!=undefined){
      this.showOnePost();
    }else{
      this.blogId=-1;
    }
    this.getColumn()
	this.getUser();
	
  },
  watch: {
    
  }
}
</script>

<style scoped lang="less">
#write{
  text-align: start;
  width: 100%;
  height: 92vh;
  // transform: translateY(-3.7rem);

  position: relative;
  // padding: 0;
  // margin: 0;
  // transition: none !important;
  button{
    cursor: pointer;
    // margin: auto 0;
    width: 20%;height: 2rem;
    font-size: .9rem;
    flex: 1;
    color: #fff;
    background: var(--blue);
    border: none;
    // border-radius: .4rem;
    margin-left: .8rem;
  }
  button:hover{
    background: var(--light--blue);
  }

  .modification{
    background: var(--red);
  }
  .modification:hover{
    background: var(--crabapple-red);
  }
  input{
    margin: 0;
    margin-bottom: .5rem;
    width: 70%;
    padding: 1rem;
    font-size: 1.3rem;
    border: none;
  }
  input:focus{
    border: none;
    outline: none;
  }

  .backHome{
    background: #fff;
    color: var(--light--blue);
    border: 1px solid var(--light--blue);
  }
  .backHome:hover{
    color: var(--blue);
    background: #fff;
  }

  .HeadTitle{
    width: 100%;
    display: flex;
    height: 4rem;
    background: #fff;

    .btnContainer{
      width: 30%;
      // flex: 2;
      display: flex;
      justify-content: space-around;
      align-items: center;

      .img{
        flex: 1.5;
        text-align: end;
        width: 40px;
        height: 40px;
        padding-right: 1.5rem;
        img{
          width: 40px;
          height: 40px;
          border-radius: 50%;
        }
      }
    }
  }
}

input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
input[type="number"]{
  -moz-appearance: textfield;
}

.type{
  // padding: 0;
  position: absolute;
  top: 0;left: 0;right: 0;
  z-index: 1000 !important; 
  
  width: 100%;
  height: 109%;
  .title{
    font-family: sans-serif;
    border-bottom: 1px solid var(--background);
    font-size: 1.5rem;
    padding: 1rem;
    text-align: center;
    color: var(--light-color);
    margin-bottom: .5rem;
  }
  

  .typeChoices{
    position: absolute;
    top: 50%;left: 50%;
    transform: translate(-50%,-70%);
    width: 30vw;
    height: 45vh;
    background: #fff;
    border-radius: .4rem;
    color: var(--very-soft-black);

    .choice{
      padding-left: 1rem;
      div{
        // padding-left: 1rem;
        // text-align: start;
        margin-bottom: .5rem;
        text-align: center;
      }
    }

  }
  
}
.type::before{
    content: '';
    position: absolute;
    top: 0;left: 0;
    width: 100%;
    height: 100%;
    background: #000;
    opacity: .5;
  }




</style>