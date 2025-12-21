import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      {path:'/',redirect:'/home'},

      {path: '/Login', name: 'Login', meta: {title: '登录'}, component: () => import('../views/Login.vue')},
      {path: '/Home', name: 'Home', meta: {title: '主页'}, component: () => import('../views/Home.vue')},
      {path: '/Register', name: 'register', meta: {title: '主页'}, component: () => import('../views/Register.vue')},
      {path: '/Cart', name: 'cart', meta: {title: '主页'}, component: () => import('../views/Cart.vue')},
      {path: '/order-confirm', name: 'order-confirm', meta: {title: '主页'}, component: () => import('../views/order-confirm.vue')},
      {path: '/order-list', name: 'order-list', meta: {title: '主页'}, component: () => import('../views/order-list.vue')},
      {path: '/Charge', name: 'Charge', meta: {title: '主页'}, component: () => import('../views/Charge.vue')},

  ],
})
router.beforeEach((to, from, next)=>{
    document.title = to.meta.title
    next()
})

export default router



