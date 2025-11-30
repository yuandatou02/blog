import {createRouter, createWebHashHistory, type RouteRecordRaw} from "vue-router";

export const constantRoutes: RouteRecordRaw[] = [
    {
        path: "/login",
        name: "登录",
        component: () => import("@/views/login/index.vue"),
        meta: {
            hidden: true,
        }
    },
    {
        path: "/:pathMatch(.*)*",
        component: () => import("@/views/error/404.vue"),
        meta: {
            hidden: true,
        }
    }
];


const router = createRouter({
    history: createWebHashHistory(),
    routes: constantRoutes
});

export default router;