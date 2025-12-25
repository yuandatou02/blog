import useUserStore from "@/store/modules/user.ts";

const useStore = () => ({
    user: useUserStore(),
});

export default useStore;