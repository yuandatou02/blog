<template>
  <div class="login">
    <el-form class="login-form" :model="loginForm" :rules="rules" ref="ruleFormRef">
      <h3 class="title">博客后台管理系统</h3>
      <!--      用户名-->
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" size="large" placeholder="账号">
          <template #prefix>
            <el-icon :size="20">
              <User/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <!--      密码-->
      <el-form-item prop="password">
        <el-input type="password" v-model="loginForm.password" show-password size="large" placeholder="密码"
                  @keyup.enter="handleLogin(ruleFormRef)">
          <template #prefix>
            <el-icon :size="20">
              <Lock/>
            </el-icon>
          </template>
        </el-input>
      </el-form-item>
      <!--      登录按钮-->
      <el-form-item>
        <el-button :loading="loading" type="primary" @click.prevent="handleLogin(ruleFormRef)"
                   style="width:100%;">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录中...</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--底部-->
    <div class="el-login-footer">
      <span>Copyright © 2022 - {{ new Date().getFullYear() }} By 阿冬</span>
    </div>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from "vue";
import type {LoginForm} from "@/api/login/types.ts";
import type {FormInstance, FormRules} from "element-plus";
import useStore from "@/stores";
import router from "@/router";

const {user} = useStore();
const ruleFormRef = ref<FormInstance>();
//登录表单
const loginForm = reactive<LoginForm>({
  username: 'test@qq.com',
  password: '123456'
});
//表单验证规则
const rules = reactive<FormRules>({
  username: [{required: true, message: "请输入用户名", trigger: "blur"}],
  password: [{required: true, message: "请输入密码", trigger: "blur"}, {
    min: 6,
    message: "密码不能少于6位",
    trigger: "blur"
  }],
});
//按钮加载
const loading = ref(false);
//登录方法
const handleLogin = async (formEl: FormInstance | undefined) => {
  // 如果表单内容为空则返回
  if (!formEl) return;
  // 不为空则请求登录
  await formEl.validate((valid) => {
    if (valid) {
      // 将登录按钮设置为正在登录中
      loading.value = true;
      // 发送登录请求
      user.LogIn(loginForm).then(() => {
        // 请求成功进行路由跳转
        router.push('/');
      }).finally(() => {
        loading.value = false;
      });
    }
  });
}
</script>

<style scoped lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("@/assets/images/bg.png");
  background-size: cover;

  .login-form {
    border-radius: 6px;
    background: #ffffff;
    opacity: 0.7;
    width: 400px;
    padding: 25px 25px 5px 25px;

    .title {
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
