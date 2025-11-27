<template>
  <div class="login">
    <el-form class="login-form">
      <h3 class="title">博客后台管理系统</h3>
      <!--      账号-->
      <el-form-item>
        <el-input type="text" size="large" placeholder="账号"></el-input>
      </el-form-item>
      <!--      密码-->
      <el-form-item>
        <el-input type="password" show-password size="large" placeholder="密码"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="large" style="width: 100%;">
          <span>登录</span>
        </el-button>
      </el-form-item>
    </el-form>
    <!--    底部-->
    <div class="el-login-footer">
      <span>Copyright © 2025 - {{ new Date().getFullYear() }} By Ikaros</span>
    </div>
  </div>
</template>

<script lang="ts" setup></script>

<style lang="scss" scoped>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("@/assets/images/login-bg.png");
  background-size: cover;

  .login-form {
    border-radius: 6px;
    background: #ffffff;
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
  color: #ffffff;
  font-family: Arial, sans-serif;
  font-size: 12px;
  letter-spacing: 1px;
}

</style>
