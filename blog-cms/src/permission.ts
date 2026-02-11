import router from "@/router";
import useStore from "@/stores";
import { getToken } from "@/utils/token";
import NProgress from "nprogress";

// 是否显示重新登录
export const isRelogin = { show: false };

NProgress.configure({
    easing: "ease",
    speed: 500,
    showSpinner: false,
    trickleSpeed: 200,
    minimum: 0.3
});

// 白名单路由
const whiteList = ["/login"];

router.beforeEach((to, _from, next) => {
    NProgress.start();
    const { user, permission } = useStore();
    // 判断是否有token
    if (getToken()) {
        if (to.path === "/login") {
            next({ path: "/" });
            NProgress.done();
        } else {
            if (user.roleList.length === 0) {
                isRelogin.show = true;
                // 判断当前用户是否已拉取完user_info信息
                user.GetInfo()
                    .then(() => {
                        isRelogin.show = false;
                        permission.generateRoutes().then((accessRoutes) => {
                            accessRoutes.forEach((route) => {
                                router.addRoute(route);
                            });
                            next({ ...to, replace: true });
                        });
                    })
                    .catch((err) => {
                        user.LogOut().then(() => {
                            ElMessage.error(err);
                            next({ path: "/login" });
                        });
                    });
            } else {
                next();
            }
        }
    } else {
        // 未登录可以访问白名单页面(登录页面)
        if (whiteList.indexOf(to.path) !== -1) {
            next();
        } else {
            next(`/login?redirect=${to.path}`);
            NProgress.done();
        }
    }
});

router.afterEach(() => {
    NProgress.done();
});
