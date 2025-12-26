import {defineStore} from "pinia";
import type {UserState} from "@/store/interface";
import type {LoginForm} from "@/api/login/types";
import {login, logout} from "@/api/login";
import {removeToken, setToken} from "@/utils/token.ts";
import {getUserInfo} from "@/api/user";

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
                        setToken(data.data);
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
                    removeToken();
                    resolve(null);
                }).catch((error) => {
                    reject(error);
                });
            });
        },
        // 获取用户信息
        GetInfo() {
            return new Promise((resolve, reject) => {
                getUserInfo()
                    .then(({data}) => {
                        if (data.flag) {
                            this.id = data.data.id;
                            this.avatar = data.data.avatar;
                            this.roleList = data.data.roleList;
                            this.permissionList = data.data.permissionList;
                        }
                        resolve(data);
                    })
                    .catch((error) => {
                        reject(error);
                    });
            });
        },
    },
});

export default useUserStore;