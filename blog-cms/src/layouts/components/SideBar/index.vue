<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse"/>

  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import useStores from "@/stores";
import Logo from "@/layouts/components/SideBar/logo.vue";

const {app, setting} = useStores();

const showLogo = computed(() => setting.sidebarLogo);
const isCollapse = computed(() => app.isCollapse);
</script>

<style scoped>

</style>