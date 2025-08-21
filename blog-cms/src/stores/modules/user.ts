import {defineStore} from "pinia";
import type {UserState} from "@/stores/interface";
import type {loginRequest} from "@/api/login/types.ts";
import {login} from "@/api/login";
import {setToken} from "@/utils/token.ts";

const useUserStore = defineStore("useUserStore", {
    state: (): UserState => ({
        id: null,
        avatar: "",
        roleList: [],
        permissionList: [],
    }),
    actions: {
        LogIn(LoginForm: loginRequest) {
            return new Promise((resolve, reject) => {
                login(LoginForm)
                    .then(({data}) => {
                        if (data.flag) {
                            setToken(data.data);
                            resolve(data);
                        } else {
                            reject(data.msg);
                        }
                    })
                    .catch((error) => {
                        reject(error);
                    });
            });
        },
    }
});

export default useUserStore;