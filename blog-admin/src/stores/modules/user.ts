import {defineStore} from "pinia";
import type {UserState} from "@/stores/interface";
//创建用户仓库
const useUserStore = defineStore("useUserStore", {
  state: (): UserState => ({
    id: null,
    avatar: '',
    roleList: [],
    permissionList: []
  }),
  actions: {},
  getters: {},
});
// 暴露用户仓库
export default useUserStore;
