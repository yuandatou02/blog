<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse" />
    <!-- 侧边栏 -->
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu
        :default-active="activeMenu"
        :unique-opened="true"
        :collapse="isCollapse"
        :collapse-transition="false"
        :background-color="variables.menuBg"
        :text-color="variables.menuText"
        :active-text-color="variables.menuActiveText"
      >
        <sidebar-item v-for="route in routes" :item="route" :key="route.path" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import variables from "@/assets/style/variables.module.scss";
import { computed } from "vue";
import useStore from "@/stores";
import Logo from "./logo.vue";
import SidebarItem from "./SidebarItem.vue";
import { useRoute } from "vue-router";
const { app, setting, permission } = useStore();
const route = useRoute();
const showLogo = computed(() => setting.sidebarLogo);
const isCollapse = computed(() => app.isCollapse);
const routes = computed(() => permission.routes);
const activeMenu = computed(() => route.path);
</script>

<style scoped></style>
