import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
import "./assets/css/global.css"
import "./assets/font/iconfont.css"
import axios from 'axios'
import * as echarts from 'echarts'
import SocketService from './utils/socket.js'

// markdown相关
import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

// highlightjs
import hljs from 'highlight.js';

VMdPreview.use(githubTheme, {
  Hljs: hljs,
});


//连接服务端
SocketService.Instance.connect()

const app = createApp(App)
installElementPlus(app)
app
    .use(store)
    .use(router)
	.use(VMdPreview)
app.config.globalProperties.$axios=axios
app.config.globalProperties.$echarts=echarts
app.config.globalProperties.$socket=SocketService.instance
app.mount('#app')