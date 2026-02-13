import { defineStore } from "pinia";
import type { PermissionState } from "@/stores/interface";
import type { RouteRecordRaw } from "vue-router";
import { constantRoutes } from "@/router";
import { getUserMenu } from "@/api/menu";

const modules = import.meta.glob("../../views/**/**.vue");
export const ParentView = () => import("@/components/ParentView/index.vue");
export const Layout = () => import("@/layout/index.vue");

const filterAsyncRoutes = (routes: RouteRecordRaw[]) => {
  const res: RouteRecordRaw[] = [];
  routes.forEach((route) => {
    const tmp = { ...route } as any;
    if (tmp.component === "Layout") {
      tmp.component = Layout;
    } else if (tmp.component === "ParentView") {
      tmp.component = ParentView;
    } else {
      Object.keys(modules).forEach((url) => {
        if (url == `../../views${tmp.component}.vue`) {
          tmp.component = modules[url];
        }
      });
    }
    res.push(tmp);
    if (tmp.children) {
      tmp.children = filterAsyncRoutes(tmp.children);
    }
  });
  return res;
};

const usePermissionStore = defineStore("usePermissionStore", {
  state: (): PermissionState => ({
    routes: []
  }),
  actions: {
    setRoutes(routes: RouteRecordRaw[]) {
      this.routes = constantRoutes.concat(routes);
    },
    generateRoutes(): Promise<RouteRecordRaw[]> {
      return new Promise((resolve, reject) => {
        getUserMenu()
          .then(({ data }) => {
            if (data.flag) {
              const asyncRoutes = data.data;
              const accessedRoutes = filterAsyncRoutes(asyncRoutes);
              this.setRoutes(accessedRoutes);
              resolve(accessedRoutes);
            }
          })
          .catch((error) => {
            reject(error);
          });
      });
    }
  }
});

export default usePermissionStore;
