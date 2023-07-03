<template>
  <div class="arcticle">
    <div class="message">
      <span class="name">{{ item.username }}</span>
      &nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
      <span class="time">{{ time }}</span>
    </div>
    <div class="title">
      <h4>{{ item.title }}</h4>
    </div>
    <div class="content">
      <span>{{  showContent }}</span>
    </div>
    <div class="operations">
      <span ><i class="far fa-eye" ></i>&nbsp;&nbsp;{{ item.watch }}</span>
      <span @click.stop="giveThumbs()" :class="thumbs? 'blue':''"><i class="far fa-thumbs-up"></i>&nbsp;&nbsp;{{ item.up_count }}</span>
      <span ><i class="far fa-comment-alt"></i>&nbsp;&nbsp;{{ item.comment_count }}</span>
    </div>
  </div>
</template>

<script>
import { ElMessageBox } from 'element-plus'

export default {
  props: {
    item: {
      default:{
        btype: 1,
        column_id: 0,
        comment_count: 24,
        content: "# 一、盒子模型的概念及组成\n盒子模型：html页面把布局元素看做一个矩形的盒子，也就是一个盛装内容的容器。\n\n盒子模型包括外边距 margin、边框 border、内边距 padding、内容 content\n\nmargin：外边距，与其它盒子的距离\nborder：边框，盒子的边框\npadding：内边距，边框与内容的距离\ncontent：内容，一般设置了宽高\n\n盒子模型如下图\n![Description](https://img-blog.csdnimg.cn/img_convert/b8888fe9de137592c3576280ac84e237.png#pic_center)\n# 二、盒子的大小\n盒子的大小就是盒子的宽高，这个宽高并不是指content的宽和高。默认是包括content、padding、border和margin。\n\n>盒子占用的宽=左外边距 + 左边框 + 左内边距 + 内容的宽 + 右内边距 + 右边框 + 右外边距\n>盒子占用的高=上外边距 + 上边框 + 上内边距 + 内容的高 + 下内边距 + 下边框 + 下外边距\n## 1、标准盒子\n![Description](https://img-blog.csdnimg.cn/img_convert/ebbfdeb266fbfb9a434e94f311e48cb7.png#pic_center)\n在标准盒子中，css设置的width和height是content的宽高。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + border-left + padding-left + width + padding-right + border-right + margin-right\n盒子实际占用的高=margin-top + border-top + padding-top + height + padding-bottom + border-bottom + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+10+10+200+10+10+10=260 px,盒子实际占用的高=10+10+10+200+10+10+10 =260 px\n![Description](https://img-blog.csdnimg.cn/img_convert/93a7b28d1d4dba8e27b604082200ddb1.png)\n## 2、ie盒子模型\n![Description](https://img-blog.csdnimg.cn/img_convert/75720f87592f48efa90d85f35faab231.png#pic_center)\nie盒子比较特殊，就如上图，通过css设置width和height并不是只对content设置，而是对border+padding+content这三个属性设置。\n这里的width和height指的是css样式中的。\n\n盒子实际占用的宽=margin-left + width + margin-right\n盒子实际占用的高=margin-top + height + margin-bottom\n\n设置div属性 ：background: pink; width: 200px; height: 200px; padding: 10px; margin: 10px; border: 10px solid red;\n最终盒子实际占用的宽=10+200+10=220 px,盒子实际占用的高=10+200+10 =220 px\n（因为用不了ie所以没有测试了！）",
        create_time: "2021-12-06T13:02:02.000+00:00",
        id: 4,
        identify: 0,
        status: 0,
        subject: 1,
        title: "网页盒子模型",
        type: 0,
        up_count: 11,
        update_time: "2021-12-06T13:02:02.000+00:00",
        url: null,
        user_id: 985669637,
      },
      type: Object
    },
    index: {
      type: Number
    }
  },
  emits: ['good'],
  data(){
    return {
      len: 40,
      thumbs: false,
      article: {}
    }
  },
  computed: {
    showContent(){
      if(this.item.content.length>this.len){
        return this.item.content.slice(0,this.len)+'...';
      }
      return this.item.content;
    },
    time(){
      let t = new Date(this.item.update_time);
      let y=t.getFullYear();let m=t.getMonth()+1;let d=t.getDate();
      return y+'-'+m+'-'+d;
    }
  },
  methods: {
    async giveThumbs(){

      if(localStorage.getItem('id')===undefined||localStorage.getItem('id')==null){
        ElMessageBox.alert('请先登录！','提示消息')
        .catch(() => {
          // catch error
        })
        
      }else{
        this.thumbs = !this.thumbs;
        let that = this;
        if(this.thumbs){
          let data ={
            'discussPostId': this.item.id,
            'op': 1,
            'userId': Number(localStorage.getItem('id'))
          }
          const {data: res} = await this.$http.post('/discussPost/updateUpNum',this.qs.stringify(data));
          this.$emit('good',that.index,1);
        }else{
          let data ={
            'discussPostId': this.item.id,
            'op': -1,
            'userId': Number(localStorage.getItem('id'))
          }
          const {data: res} = await this.$http.post('/discussPost/updateUpNum',this.qs.stringify(data));
          this.$emit('good',that.index,-1);
        }
      }
      
      
      // /discussPost/up

    },
    gotoBlog(id){

      this.$router.push({name: 'blog', params:{id: id}});
    },
    // /support-blog/checkSupportStatus
    async checkThumbs(){
      let data ={
          'discussPostId': this.item.id,
          'userId': Number(localStorage.getItem('id'))
        }
        const {data: res} = await this.$http.post('/support-blog/checkSupportStatus',this.qs.stringify(data));

        if(res.code===0) this.thumbs=true;
    }
  },
  watch: {
    item(newVal, oldVal){
      deep: true
    }
  },
  mounted(){
    this.checkThumbs();
  }
}
</script>

<style scoped lang="less">
.arcticle{
  cursor: pointer;
  font-size: 16px;
  font-family: serif;
  width: 75%;
  background: #fff;
  padding-left: 1.3rem;
  // border-top: .05rem solid #EDECF4;
  margin-bottom: .5rem;
  border-radius: .2rem;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.08);
  div{
    margin-bottom: 1rem;
  }
}
.arcticle:hover{
  // box-shadow: var(--box-shadow);
  background: #fdfdfd;
}
.message{
  text-align: left;
  margin-top: 1rem;
  span.name{
    color: #000;
    cursor: pointer;
  }
  span.name:hover{
    color: var(--blue);
  }
  span{
    color: var(--light-color);
  }
}
.title{
  font-size: 1.2rem;
  text-align: left;
  h4{
    color: #000;
    font-family: serif;
  }
}
.content{
  max-height: 20px;
  color: var(--light-color);
  text-align: left;
  font-size: 0.9rem;
}
.operations{
  display: flex;
  align-items: flex-start;
  font-size: 0.89rem;
  span{
    cursor: pointer;
    margin-right: 30px;
  }
  span:hover{
    color: var(--blue);
  }
}

.blue {
  color: blue;
}
</style>