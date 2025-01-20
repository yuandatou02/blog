//统一暴露仓库
import useUserStore from "@/stores/modules/user.ts";

const useStore = () => ({
  user: useUserStore()
});

export default useStore;
