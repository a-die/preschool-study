import { createRouter, createWebHistory } from 'vue-router'

// 导入组件
import test from '@/components/test.vue';
import Home from '@/components/homePage/Home.vue';
import Article from '@/components/homePage/Article.vue';
import Login from '@/components/Login';

// 博文组件
import Blog from '@/components/Blog/Blog'


const routes = [
  {
    path: '/',
    redirect: '/index'
  },
  {
	  path: '/index',
	  component: () => import('@/components/TuoPu.vue')
  }
  ,{
    path: '/home/:type/:subject', 
    name: 'home',
    component: Home
  },{
    path: '/test', 
    component: test
  },{
    path: '/login', 
    component: Login
  },{
    path: '/log-reg', 
    component: () => import('@/components/Login&Register.vue')
  },{
    path: '/blog/:id',
    name: 'blog',
    component: Blog
  }, // 在路由后面声明 :id 防止页面刷新后，参数丢失
  {
    path: '/register', 
    name: 'register',
    component: () => import('@/components/Register.vue')
  },
  {
    path: '/write',
    name: 'write',
    component: () => import('@/components/Write/Write.vue')
  },
  {
    path: '/mine',
    name: 'mine',
    component: () => import('@/components/Mine/Mine.vue'),
    redirect: '/mine/myPage',
    children:[
      {
        name: 'mine-mypage',
        path: 'myPage',
        component: () => import('@/components/Mine/MineIndex.vue')
      },
      {
        path: 'myarticle',
        component: () => import('@/components/Mine/MineArticle.vue'),
        redirect: '/mine/myarticle/publishedNotes',
        children:[
          {
            path: 'published',
            component: () => import('@/components/Mine/MineArticle/PublishedArticle.vue')
          },
          {
            path: 'publishedNotes',
            component: () => import('@/components/Mine/MineArticle/PublishedNotes.vue')
          },
          {
            path: 'publishedQuiz',
            component: () => import('@/components/Mine/MineArticle/PublishedQuiz.vue')
          },
          {
            path: 'unpublished',
            component: () => import('@/components/Mine/MineArticle/UnpublishedArticle.vue')
          },
          {
            path: 'collected',
            component: () => import('@/components/Mine/MineArticle/CollectedArticle.vue')
          }
        ]
      },
      {
        path: 'mycolumn',
        component: () => import('@/components/Mine/MineColumn.vue')
      },
      {
        path: 'mystudy',
        component: () => import('@/components/Mine/MineStudy.vue')
      },
      {
        path: 'mytalking',
        component: () => import('@/components/Mine/MineTalking.vue')
      },
      {
        path: 'mysetting',
        component: () => import('@/components/Mine/MineSetting.vue'),
        redirect: '/mine/mysetting/settingSafe',
        children: [
          {
            path: 'settingInfo',
            component: () => import('@/components/Mine/MineSetting/SettingInfo.vue')
          },
          {
            path: 'settingMyPage',
            component: () => import('@/components/Mine/MineSetting/SettingMypage.vue')
          },
          {
            path: 'settingSafe',
            component: () => import('@/components/Mine/MineSetting/SettingSafe.vue')
          }
        ]
      },
    ]
  },
  {
    path: '/search/:content/:btype',
    name: 'search',
    component: () => import('@/components/Search/Search.vue')
  },
  {
    path: '/column/:userId/:column',
    name: 'column',
    component: () => import('@/components/Column/ColumnIndex.vue')
  },
  {
    path: '/columnManage/:column',
    name: 'columnManage',
    component: () => import('@/components/Column/ColumnManage.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
