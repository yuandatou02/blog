import nProgress from "nprogress";
import router from "@/router";
import useStore from "@/store";
import {getToken} from "@/utils/token.ts";
import {isRelogin} from "@/utils/request.ts";
import {ElMessage} from "element-plus";

nProgress.configure({
    easing: "ease",
    speed: 500,
    showSpinner: false,
    trickleSpeed: 200,
    minimum: 0.3,
});

// 白名单路由
const whiteList = ["/login"];

router.beforeEach((to, _from, next) => {
    nProgress.start();
    const {user, permission} = useStore();
    // 判断是否有token
    if (getToken()) {
        if (to.path === "/login") {
            next({path: "/"});
            nProgress.done();
        } else {
            if (user.roleList.length === 0) {
                isRelogin.show = true;
                // 判断当前用户是否已拉取完user_info信息
                user
                    .GetInfo()
                    .then(() => {
                        isRelogin.show = false;
                        permission.generateRoutes().then((accessRoutes) => {
                            accessRoutes.forEach((route) => {
                                router.addRoute(route);
                            });
                            next({...to, replace: true});
                        });
                    })
                    .catch((err) => {
                        user.Logout().then(() => {
                            ElMessage.error(err);
                            next({path: "/login"});
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
            nProgress.done();
        }
    }
});

router.afterEach(() => {
    nProgress.done();
});