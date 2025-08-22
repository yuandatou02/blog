import useUserStore from "@/stores/modules/user.ts";
import useAppStore from "@/stores/modules/app.ts";

const useStores = () => ({
    user: useUserStore(),
    app: useAppStore(),
});

export default useStores;