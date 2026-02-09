import useUserState from "@/stores/modules/user.ts";

const useStore = () => ({
    user: useUserState()
});

export default useStore;
