import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router';
// 路由项目
export const constantRoutes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: '登录',
    component: () => import('@/views/login/index.vue'),
    meta: {
      hidden: true,
    }
  },
]
// 创建路由
const router = createRouter(
  {
    history: createWebHistory(),
    routes: constantRoutes,
  }
);
export default router;
