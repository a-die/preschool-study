import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import ElementPlus from 'element-plus'
import * as echarts from 'echarts'
import qs from 'qs'

import store from './store/index.js'

import jquery from 'jquery'



// markdown 编辑器
import VMdEditor from '@kangc/v-md-editor/lib/codemirror-editor';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import Prism from 'prismjs';
// codemirror 编辑器的相关资源
import Codemirror from 'codemirror';
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
// highlightjs
import hljs from 'highlight.js';

import '@kangc/v-md-editor/lib/style/preview.css';
import '@kangc/v-md-editor/lib/theme/style/github.css';

import 'element-plus/dist/index.css'
import '@fortawesome/fontawesome-free/css/all.css'
import '@fortawesome/fontawesome-free/js/all.js'


import '@kangc/v-md-editor/lib/style/codemirror-editor.css';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';




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

VMdPreview.use(githubTheme, {
  Hljs: hljs,
});


axios.defaults.baseURL = 'http://localhost:8081'

const app = createApp(App);
app.use(VMdEditor);
app.use(VMdPreview);
app.use(store);
app.config.globalProperties.$echarts=echarts
app.config.globalProperties.$http = axios;
app.config.globalProperties.qs = qs;
app.config.globalProperties.$=jquery;
app.use(router).use(ElementPlus).mount('#app');
