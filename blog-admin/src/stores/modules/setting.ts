import {defineStore} from "pinia";
import type {SettingState} from "@/stores/interface";
import defaultSettings from "@/setting";

const {tagView, fixedHeader, sidebarLogo} = defaultSettings;

const useSettingStore = defineStore("useSettingStore", {
  state: (): SettingState => ({
    tagView: tagView,
    fixedHeader: fixedHeader,
    sidebarLogo: sidebarLogo,
  }),
});

export default useSettingStore;
