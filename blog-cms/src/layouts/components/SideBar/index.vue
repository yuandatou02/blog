<template>
  <div :class="{'has-logo': setting.sidebarLogo}">
    <!--网站logo-->
    <logo v-if="setting.sidebarLogo" :collapse="app.isCollapse"/>
  </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
import Logo from "@/layouts/components/SideBar/logo.vue";

const {app, setting} = useStore();
</script>