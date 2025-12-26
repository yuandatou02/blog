<template>
  <div class="app-wrapper" :class="classObj">
    <div v-if="(app.device === 'mobile' && !app.isCollapse)" class="drawer-bg" @click="handleClickOutside"/>
    <!-- 侧边栏 -->
    <side-bar class="sidebar-container"/>
    <!-- 侧边栏 -->
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu :default-active="activeMenu" :unique-opened="true" :collapse="isCollapse" :collapse-transition="false"
               :background-color="variables.menuBg" :text-color="variables.menuText"
               :active-text-color="variables.menuActiveText">
        <sidebar-item v-for="route in routes" :item="route" :key="route.path" :base-path="route.path"/>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
import SideBar from "./components/SideBar/index.vue";
import variables from "@/assets/variables.module.scss";
import {useRoute} from "vue-router";
import {computed} from "vue";

const {app, permission} = useStore();
const route = useRoute();
const isCollapse = computed(() => app.isCollapse);
const routes = computed(() => permission.routes);
const classObj = computed(() => ({
  hideSidebar: app.isCollapse,
  openSidebar: !app.isCollapse,
  mobile: app.device === "mobile",
}));
const activeMenu = computed(() => route.path);
const handleClickOutside = () => {
  app.changeCollapse(true);
};
</script>

<style scoped lang="scss">
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