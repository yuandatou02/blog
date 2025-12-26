import {defineStore} from "pinia";
import type {SettingState} from "@/store/interface";

const useSettingStore = defineStore("useSettingStore", {
    state: (): SettingState => ({
        tagView: true,
        fixedHeader: true,
        sidebarLogo: true,
    }),
});

export default useSettingStore;