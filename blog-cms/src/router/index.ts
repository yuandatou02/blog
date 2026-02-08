import { createRouter, createWebHistory, type RouteRecordRaw } from "vue-router";

export const constantRoutes: RouteRecordRaw[] = [
    {
        path: "/:pathMatch(.*)*",
        component: () => import("@/views/error/404.vue"),
        meta: {
            hidden: true
        }
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: constantRoutes
});

export default router;
