import {defineStore} from "pinia";
import type {UserState} from "@/stores/interface";
import {removeToken} from "@/utils/token.ts";
//创建用户仓库
const useUserStore = defineStore("useUserStore", {
  state: (): UserState => ({
    id: null,
    avatar: '',
    roleList: [],
    permissionList: []
  }),
  actions: {
    LogOut() {
      return new Promise((resolve, reject) => {
        logout()
          .then(() => {
            this.id = null;
            this.avatar = "";
            this.roleList = [];
            this.permissionList = [];
            removeToken();
            resolve(null);
          })
          .catch((error) => {
            reject(error);
          });
      });
    },
  },
  getters: {},
});
// 暴露用户仓库
export default useUserStore;
