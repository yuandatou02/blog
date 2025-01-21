<template>
  <div :class="{'has-logo':showLogo}">
    <!-- 网站Logo -->
    <Logo v-if="showLogo" :collapse="isCollapse"></Logo>
  </div>
</template>

<script setup lang="ts">
import {computed} from "vue";
import useStore from "@/stores";
import Logo from "./Logo.vue";

const {app, setting} = useStore();
const isCollapse = computed(() => app.isCollapse);
//是否显示logo
const showLogo = computed(() => setting.sidebarLogo);
</script>

<style scoped lang="scss">

</style>
