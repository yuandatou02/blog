<template>
  <div class="login-container">
    <el-form class="login-form" ref="loginFormRef" :rules="loginRules" :model="loginForm">
      <!-- 标题 -->
      <div class="title-container">
        <h1 class="title">后台管理系统</h1>
      </div>
      <!-- 账号 -->
      <el-form-item prop="username">
        <el-input placeholder="请输入用户名" type="text" size="large" v-model="loginForm.username">
          <template #prefix>
                        <span class="svg-container">
                            <svg-icon icon-class="user"/>
                        </span>
          </template>
        </el-input>
      </el-form-item>
      <!-- 密码 -->
      <el-form-item prop="password">
        <el-input placeholder="请输入密码" type="password" size="large" show-password v-model="loginForm.password">
          <template #prefix>
                        <span class="svg-container">
                            <svg-icon icon-class="password"/>
                        </span>
          </template>
        </el-input>
      </el-form-item>
      <!-- 登录按钮 -->
      <el-form-item>
        <el-button :loading="lading" type="primary" class="login-btn" size="large"
                   @click.prevent="handleLogin(loginFormRef)">
          <span v-if="!lading">登陆</span>
          <span v-else>登陆中...</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import {reactive, ref} from "vue";
import type {FormInstance, FormRules} from "element-plus";
import type {LoginForm} from "@/api/login/types";
import {login} from "@/api/login";
import router from "@/router";

const lading = ref(false);
// 表单规则
const loginRules = reactive<FormRules>({
  username: [
    {required: true, message: "请输入用户名", trigger: "blur"},
    {min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur"}
  ],
  password: [
    {required: true, message: "请输入密码", trigger: "blur"},
    {min: 6, max: 16, message: "长度在 6 到 16 个字符", trigger: "blur"}
  ]
});
const loginFormRef = ref<FormInstance>();
const loginForm = reactive<LoginForm>({
  username: "",
  password: ""
});
// 登录
const handleLogin = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      lading.value = true;
      login(loginForm).then(res => {
        localStorage.setItem("token", res.data.data);
        // localStorage.setItem("user", JSON.stringify(res.data.user));
        router.push("/");
      });
      lading.value = false;
    }
  });
};
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100%;
  width: 100%;
  background-color: #2d3a4b;
  overflow: hidden;

  .login-form {
    position: relative;
    width: 520px;
    max-width: 100%;
    padding: 160px 35px 0;
    margin: 0 auto;
    overflow: hidden;

    .title-container {
      position: relative;

      .title {
        color: #eee;
        margin: 0 auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }

    .login-btn {
      width: 100%;
      margin-bottom: 30px;
    }

    .svg-container {
      color: #889aa4;
      vertical-align: middle;
      display: inline-block;
    }
  }
}
</style>