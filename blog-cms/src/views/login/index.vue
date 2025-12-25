<template>
  <div class="login">
    <el-form class="login-form" :model="loginForm" :rules="loginRules" ref="ruleFormRef">
      <h3 class="login-title">博客后台管理系统</h3>
      <!--      账号-->
      <el-form-item prop="username">
        <el-input type="text" size="large" placeholder="请输入账号" v-model="loginForm.username">
          <template #prefix>
            <svg-icon icon-class="user"/>
          </template>
        </el-input>
      </el-form-item>
      <!--      密码-->
      <el-form-item prop="password">
        <el-input type="password" show-password size="large" placeholder="请输入密码" v-model="loginForm.password"
                  @keyup.enter="handleLogin(ruleFormRef)">
          <template #prefix>
            <svg-icon icon-class="password"/>
          </template>
        </el-input>
      </el-form-item>
      <!--      登录按钮-->
      <el-form-item>
        <el-button :loading="loading" type="primary" style="width: 100%" @click.prevent="handleLogin(ruleFormRef)">
          <span v-if="!loading">登录</span>
          <span v-else>登录中...<</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022 - {{ new Date().getFullYear() }} By Ikaros</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import useStore from "@/store";
import type {LoginForm} from "@/api/login/types";
import type {FormInstance, FormRules} from "element-plus";
import router from "@/router";

const loading = ref(false);
const {user} = useStore();
const ruleFormRef = ref<FormInstance>();
const loginForm = reactive<LoginForm>({
  username: "test@qq.com",
  password: "123456",
});
const loginRules = reactive<FormRules>({
  username: [{required: true, message: "请输入用户名", trigger: "blur"}],
  password: [{required: true, message: "请输入密码", trigger: "blur"}, {
    min: 6,
    message: "密码不能少于6位",
    trigger: "blur",
  }],
});
// 登录方法
const handleLogin = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid) => {
    if (valid) {
      loading.value = true;
      user.Login(loginForm).then(() => {
        router.push("/");
        loading.value = false;
      }).catch(() => {
        loading.value = false;
      });
    }
  });
};
</script>

<style scoped lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("@/assets/images/login-bg.png");
  background-size: cover;

  .login-form {
    border-radius: 6px;
    background: #fff;
    width: 400px;
    padding: 25px 25px 5px 25px;

    .login-title {
      margin: 0 auto 30px auto;
      text-align: center;
      color: #707070;
    }
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial, serif;
  font-size: 12px;
  letter-spacing: 1px;
}
</style>