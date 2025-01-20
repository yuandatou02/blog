//统一暴露仓库
import useUserStore from "@/stores/modules/user.ts";
import useAppStore from "@/stores/modules/app.ts";
import useSettingStore from "@/stores/modules/setting.ts";

const useStore = () => ({
  user: useUserStore(),
  app: useAppStore(),
  setting: useSettingStore(),
});

export default useStore;
