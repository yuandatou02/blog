<template>
  <div class="navbar">
    <!-- 折叠按钮 -->
    <hamburger class="hamburger-container" />
    <!-- 面包屑 -->
    <breadcrumb class="breadcrumb-container" />
    <!--  右部选择区  -->
    <div class="right-menu">
      <template v-if="device !== 'mobile'">
        <!-- 博客首页 -->
        <el-tooltip content="博客首页" effect="dark" placement="bottom">
          <div class="right-menu-item hover-effect">
            <svg-icon @click.prevent="openHome" icon-class="home" size="1.2rem" />
          </div>
        </el-tooltip>
        <!-- 修改密码 -->
        <el-tooltip content="修改密码" effect="dark" placement="bottom">
          <password class="right-menu-item hover-effect" />
        </el-tooltip>
        <!-- 全屏 -->
        <screen-full class="right-menu-item hover-effect" />
        <!-- 布局大小 -->
        <el-tooltip content="布局大小" effect="dark" placement="bottom">
          <size-select class="right-menu-item hover-effect" />
        </el-tooltip>
      </template>
      <el-dropdown @command="handleCommand" class="avatar-container right-menu-item hover-effect" trigger="click">
        <!-- 头像 -->
        <div class="avatar-wrapper">
          <img :src="user.avatar" class="user-avatar" alt="avatar" />
          <el-icon class="el-icon-caret-bottom">
            <svg-icon icon-class="caret-bottom" />
          </el-icon>
        </div>
        <!-- 选项 -->
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="setLayout">
              <span>布局设置</span>
            </el-dropdown-item>
            <el-dropdown-item divided command="logout">
              <span>退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup lang="ts">
import Hamburger from "@/components/Hamburger/index.vue";
import Breadcrumb from "@/components/Breadcrumb/index.vue";
import Password from "@/components/Password/index.vue";
import ScreenFull from "@/components/Screenfull/index.vue";
import SizeSelect from "@/components/SizeSelect/index.vue";
import { computed } from "vue";
import useStore from "@/stores";
import SvgIcon from "@/components/SvgIcon/index.vue";
import { messageConfirm } from "@/utils/modal.ts";

const { app, user } = useStore();
const device = computed(() => app.device);
const openHome = () => {
  window.open("https://www.ttkwsd.top");
};
const handleCommand = (command: string) => {
  switch (command) {
    case "setLayout":
      setLayout();
      break;
    case "logout":
      logout();
      break;
    default:
      break;
  }
};
const emits = defineEmits(["setLayout"]);
const logout = () => {
  messageConfirm("确定注销并退出系统吗？")
    .then(() => {
      user.LogOut().then(() => {
        location.href = "/login";
      });
    })
    .catch(() => {});
};
const setLayout = () => {
  emits("setLayout");
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  display: flex;
  background-color: var(--el-bg-color);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    margin-left: auto;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
