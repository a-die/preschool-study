<template>
  <div class="rightContent">
    <button type="primary" class="write" @click="gotoWrite()"> <i class="fas fa-edit"></i> 发布文章</button>

    <div class="message">
      <div>
        <p class="number">{{userCount}}</p>
        <p>用户</p>
      </div>
      <div>
        <p class="number">{{postCount}}</p>
        <p>文章</p>
      </div>
      <div>
        <p class="number">{{commentReplyCount}}</p>
        <p>评论</p>
      </div>
    </div>

    <div class="subject">
      <span> <i class="fas fa-book-open"></i> 科目</span>
      <ul>
        <li :class="{'active':(subjectIndex==item.id)}" v-for="item in subject" :key="item.id" @click="getArticle(item.id)">{{item.name}}<i>&gt;</i></li>
				<li v-if="subject.length===someSubject.length" @click="showAll(true)" class="showAll">显示所有科目</li>
				<li v-else @click="showAll(false)" class="showAll">收缩</li>
      </ul>

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { ElMessageBox } from 'element-plus'
export default {
	inject: ['jumpp'],
  data(){
    return{
      userCount:0,
      postCount:0,
      commentReplyCount:0,
      subject: [
				{
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
						}
			],
			someSubject: [
				{
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
						}
			],
			allSubject: [{
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
			}]
    }
  },
  computed: {
    ...mapState([
      'loginFlag',
      'subjectIndex',
      'type',
    ])
  }
  ,
  methods: {
    gotoWrite(){
      if(this.loginFlag===0) {
				ElMessageBox.alert('请先登录！','提示消息')
        .catch(() => {
          // catch error
        })
			}
      else{
        this.$store.commit('setHeadFalse');
        this.$router.push('/write');
      }
    },
    async getUserCount(){
      const {data: res} = await this.$http.get('/user/getUserNum');

      this.userCount=res;
    },
    // /discussPost/getDiscussPostNum
    async getPostCount(){
      const {data: res} = await this.$http.get('/discussPost/getDiscussPostNum');

      this.postCount=res;
    },
    async getCommentReplyCount(){
      const {data: res} = await this.$http.get('/comment/getCommentNum');

      this.commentReplyCount=res;
    },
    async getSubject(){
      const {data: res} = await this.$http.get('/comment/getCommentNum');

    },
    getArticle(id){
			this.jumpp();
      this.$store.commit('setSubject',id);
      this.$router.push({name: 'home',params: {type: this.type,subject: this.subjectIndex}});
    },
		showAll(i){
			if(i){
				setTimeout(() => {
					this.subject=this.allSubject;
				},100);
				
			}
			else{
				this.jumpp();
				setTimeout(() => {
					this.subject=this.someSubject;
				},100);
				
			}
		}
  },
  mounted(){
		if(this.subjectIndex>9) this.subject=this.allSubject;
    this.getUserCount();
    this.getPostCount();
    this.getCommentReplyCount();
  },
	watch: {
		subjectIndex(newVal,oldVal){
			if(newVal>9) this.subject=this.allSubject;
			else this.subject=this.someSubject;
		}
	}
}
</script>

<style scoped lang="less">
.rightContent>div{
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
  margin-top: .8rem;
  border-radius:  .5rem;
}

.write{
  width: 100%;
}

.message{
  background: #fff;
  display: flex;
  justify-content: space-around;
  align-items: center;
  div{
    padding: 1rem;
    font-size: 0.9rem;
    p:last-child{
      margin-top: .5rem;
    }
  }
}
.subject{
  background: #fff;
  ul{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    font-size: .9rem;
		position: relative;

    li{
      padding: .5rem;
      width: 100%;
      display: flex;
      justify-content: space-between;
      cursor: pointer;
    }
    li:hover {
      background: #fafafa;
      font-weight: 700;
			color: var(--blue);
    }
		li.active {
      background: #fafafa;
      font-weight: 700;
			color: var(--blue);
    }
  }
  span{
    display: block;
    padding: .5rem;
    text-align: start;
    border-bottom: #dadada solid 1px;
    i{
      color: var(--blue);
    }
  }
}
button{
  cursor: pointer;
  border-radius: .4rem;
  color: #fff;
  background: var(--blue);
  border: none;
  padding: .7rem;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
}
button:hover{
  background: var(--light--blue);
}
.number{
  font-size: 1.2rem;
  color: #000;
}
.showAll{
	padding: .5rem !important;
	text-align: center;
	font-size: .9rem;
	font-weight: 500;
	display: block !important;
	text-align: center;
	background: #fdfdfd;
	text-align: center !important;
	position: relative;
	bottom: 0;
	width: 100%;
	cursor: pointer;
	z-index: 200;
}
.showAll::before{
	content: '';
	background: var(--very-soft-black);
	position: absolute;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	cursor: pointer;
	filter: blur(1px);
  // 设置背景颜色的透明度
  opacity: 0.5;
	z-index: 100;
}
</style>