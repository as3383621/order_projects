import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '后台管理系统',
    component: () => import('../views/Manage.vue'),
    redirect: "/root/login",
    children: [
      {path: 'user', name: '用户管理/普通用户管理', component: () => import('../views/User_Manage.vue')},
      {path: 'root', name: '用户管理/管理员管理', component: () => import('../views/Root_Manage.vue')},
      {path: 'food', name: '菜品管理', component: () => import('../views/Food_Manage.vue')},
      {path: 'comment', name: '评论管理', component: () => import('../views/Comment_Manage')},
      {path: 'news', name: '新闻管理', component: () => import('../views/News_Manage')},
      {path: 'NotShipped', name: '未发货订单管理', component: () => import('../views/NotShipped_Manage')},
      {path: 'Shipped', name: '已发货订单管理', component: () => import('../views/Shipped_Manage')},
    ]
  },
  {
    path: '/root/login',
    name: 'RootLogin',
    component: () => import('../views/Login_Root.vue')
  },
  {
    path: '/root/register',
    name: 'RootRegister',
    component: () => import('../views/Register_Root.vue')
  },
  {
    path: '/user/login',
    name: 'UserLogin',
    component: () => import('../views/Login_User.vue')
  },
  {
    path: '/user/register',
    name: 'UserRegister',
    component: () => import('../views/Register_User.vue')
  },
  {
    path: '/index',
    name: 'index',
    component: () => import('../views/index.vue')
  },
  {
    path: '/select',
    name: 'Select',
    component: () => import('../views/Select.vue')
  },
  {
    path: '/minute',
    name: 'Food',
    component: () => import('../views/Food.vue')
  },
  {
    path: '/NewsList',
    name: 'news_list',
    component: () => import('../views/News_list.vue')
  },
  {
    path: '/NewsMinute',
    name: 'news_minute',
    component: () => import('../views/News_minute.vue')
  },
  {
    path: '/car',
    name: 'car',
    component: () => import('../views/Car.vue')
  },
  {
    path: '/more',
    name: 'more',
    component: () => import('../views/More.vue')
  },
  {
    path: '/SelectShipped',
    name: 'select_shipped',
    component: () => import('../views/Shipped.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
