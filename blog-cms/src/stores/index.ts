import useUserState from "@/stores/modules/user.ts";
import useAppStore from "@/stores/modules/app.ts";
import useSettingStore from "@/stores/modules/setting.ts";
import usePermissionStore from "@/stores/modules/permission.ts";

const useStore = () => ({
  user: useUserState(),
  app: useAppStore(),
  setting: useSettingStore(),
  permission: usePermissionStore()
});

export default useStore;
