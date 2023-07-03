# javaee

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).



## 11月20日

寻找到markdown编辑器相关的插件

- v-md-editor

  > 网址：`https://ckang1229.gitee.io/vue-markdown-editor/zh/`
  >
  > 简介：v-md-editor 是基于 Vue 开发的 markdown 编辑器组件
  >
  > 分类：轻量版编辑器、进阶版编辑器、 预览组件、html 预览组件

- md-editor-v3

  > 网址：`https://imzbf.github.io/md-editor-v3/index`
  >
  > 简介：Markdown编辑器Vue3版本，使用jsx和typescript语法开发，支持切换主题、prettier美化文本等。
  >
  > 分类：主题切换，基本功能都能满足。



### Markdown 编辑器选择

#### v-md-editor

> markdown 解析使用 [markdown-it (opens new window)](https://github.com/markdown-it/markdown-it)来实现,代码块解析使用 [prism (opens new window)](https://github.com/PrismJS/prism)来实现。
>
> 编辑器由 CodeMirror 实现，拥有较为良好的编辑体验，但是包的体积会变大。

==导入主体包==

```sh
# 使用 npm
npm i @kangc/v-md-editor@next -S

# 使用 yarn
yarn add @kangc/v-md-editor@next
```

==使用**vuepress 主题**==

```sh
# 安装扩展代码高亮语言包
# yarn
yarn add babel-plugin-prismjs --dev

# npm
npm install babel-plugin-prismjs

# Vite安装拓展代码高亮语言包
# yarn
yarn add vite-plugin-prismjs --dev

# npm
npm install vite-plugin-prismjs
```

导入语言包,下面都是导入所有语言包的，可以按需导入。

```js
// babel.config.js
const components = require('prismjs/components');
const allLanguages = Object.keys(components.languages).filter((item) => item !== 'meta');

module.exports = {
  plugins: [
    [
      'prismjs',
      {
        languages: allLanguages,
      },
    ],
  ],
};

// vite.config.js
import { defineConfig } from 'vite';
import prismjs from 'vite-plugin-prismjs';

export default defineConfig({
  plugins: [
    prismjs({
      languages: 'all',
    }),
  ],
});
```

$main.js$ 导入主题

```js
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

import Prism from 'prismjs';

VueMarkdownEditor.use(vuepressTheme, {
  Prism,
  extend(md) {
    // md为 markdown-it 实例，可以在此处进行修改配置,并使用 plugin 进行语法扩展
    // md.set(option).use(plugin);
  },
});
```



效果，实现了代码高亮

![image-20211120115619197](/Users/penghuwan/Desktop/code/javaee/前端/README.assets/image-20211120115619197.png)

github主题是自带的，不需下载，直接在main.js中使用就好了。



==导入codemirror 编辑器的相关资源==

在主体包的时候，直接装好了，所以不需再下载。

$main.js$

```js
// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
// mode
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/vue/vue';
// edit
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchbrackets';
// placeholder
import 'codemirror/addon/display/placeholder';
// active-line
import 'codemirror/addon/selection/active-line';
// scrollbar
import 'codemirror/addon/scroll/simplescrollbars';
import 'codemirror/addon/scroll/simplescrollbars.css';
// style
import 'codemirror/lib/codemirror.css';

VMdEditor.Codemirror = Codemirror;
```



==整体导入==

$main.js$

```js
import { createApp } from 'vue'
import App from './App.vue'

import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor';
import '@kangc/v-md-editor/lib/style/codemirror-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

import Prism from 'prismjs';

// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
// mode
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/vue/vue';
// edit
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchbrackets';
// placeholder
import 'codemirror/addon/display/placeholder';
// active-line
import 'codemirror/addon/selection/active-line';
// scrollbar
import 'codemirror/addon/scroll/simplescrollbars';
import 'codemirror/addon/scroll/simplescrollbars.css';
// style
import 'codemirror/lib/codemirror.css';

VMdEditor.Codemirror = Codemirror;
VMdEditor.use(vuepressTheme, {
  Prism,
  extend(md) {
    // md为 markdown-it 实例，可以在此处进行修改配置,并使用 plugin 进行语法扩展
    // md.set(option).use(plugin);
  },
});


const app = createApp(App);
app.use(VMdEditor);
app.mount('#app')

```

$vite.config.js$

```js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import prismjs from 'vite-plugin-prismjs';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    prismjs({
      languages: 'all',
    }),
  ]
})

```

最终效果![image-20211120120237094](/Users/penghuwan/Desktop/code/javaee/前端/README.assets/image-20211120120237094.png)



#### md-editor-v3

> 编辑器比较简单，但是markdown语法显示蛮不错的！



==导入主体包==

```sh
yarn add md-editor-v3
```

$main.js$

```js
import MdEditor from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'

const app = createApp(App);
app.use(MdEditor);
```

$xxx.vue$

theme是表示主题，在这里使用的是`vuepress`,还有`github`和`dark`

```vue
<template>
  <md-editor-v3 class="write" v-model="text" :screenfullJs="screenfull" />
</template>

<script>
export default {
  data(){
    return {
      text: 'haha',
      theme: 'vuepress',
    }
  }
}
</script>

<style scoped>
.write{
  width: 100%;
  height: 800px;
}
</style>
```

最终效果：![image-20211120160304858](/Users/penghuwan/Desktop/code/javaee/前端/README.assets/image-20211120160304858.png)



因为`v-md-editor`的编辑器更好，所以选择使用它！



#### v-md-editor在 vue-cli上的配置

> 在 vue-cli上我们使用的是 
>
> - `CodeMirror`编辑器
> - 主题使用 `vuepress` markdown 解析使用markdown-it来实现，代码块解析使用prism来实现。

**安装**

```sh
# 安装主体包
npm i @kangc/v-md-editor@next -S

# 安装 prism 的代码高亮包
npm install babel-plugin-prismjs
```

**配置**

`babel.config.js` 配置prism解析代码块高亮。

导入所有的语言高亮（实际上是会造成冗余的，因为prism支持的语言很多，很多都用不到）

```js
const components = require('prismjs/components');
const allLanguages = Object.keys(components.languages).filter((item) => item !== 'meta');

module.exports = {
  presets: [
    '@vue/cli-plugin-babel/preset',
  ],
  plugins: [
    [
      'prismjs',
      {
        languages: allLanguages,
      },
    ],
  ],
}
```

`main.js`

将该组件导入到Vue实例中，同时配置CodeMirror编辑器

```js
import { createApp } from 'vue'
import App from './App.vue'

import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor';
import '@kangc/v-md-editor/lib/style/codemirror-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';

import Prism from 'prismjs';

// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
// mode
import 'codemirror/mode/markdown/markdown';
import 'codemirror/mode/javascript/javascript';
import 'codemirror/mode/css/css';
import 'codemirror/mode/htmlmixed/htmlmixed';
import 'codemirror/mode/vue/vue';
// edit
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchbrackets';
// placeholder
import 'codemirror/addon/display/placeholder';
// active-line
import 'codemirror/addon/selection/active-line';
// scrollbar
import 'codemirror/addon/scroll/simplescrollbars';
import 'codemirror/addon/scroll/simplescrollbars.css';
// style
import 'codemirror/lib/codemirror.css';

VMdEditor.Codemirror = Codemirror;
VMdEditor.use(vuepressTheme, {
  Prism,
  extend(md) {
    // md为 markdown-it 实例，可以在此处进行修改配置,并使用 plugin 进行语法扩展
    // md.set(option).use(plugin);
  },
});


const app = createApp(App);
app.use(VMdEditor);
app.mount('#app')
```

**使用**

```vue
<template>
   <v-md-editor class="write" v-model="text" height="100%"></v-md-editor>
</template>

<script>
export default {
  data() {
    return {
      text: ''
    }
  },
}
</script>

<style scoped>

</style>
```

导入到App.vue中使用，

最终效果：

![image-20211120171140592](/Users/penghuwan/Desktop/code/javaee/前端/README.assets/image-20211120171140592.png)

![image-20211120171316489](/Users/penghuwan/Desktop/code/javaee/前端/README.assets/image-20211120171316489.png)



# 11月22日

vuex的学习

我们的用户端使用vue-cli，组件化开发，需要使用到组件通信，使用vuex更方便。

> 学习vuex





# 11月29日

问题记录：

- 个人界面的设置和文章界面，菜单栏点击问题！



# 12月1日

注册界面：

![image-20211201152834228](/Users/penghuwan/Desktop/课/JavaEE/javaEE/前端/README.assets/image-20211201152834228.png)

登录界面：

![image-20211201152905454](/Users/penghuwan/Desktop/课/JavaEE/javaEE/前端/README.assets/image-20211201152905454.png)



**数据刷新的处理**

```js
created(){
  // 实现页面刷新，不丢失数据
  window.addEventListener('beforeunload',()=>{
    sessionStorage.setItem('list', JSON.stringify(this.$store.state))
  })
  try{
    sessionStorage.getItem('list') && this.$store.replaceState(Object.assign({},this.$store.state,JSON.parse(sessionStorage.getItem('list'))))
  }catch(err) {
    console.log(err);
  }
  sessionStorage.removeItem("list");   
}
```



# 12月4日

需要需求：

- 多表查询，查询用户的姓名
  - 主页文章查询
  - 博文内部展示
  - 博文内部评论
  - 回复
- 增加取消点赞



# 12月5日

需要需求：

- 增加评论的点赞字段



# 12月7日

遇到问题：

<font color=red>路由跳转到同一组件，传的参数不同。组件不实现刷新！</font> [解决办法](https://juejin.cn/post/6905729933558874119)



# 12月8日

基本完成写文章，评论，回复等功能！



# 12月14日

细节优化：

- 点击删除的提醒
- 页面加载的过程
- 登录的提醒



# 12月18日

**今日完成**

- 专栏操作基本完成
- 收藏文章的时间没有获得数据，暂时用最新时间来代替

**bug记录**

- 评论时间不会自动插入（文章和回复都会自动的填入创建时间）

**待解决**

- 专栏删除，不要把文章全删除，应该修改所有的文章column_id为-1,再删除专栏
- 文章删除，应该考虑评论和回复。
