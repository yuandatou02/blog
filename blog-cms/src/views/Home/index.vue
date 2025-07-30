<template>
    <div>
        <h1>博客首页（待开发）</h1>
        <el-button @click="user.logout()">退出</el-button>
    </div>
</template>

<script lang="ts" setup>
import { useUserStore } from '@/stores/user'
const user = useUserStore()
</script>