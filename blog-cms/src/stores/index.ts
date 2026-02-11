import useUserState from "@/stores/modules/user.ts";
import useAppStore from "@/stores/modules/app.ts";
import useSettingStore from "@/stores/modules/setting.ts";

const useStore = () => ({
    user: useUserState(),
    app: useAppStore(),
    setting:useSettingStore()
});

export default useStore;
