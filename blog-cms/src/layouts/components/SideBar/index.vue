<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse"/>
  </div>
</template>

<script setup lang="ts">
import useStore from "@/store";
import {computed} from "vue";
import Logo from "@/layouts/components/SideBar/logo.vue";

const {app, setting} = useStore();
const isCollapse = computed(() => app.isCollapse);
const showLogo = computed(() => setting.sidebarLogo);
</script>

<style scoped lang="scss">

</style>