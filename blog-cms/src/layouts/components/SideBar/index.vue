<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse"/>
    <!-- 侧边栏 -->
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu :default-active="activeMenu" :unique-opened="true" :collapse="isCollapse" :collapse-transition="false"
               :background-color="variables.menuBg" :text-color="variables.menuText"
               :active-text-color="variables.menuActiveText"></el-menu>
      <sidebar-item v-for="route in routes" :item="route" :key="route.path" :base-path="route.path"/>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import variables from '@/assets/variables.module.scss';
import {computed} from "vue";
import useStores from "@/stores";
import Logo from "@/layouts/components/SideBar/logo.vue";
import {useRoute} from "vue-router";
import SidebarItem from "@/layouts/components/SideBar/SidebarItem.vue";

const {app, setting, permission} = useStores();
const route = useRoute();
const showLogo = computed(() => setting.sidebarLogo);
const isCollapse = computed(() => app.isCollapse);
const activeMenu = computed(() => route.path);
const routes = computed(() => permission.routes);
</script>

<style scoped>

</style>