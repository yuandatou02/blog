import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: '/login',
        name: '登陆',
        component: () => import('@/views/login/index.vue'),
        meta: {hidden: true},
    },
    {
        path: "/:pathMatch(.*)*",
        component: () => import("@/views/error/404.vue"),
        meta: {
            hidden: true,
        },
    },
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

export default router
