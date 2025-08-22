import useUserStore from "@/stores/modules/user.ts";
import useAppStore from "@/stores/modules/app.ts";
import useSettingStore from "@/stores/modules/setting.ts";

const useStores = () => ({
    user: useUserStore(),
    app: useAppStore(),
    setting: useSettingStore(),
});

export default useStores;