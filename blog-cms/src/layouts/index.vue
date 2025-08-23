<template>
  <div class="app-wrapper" :class="classObj">
    <div v-if="(device === 'mobile' && !app.isCollapse)" class="drawer-bg" @click="handleClickOutside"/>
    <!-- 侧边栏 -->
    <SideBar class="sidebar-container"></SideBar>
    <!-- 顶部导航 -->
    <div :class="{ hasTagsView: needTagView }" class="main-container">
      <div :class="{ 'fixed-header': fixedHeader }">
        <!-- 导航栏 -->
        <NavBar @setLayout="setLayout"/>
        <!-- 历史标签栏 -->
        <TagView v-if="needTagView"/>
      </div>
      <!-- 内容区域 -->
      <AppMain/>
      <!-- 设置 -->
      <Settings ref="settingRef"/>
    </div>
  </div>
</template>

<script setup lang="ts">
import {computed, ref} from "vue";
import useStores from "@/stores";
import SideBar from "./components/SideBar/index.vue";
import NavBar from "./components/NavBar/index.vue";
import TagView from "@/components/TagView/index.vue";
import Settings from "@/components/Settings/index.vue";
import AppMain from "./components/AppMain/index.vue";

const settingRef = ref();
const device = computed(() => app.device);
const {app, setting} = useStores();

const classObj = computed(() => ({
  hideSidebar: app.isCollapse,
  openSidebar: !app.isCollapse,
  mobile: device.value === "mobile",
}));
const needTagView = computed(() => setting.tagView);
const fixedHeader = computed(() => setting.fixedHeader);
const handleClickOutside = () => {
  app.changeCollapse(true);
}

const setLayout = () => {
  settingRef.value.openSetting();
}
</script>

<style lang="scss" scoped>
@use "@/assets/mixin.scss" as *;
@use "@/assets/variables.module.scss" as *;

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