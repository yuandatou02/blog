<template>
  <div class="navbar">
    <!-- 折叠按钮 -->
    <hamburger class="hamburger-container"></hamburger>
    <!-- 面包屑 -->
    <breadcrumb class="breadcrumb-container"></breadcrumb>
    <!--    右侧菜单-->
    <div class="right-menu">
      <!--      如果设备不等于移动端则显示右侧菜单-->
      <template v-if="device !=='mobile'">
        <!--        右侧下拉菜单-->
        <el-dropdown @command="handleCommand" class="avatar-container right-menu-item hover-effect" trigger="click">
          <!-- 头像 -->
          <div class="avatar-wrapper">
            <img :src="user.avatar" class="user-avatar" alt="avatar"/>
            <el-icon class="el-icon-caret-bottom">
              <caret-bottom/>
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
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import Hamburger from "@/components/Hamburger/index.vue";
import Breadcrumb from "@/components/Breadcrumb/index.vue";
import useStore from "@/stores";
import {computed} from "vue";
import {messageConfirm} from "@/utils/model.ts";

const {app, user} = useStore();
// 获取设备类型
const device = computed(() => app.device);
// 右侧菜单回调函数
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
// 退出登录函数
const logout = () => {
  messageConfirm("确定注销并退出系统吗？").then(() => {
    user.LogOut().then(() => {
      location.href = "/login";
    });
  });
};
const emits = defineEmits(['setLayout']);
const setLayout = () => {
  emits('setLayout');
};
</script>

<style scoped lang="scss">
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
