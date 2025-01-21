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
        :collapse-transition="false"
        :background-color="variables.menuBg" :text-color="variables.menuText"
        :active-text-color="variables.menuActiveText"
      >
        <!--        遍历-->
        <sidebar-item v-for="route in routes" :item="route" :key="route.path" :base-path="route.path" />
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script setup lang="ts">
import variables from '@/assets/styles/variables.module.scss'
import { computed } from 'vue'
import useStore from '@/stores'
import Logo from './Logo.vue'
import { useRoute } from 'vue-router'
import SidebarItem from '@/layouts/components/SideBar/SidebarItem.vue'

const route = useRoute()
const { app, setting, permission } = useStore()
const isCollapse = computed(() => app.isCollapse)
//是否显示logo
const showLogo = computed(() => setting.sidebarLogo)
//页面加载时默认激活菜单
const activeMenu = computed(() => route.path)
// 权限路由
const routes = computed(() => permission.routes)
</script>

<style scoped lang="scss">
</style>
