import {defineStore} from "pinia";
import type {PermissionState} from "@/stores/interface";
// 权限仓库
const usePermissionStore = defineStore("usePermissionStore", {
  state: (): PermissionState => ({
    routes: [],
  }),
});

export default usePermissionStore;
