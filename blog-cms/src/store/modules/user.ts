import {defineStore} from "pinia";
import type {UserState} from "@/store/interface";
import type {LoginForm} from "@/api/login/types";
import {login, logout} from "@/api/login";

const useUserStore = defineStore("useUserStore", {
    state: (): UserState => ({
        id: null,
        avatar: "",
        roleList: [],
        permissionList: [],
    }),
    actions: {
        // 登录
        Login(loginForm: LoginForm) {
            return new Promise((resolve, reject) => {
                login(loginForm).then(({data}) => {
                    if (data.flag) {
                        // TODO:使用cookie存储token
                        resolve(data);
                    } else {
                        reject(data.msg);
                    }
                }).catch((error) => {
                    reject(error);
                });
            });
        },
        // 登出
        Logout() {
            return new Promise((resolve, reject) => {
                logout().then(() => {
                    this.id = null;
                    this.avatar = "";
                    this.roleList = [];
                    this.permissionList = [];
                    // TODO:清除cookie
                    resolve(null);
                }).catch((error) => {
                    reject(error);
                });
            });
        },
    },
});

export default useUserStore;