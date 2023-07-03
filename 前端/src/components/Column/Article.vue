<template>
  <div class="ourArticle" @click="gotoBlog()">
    <h3> <span v-if="article.btype===0">笔记</span> <span v-else-if="article.btype===1">踩坑记录</span> <span v-else>提问</span> {{article.title}}</h3>
    <div class="ArticleContent">{{ content }}</div>
  </div>
</template>

<script>
export default {
  emits: ['update'],
  props: {
    article: {
      type: Object,
      default: {
        btype: 1,
        column_id: 0,
        comment_count: 0,
        content: "#### 程序计数器\n\n- 程序计数器是一块较小的内存空间\n- 它可以看作是当前线程所执行的字节码的行号指示器。\n- 在虚拟机的概念模型里，字节码解释器工作就是通过改变这个计数器的值来选取下一条需要执行的字节码指令，**分支、循环、跳转、异常处理、线程恢复等基础功能**都需要依赖这个计数器来完成\n\n- 由于Java虚拟机的多线程是通过线程轮流切换并分配处理器执行事件的方式来实现的，在任何一个确定的时刻，一个处理器都只会执行一条线程中的指令。因此，为了线程切换后能恢复到正确的执行位置，每条线程都需要有一个**独立的程序计数器**，各条线程之间计数器互不影响，独立存储，我们称这类内存区域为**\"线程私有\"的内存**\n\n- **如果线程正在执行的是一个Java方法，这个计数器记录的是正在执行的虚**拟机字节码指令的地址：如果正在执行的是Native方法，这个计数器则为空。此内存区域是唯一一个在Java虚拟机规范中没有规定任何OutOfMemoryError情况的区域\n\n#### Java虚拟机栈\n\n- 线程私有，生命周期与线程相同\n- 虚拟机栈描述的是 Java方法执行的内存模型：\n\t- 每个方法在执行的同时都会创建一个栈帧用于存储局部变量表、操作数栈、动态链接、方法出口等信息\n\t- 每一个方法从调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中入栈到出栈的过程\n\n- 经常有人把Java内存区分为**堆内存**和**栈内存**（分法粗糙，内存区域划分实际上远比这复杂），这种划分方式只能说明大部分程序员最关注的、与对象内存分配关系最密切的内存区域是这两块。\n- 其中所指的\"栈\"就是Java虚拟机栈，或者说是虚拟机栈中的局部变量表的部分\n\n- 局部变量表存放了**基本数据类型**（boolean、byte、char、short、int、float、long、double）、对象引用（reference类型，它不等同于对象本身，可能是一个指向对象起始地址的引用指针，也可能是指向一个代表对象的句柄或其它与此对象相关的位置）和returnAddress类型（指向了一条字节码指令的地址）\n- 其中64位长度的 long和 double类型的数据会占用2个局部变量空间（Slot)，其余的数据类型只占用1个。局部变量表所需的内存空间在编译期间完成分配，当进入一个方法时,这个方法需要在帧中分配多大的局部变量空间是完全确定的，在方法运行期间不会改变局部变量表的大小。\n- 在Java虚拟机规范中，对这个区域规定了两种异常状况:如果线程请求的栈深度大于虚拟机所允许的深度，将抛出StackOverflowError异常;如果虚拟机栈可以动态扩展(当前大部分的Java虚拟机都可动态扩展，只不过Java虚拟机规范中也允许固定长度的虚拟机栈),如果扩展时无法申请到足够的内存，就会抛出 OutOfMemoryError异常。\n\n#### 本地方法栈\n\n- 与虚拟机栈发挥的作用非常相似，它们之间的区别是：\n\t- 虚拟机栈：为虚拟机执行Java方法（也就是字节码）服务\n\t- 本地方法栈：为虚拟机使用到的Native方法服务\n\n#### Java堆\n\n- 是Java虚拟机所管理的内存中最大的一块\n- Java堆是被所有线程共享的一块内存区域\n- 在虚拟机启动时创建\n- 此内存区域的唯一目的就是**存放对象实例**，几乎所有的对象实例都在这里分配内存。这一点Java虚拟机规范中描述是：所有对象实例以及数组都要在堆上分配\n- Java堆是垃圾收集器管理的主要区域，因此很多时候也被称为`GC堆`，从内存回收的角度来看，由于现在收集器基本都采用分代收集算法，所以Java堆中还可以细分为：老年代和新生代；从内存分配的角度来看，线程共享的Java堆中可能划分出多个线程私有的分配缓冲区（TLAB），不过无论如何划分，都与存放内容无关，无论哪个区域，存储的都仍然是对象实例，进一步的划分目的是为了更好的回收内存，或者更快地分配内存\n- 根据Java虚拟机规范的规定，Java堆可以处于物理上不连续的内存空间中，只要逻辑上是连续的即可，如果在堆中没有内存完成实例分配，并且堆也无法再扩展时，将会抛出`OutOfMemoryError`异常\n\n#### 方法区\n\n- 方法区和Java堆一样，是各个线程共享的内存区域\n- 它用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据\n- 虽然Java虚拟机规范把方法区描述为堆的一个逻辑部分，但它却还有一个别名叫Non-Heap（非堆），目的应该是和Java堆区分开来",
        create_time: "2021-12-08T12:48:03.000+00:00",
        id: 6,
        identify: 0,
        status: 0,
        subject: 1,
        title: "Java虚拟机",
        type: 0,
        up_count: 1,
        update_time: "2021-12-08T12:48:03.000+00:00",
        url: null,
        user_id: 985669639,
      },
      required: true
    }
  },
  data(){
    return {
      isMe: false
    }
  },
  computed: {
    time(){
      let t = new Date(this.article.update_time);
      let y=t.getFullYear();let m=t.getMonth()+1;let d=t.getDate();
      return y+'-'+m+'-'+d;
    },
    content(){
      if(this.article.content.length>=165) return this.article.content.slice(0,165)+'...';
      else return this.article.content;
    }
  },
  mounted(){
    if(sessionStorage.getItem('visit')===localStorage.getItem('id')){
      this.isMe=true;
    }
  },
  methods: {
    gotoBlog(){
      this.$router.push({name: 'blog', params:{id: this.article.id}});
    },
    // 删除
    async deleteArticle(){
      let data ={
        'id': this.article.id
      }
      const {data: res} = await this.$http.post('/discussPost/deleteOnePost' , this.qs.stringify(data));
      if(res.code===0){
        this.$emit('update');
      }
    },
    goWrite(){
      this.$store.commit('setHeadFalse');
      sessionStorage.setItem('blogId',this.article.id);
      this.$router.push('/write');
    }
  }
}
</script>

<style scoped lang="less">
.ourArticle{
  margin-bottom: .5rem;
  border-bottom: 1px solid var(--background);
  padding: 1rem;
  text-align: start;
  cursor: pointer;
  h3{
    font-size: 1.1rem;
    margin-bottom: .5rem;
    span{
      font-size: .8rem;
      padding: .2rem;
      border-radius: .5rem;
      display: inline-block;
      background: var(--very-light-blue);
      color: var(--blue);
      font-weight: 400;
    }
  }
  .ArticleContent{
    margin-bottom: .5rem;
    font-size: .95rem;
    color: var(--light--gray);
  }
}
.ourArticle:hover{
  background: var(--background);
  h3{
    color: var(--light--blue);
  }
}
</style>