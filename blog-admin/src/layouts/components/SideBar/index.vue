<template>
  <div :class="{'has-logo':showLogo}">
    <!-- 网站Logo -->
    <Logo v-if="showLogo" :collapse="isCollapse"></Logo>
    <!-- 侧边栏 -->
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :unique-opened="true"
        :collapse="isCollapse"
        :collapse-transition="false">
        <sidebar-item v-if="route in routes" :item="route" :key="route.path" :base-path="route.path"/>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import useStore from "@/stores";
import Logo from "./Logo.vue";
import {useRoute} from 'vue-router';
import SidebarItem from "@/layouts/components/SideBar/SidebarItem.vue";

const route = useRoute();
const {app, setting, permission} = useStore();
const isCollapse = computed(() => app.isCollapse);
//是否显示logo
const showLogo = computed(() => setting.sidebarLogo);
//页面加载时默认激活菜单
const activeMenu = computed(() => route.path);
// 权限路由
const routes = computed(() => permission.routes);
</script>

<style scoped lang="scss">
@use "@/assets/styles/variables.module";

.el-menu {
  // 菜单的背景颜色
  --bg-color: variables.$menuBg;
  // 文字颜色
  --text-color: variables.$menuText;
  // 活动菜单项的文本颜色
  --active-color: variables.$menuActiveText;
}
</style>
