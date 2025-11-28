import {defineStore} from "pinia";
import type {UserState} from "@/store/interface";
import type {LoginForm} from "@/api/login/types";
import {login} from "@/api/login";

const useUserStore = defineStore("useUserStore", {
    state: (): UserState => ({
        id: null,
        avatar: "",
        roleList: [],
        permissionList: [],
    }),
    actions: {
        Login(loginForm: LoginForm) {
            return new Promise((resolve, reject) => {
                login(loginForm).then(({data}) => {
                    if (data.flag) {
                        // 保存token
                        resolve(data);
                    } else {
                        reject(data.msg);
                    }
                });
            });
        }
    }
});

export default useUserStore;