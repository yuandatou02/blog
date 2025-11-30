import {defineStore} from "pinia";
import defaultSettings from "@/setting.ts";
import type {SettingState} from "@/store/interface";

const useSettingStore = defineStore("useSettingStore", {
    state: (): SettingState => ({
        ...defaultSettings
    })
});


export default useSettingStore;