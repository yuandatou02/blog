<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse"/>
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
import {computed} from "vue";
import Logo from "@/layouts/components/SideBar/logo.vue";
import SidebarItem from "@/layouts/components/SideBar/SidebarItem.vue";
import variables from "@/assets/variables.module.scss";
import {useRoute} from "vue-router";

const route = useRoute();
const {app, setting, permission} = useStore();
const isCollapse = computed(() => app.isCollapse);
const showLogo = computed(() => setting.sidebarLogo);
const routes = computed(() => permission.routes);
const activeMenu = computed(() => route.path);
</script>

<style scoped lang="scss">

</style>