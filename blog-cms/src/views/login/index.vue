<template>
    <div class="login-container">
        <el-form class="login-form">
            <!-- 标题 -->
            <div class="title-container">
                <h1 class="title">后台管理系统</h1>
            </div>
            <!-- 账号 -->
            <el-form-item>
                <el-input placeholder="请输入用户名" type="text" size="large">
                    <template #prefix>
                        <span class="svg-container">
                            <svg-icon icon-class="user" />
                        </span>
                    </template>
                </el-input>
            </el-form-item>
            <!-- 密码 -->
            <el-form-item>
                <el-input placeholder="请输入密码" type="password" size="large">
                    <template #prefix>
                        <span class="svg-container">
                            <svg-icon icon-class="password" />
                        </span>
                    </template>
                </el-input>
            </el-form-item>
            <!-- 登录按钮 -->
            <el-form-item>
                <el-button type="primary" class="login-btn" size="large"></el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script lang="ts" setup></script>

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