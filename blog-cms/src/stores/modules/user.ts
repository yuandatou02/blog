import { defineStore } from "pinia";
import type { UserState } from "@/stores/interface";
import { login } from "@/api/login";
import type { LoginForm } from "@/api/login/types";

const useUserState = defineStore("useUserState", {
    state: (): UserState => ({
        id: null,
        avatar: "",
        roleList: [],
        permissionList: []
    }),
    actions: {
        LogIn(LoginForm: LoginForm) {
            return new Promise((resolve, reject) => {
                login(LoginForm)
                    .then(({ data }) => {
                        if (data.flag) {
                            // setToken(data.data);
                            resolve(data);
                        } else {
                            reject(data.message);
                        }
                    })
                    .catch((error) => {
                        reject(error);
                    });
            });
        }
    }
});

export default useUserState;
