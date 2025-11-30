import useUserStore from "@/store/modules/user.ts";
import useAppStore from "@/store/modules/app.ts";
import useSettingStore from "@/store/modules/setting.ts";

const useStore = () => ({
    user: useUserStore(),
    app: useAppStore(),
    setting: useSettingStore()
});

export default useStore;