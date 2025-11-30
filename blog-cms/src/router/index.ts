import {createRouter, createWebHashHistory, type RouteRecordRaw} from "vue-router";
import Layout from "@/layouts/index.vue";

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
    },
    {
        path: "/",
        component: Layout,
        redirect: "/index",
        children: [
            {
                path: "index",
                component: () => import("@/views/home/index.vue"),
                name: "Index",
                meta: {title: "首页", icon: "dashboard", affix: true}
            }
        ]
    }
];


const router = createRouter({
    history: createWebHashHistory(),
    routes: constantRoutes
});

export default router;