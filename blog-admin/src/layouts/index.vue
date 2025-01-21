<template>
  <div class="app-wrapper" :class="classObj">
    <div v-if="(device === 'mobile' && !app.isCollapse)" class="drawer-bg" @click="handleClickOutside" />
    <!--    侧边栏-->
    <side-bar class="sidebar-container"></side-bar>
    <!--    顶部导航栏-->
    <div :class="{ hasTagsView: needTagView }" class="main-container">
      <div :class="{ 'fixed-header': fixedHeader }">
        <!-- 导航栏 -->
        <NavBar @setLayout="setLayout"></NavBar>
        <!-- 历史标签栏 -->
      </div>
      <!--      主要内容区-->
      <AppMain></AppMain>
      <!-- 设置 -->
      <Settings ref="settingRef"></Settings>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref, watchEffect } from 'vue'
import useStore from '@/stores'
import Settings from '@/components/Settings/index.vue'
import NavBar from '@/layouts/components/NavBar/index.vue'
import SideBar from '@/layouts/components/SideBar/index.vue'
import AppMain from '@/layouts/components/AppMain/index.vue'
import { useWindowSize } from '@vueuse/core'

const device = computed(() => app.device)
const { app, setting } = useStore()
const classObj = computed(() => ({
  hideSidebar: app.isCollapse,
  openSidebar: !app.isCollapse,
  mobile: device.value === 'mobile'
}))
const { width } = useWindowSize()
const WIDTH = 992
const settingRef = ref()
const needTagView = computed(() => setting.tagView)
const fixedHeader = computed(() => setting.fixedHeader)
const handleClickOutside = () => {
  app.changeCollapse(true)
}
const setLayout = () => {
  settingRef.value.openSetting()
}
watchEffect(() => {
  if (width.value - 1 < WIDTH) {
    app.toggleDevice('mobile')
    app.changeCollapse(true)
  } else {
    app.toggleDevice('desktop')
  }
})
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
