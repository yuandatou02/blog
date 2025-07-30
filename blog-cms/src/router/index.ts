import { createRouter, createWebHistory } from 'vue-router'
const routes = [
  { path: '/', component: () => import('@/views/Home/index.vue') },
  { path: '/login', component: () => import('@/views/login/index.vue') }
]
export default createRouter({ history: createWebHistory(), routes })
