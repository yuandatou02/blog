<template>
    <div class="login-box">
        <h2>登录 / 注册</h2>
        <el-form ref="formRef" :model="form" label-width="80">
            <el-form-item label="邮箱">
                <el-input v-model="form.email" />
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.password" type="password" show-password />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submit(true)">登录</el-button>
                <el-button @click="submit(false)">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts" setup>
import { reactive } from 'vue'
import { useUserStore } from '@/stores/user'

const form = reactive({ email: '', password: '' })
const user = useUserStore()

const submit = async (isLogin: boolean) => {
    if (isLogin) await user.login(form.email, form.password)
    else await user.register(form.email, form.password)
}
</script>

<style scoped>
.login-box {
    width: 400px;
    margin: 100px auto;
    padding: 40px;
    border: 1px solid #eee;
    border-radius: 8px;
}
</style>