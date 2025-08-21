import useUserStore from "@/stores/modules/user.ts";

const useStores = () => ({
    user: useUserStore(),
});

export default useStores;