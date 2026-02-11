<template>
    <div class="app-wrapper" :class="classObj">
      <div v-if="(device === 'mobile' && !app.isCollapse)" class="drawer-bg" @click.prevent="handleClickOutside" />
      <!-- 侧边栏 -->
      <side-bar class="sidebar-container"/>
    </div>
</template>

<script setup lang="ts">
import SideBar from "./components/SideBar/index.vue";
import useStore from "@/stores";
import { computed } from "vue";

const { app } = useStore();
const device = computed(() => app.device);
const classObj = computed(() => ({
    hideSidebar: app.isCollapse,
    openSidebar: !app.isCollapse,
    mobile: device.value === "mobile"
}));
const handleClickOutside = () => {
  app.changeCollapse(true);
}
</script>

<style lang="scss" scoped>
@use "@/assets/style/mixin.scss" as *;
@use "@/assets/style/variables.module.scss" as *;

.app-wrapper {
    @include clearfix;
    position: relative;
    height: 100%;
    width: 100%;

    &.mobile.openSidebar {
        position: fixed;
        top: 0;
    }
}

.drawer-bg {
    background: #000;
    opacity: 0.3;
    width: 100%;
    top: 0;
    height: 100%;
    position: absolute;
    z-index: 999;
}

.fixed-header {
    position: fixed;
    top: 0;
    right: 0;
    z-index: 40;
    width: calc(100% - #{$sideBarWidth});
    transition: width 0.28s;
}

.hideSidebar .fixed-header {
    width: calc(100% - 64px);
}

.sidebarHide .fixed-header {
    width: 100%;
}

.mobile .fixed-header {
    width: 100%;
}
</style>
