<template>
  <div :class="classObj" class="app-wrapper">
    <div v-if="(app.device ==='mobile' && !app.isCollapse)" class="drawer-bg" @click="handleClickOutside"/>
    <!--侧边栏-->
    <side-bar/>
  </div>
</template>

<script setup lang="ts">
import SideBar from "@/layouts/components/SideBar/index.vue";
import useStore from "@/store";
import {computed} from "vue";

const {app} = useStore();
const classObj = computed(() => ({
  hideSidebar: app.isCollapse,
  openSidebar: !app.isCollapse,
  mobile: app.device === "mobile",
}));

const handleClickOutside = () => {
  app.changeCollapse(true);
};
</script>

<style scoped lang="scss">
@use "@/assets/styles/mixin.scss" as *;

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
</style>