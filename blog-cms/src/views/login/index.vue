<template>
  <div class="login">
    <el-form class="login-form">
      <h3 class="login-title">博客后台管理系统</h3>
      <!--      账号-->
      <el-form-item>
        <el-input/>
      </el-form-item>
      <!--      密码-->
      <el-form-item>
        <el-input/>
      </el-form-item>
      <!--      登录按钮-->
      <el-form-item>
        <el-button/>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022 - {{ new Date().getFullYear() }} By Ikaros</span>
    </div>
  </div>
</template>

<script setup lang="ts">

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