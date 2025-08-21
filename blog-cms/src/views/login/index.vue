<template>
  <div class="login">
    <!--    登陆表单 -->
    <el-form class="login-form">
      <h3 class="title">博客后台管理系统</h3>
      <!--      用户名输入框 -->
      <el-form-item prop="username">
        <el-input type="text" placeholder="请输入用户名" size="large">
          <template #prefix>
            <svg-icon icon-class="user"/>
          </template>
        </el-input>
      </el-form-item>
      <!--        密码输入框 -->
      <el-form-item prop="password">
        <el-input type="password" placeholder="请输入密码" size="large" show-password>
          <template #prefix>
            <svg-icon icon-class="lock"/>
          </template>
        </el-input>
      </el-form-item>
      <!--      登录按钮 -->
      <el-form-item>
        <el-button type="primary" size="large" style="width: 100%;" @click="handleLogin">登录</el-button>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022 - {{ new Date().getFullYear() }} By Ikaros</span>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import SvgIcon from "@/components/SvgIcon/index.vue";

export default defineComponent({
  components: {SvgIcon}
})
</script>

<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("@/assets/images/login-bg.png");
  background-size: cover;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
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
