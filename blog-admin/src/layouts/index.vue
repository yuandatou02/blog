<template>
  <div class="app-wrapper" :class="classObj">
    <div v-if="(device === 'mobile' && !app.isCollapse)" class="drawer-bg" @click="handleClickOutside"/>
    <!--    侧边栏-->
    <div :class="{ hasTagsView: needTagView }" class="main-container">
      <div :class="{ 'fixed-header': fixedHeader }">
        <!-- 导航栏 -->
        <NavBar @setLayout="setLayout"></NavBar>
        <!-- 历史标签栏 -->
      </div>
    </div>
  </div>
  <h1>你好</h1>
</template>

<script setup lang="ts">
import {computed, ref} from "vue";
import useStore from "@/stores";
import NavBar from "@/layouts/components/NavBar/index.vue";

const device = computed(() => app.device);
const {app, setting} = useStore();
const classObj = computed(() => ({
  hideSidebar: app.isCollapse,
  openSidebar: !app.isCollapse,
  mobile: device.value === "mobile",
}));
const settingRef = ref();
const needTagView = computed(() => setting.tagView);
const fixedHeader = computed(() => setting.fixedHeader);
const handleClickOutside = () => {
  app.changeCollapse(true);
}
const setLayout = () => {
  settingRef.value.openSetting();
}
</script>

<style scoped lang="scss">
@use "@/assets/styles/mixin";
@use "@/assets/styles/variables.module";

.app-wrapper {
  @include mixin.clearfix;
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
  width: calc(100% - #{variables.$sideBarWidth});
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
