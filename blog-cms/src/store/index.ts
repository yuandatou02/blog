import useUserStore from "@/store/modules/user.ts";
import useAppStore from "@/store/modules/app.ts";

const useStore = () => ({
    user: useUserStore(),
    app: useAppStore()
});

export default useStore;