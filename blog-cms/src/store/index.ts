import useUserStore from "@/store/modules/user.ts";
import useAppStore from "@/store/modules/app.ts";
import useSettingStore from "@/store/modules/setting.ts";
import usePermissionStore from "@/store/modules/permission.ts";

const useStore = () => ({
    user: useUserStore(),
    app: useAppStore(),
    setting: useSettingStore(),
    permission: usePermissionStore(),
});

export default useStore;