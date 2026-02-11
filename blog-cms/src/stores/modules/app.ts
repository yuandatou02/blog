import { defineStore } from "pinia";
import type { AppState } from "@/stores/interface";

const useAppStore = defineStore("useAppStore", {
    state: (): AppState => ({
        isCollapse: false,
        device: "desktop",
        size: "default"
    }),
    actions: {
        changeCollapse(isCollapse: boolean) {
            this.isCollapse = isCollapse;
        },
    }
});

export default useAppStore;
