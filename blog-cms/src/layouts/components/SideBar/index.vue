<template>
  <div :class="{'has-logo': setting.sidebarLogo}">
    <!--网站logo-->
    <logo v-if="setting.sidebarLogo" :collapse="app.isCollapse"/>
    <!--侧边栏-->
    <el-scrollbar wrap-class="scrollbar-wrapper">
      <el-menu :default-active="activeMenu" :unique-opened="true" :collapse="app.isCollapse"
               :collapse-transition="false" :background-color="variables.menuBg" :text-color="variables.menuText"
               :active-text-color="variables.menuActiveText"
      >

      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import variables from "@/assets/styles/variables.module.scss";
import useStore from "@/store";
import {useRoute} from "vue-router";
import Logo from "@/layouts/components/SideBar/logo.vue";
import {computed} from "vue";

const route = useRoute();
const {app, setting} = useStore();
const activeMenu = computed(() => route.path);
</script>