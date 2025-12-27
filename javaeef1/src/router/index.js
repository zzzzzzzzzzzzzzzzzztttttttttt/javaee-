import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      {path:'/',redirect:'/home'},

      {path: '/Login', name: 'Login', meta: {title: '登录'}, component: () => import('../views/Login.vue')},
      {path: '/Home', name: 'Home', meta: {title: '主页'}, component: () => import('../views/Home.vue')},
      {path: '/Register', name: 'Register', meta: {title: '主页'}, component: () => import('../views/Register.vue')},
      {path: '/Cart', name: 'Cart', meta: {title: '主页'}, component: () => import('../views/Cart.vue')},
      {path: '/Order', name: 'Order', meta: {title: '主页'}, component: () => import('../views/Order.vue')},
  ],
})
router.beforeEach((to, from, next)=>{
    document.title = to.meta.title
    next()
})

export default router



