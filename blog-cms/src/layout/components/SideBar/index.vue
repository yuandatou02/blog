<template>
  <div :class="{ 'has-logo': showLogo }">
    <!-- 网站Logo -->
    <logo v-if="showLogo" :collapse="isCollapse" />
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import useStore from "@/stores";
import Logo from "./logo.vue";
const {app,setting} = useStore();
const showLogo = computed(() => setting.sidebarLogo);
const isCollapse = computed(() => app.isCollapse);
</script>

<style scoped>

</style>