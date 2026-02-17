<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form @submit.native.prevent :model="queryParams" :inline="true" v-show="showSearch">
      <el-form-item label="友链名称">
        <el-input
          @keyup.enter="handleQuery"
          v-model="queryParams.keyword"
          style="width: 200px"
          placeholder="请输入友链名称"
          clearable
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="handleQuery">搜索</el-button>
      </el-form-item>
    </el-form>
    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb15">
      <el-col :span="1.5">
        <el-button type="primary" plain :icon="Plus" @click.prevent="openModel(undefined)">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          :disabled="friendIdList.length === 0"
          :icon="Delete"
          @click="handleDelete(undefined)"
          >批量删除</el-button
        >
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table border :data="friendList" @selection-change="handleSelectionChange" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <!-- 友链头像 -->
      <el-table-column prop="avatar" label="友链头像" align="center" width="100">
        <template #default="scope">
          <img :src="scope.row.avatar" width="40" height="40" alt="avatar" />
        </template>
      </el-table-column>
      <!-- 友链颜色 -->
      <el-table-column prop="color" label="友链颜色" align="center" width="100" />
      <!-- 友链名称 -->
      <el-table-column
        prop="name"
        label="友链名称"
        align="center"
        :show-overflow-tooltip="true"
        width="180"
      ></el-table-column>
      <!-- 友链地址 -->
      <el-table-column prop="url" label="友链地址" align="center" width="220"></el-table-column>
      <!-- 友链介绍 -->
      <el-table-column
        prop="introduction"
        label="友链介绍"
        align="center"
        :show-overflow-tooltip="true"
      ></el-table-column>
      <!-- 创建时间 -->
      <el-table-column prop="createTime" label="创建时间" align="center" width="140">
        <template #default="scope">
          <div class="create-time">
            <el-icon>
              <clock />
            </el-icon>
            <span style="margin-left: 10px">{{ formatDate(scope.row.createTime) }}</span>
          </div>
        </template>
      </el-table-column>
      <!-- 操作 -->
      <el-table-column label="操作" align="center" width="160">
        <template #default="scope">
          <el-button type="primary" :icon="Edit" link @click="openModel(scope.row)"> 编辑 </el-button>
          <el-button type="danger" :icon="Delete" link @click="handleDelete(scope.row.id)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination
      v-if="count > 0"
      :total="count"
      v-model:page="queryParams.current"
      v-model:limit="queryParams.size"
      @pagination="getList"
    />
    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="addOrUpdate" width="450px" append-to-body>
      <el-form ref="friendFormRef" label-width="100px" :model="friendForm" :rules="rules" @submit.native.prevent>
        <el-form-item label="友链名称" prop="name">
          <el-input placeholder="请输入友链名称" v-model="friendForm.name" style="width: 250px" />
        </el-form-item>
        <el-form-item label="友链颜色" prop="color">
          <el-color-picker v-model="friendForm.color"></el-color-picker>
        </el-form-item>
        <el-form-item label="友链头像" prop="avatar">
          <el-input placeholder="请输入友链头像" v-model="friendForm.avatar" style="width: 250px" />
        </el-form-item>
        <el-form-item label="友链地址" prop="url">
          <el-input placeholder="请输入友链地址" v-model="friendForm.url" style="width: 250px" />
        </el-form-item>
        <el-form-item label="友链介绍" prop="introduction">
          <el-input placeholder="请输入友链介绍" v-model="friendForm.introduction" style="width: 250px" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click.prevent="submitForm">确 定</el-button>
          <el-button @click="addOrUpdate = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import type { Friend, FriendForm, FriendQuery } from "@/api/friend/types";
import { Search, Plus, Delete, Edit } from "@element-plus/icons-vue";
import { addFriend, deleteFriend, getFriendList, updateFriend } from "@/api/friend";
import type { FormInstance, FormRules } from "element-plus";
import { Clock } from "@element-plus/icons-vue";
import { messageConfirm, notifySuccess } from "@/utils/modal.ts";
import { formatDate } from "@/utils/data.ts";

const queryParams = reactive<FriendQuery>({
  current: 1,
  size: 10
});
const friendList = ref<Friend[]>([]);
const friendFormRef = ref<FormInstance>();
const friendForm = reactive<FriendForm>({} as FriendForm);
const showSearch = ref(true);
const loading = ref(false);
const count = ref(0);
const title = ref("");
const addOrUpdate = ref(false);
const friendIdList = ref<number[]>([]);
const rules = reactive<FormRules>({
  name: [{ required: true, message: "请输入友链名称", trigger: "blur" }],
  color: [{ required: true }],
  avatar: [{ required: true, message: "请输入友链头像", trigger: "blur" }],
  url: [{ required: true, message: "请输入友链地址", trigger: "blur" }],
  introduction: [{ required: true, message: "请输入友链介绍", trigger: "blur" }]
});
const getList = () => {
  loading.value = true;
  getFriendList(queryParams).then(({ data }) => {
    friendList.value = data.data.recordList;
    count.value = data.data.count;
    loading.value = false;
  });
};
const openModel = (friend?: Friend) => {
  friendFormRef.value?.clearValidate();
  if (friend !== undefined) {
    Object.assign(friendForm, JSON.parse(JSON.stringify(friend)));
    title.value = "修改友链";
  } else {
    title.value = "添加友链";
    let newFriendForm = {
      id: undefined,
      color: "#409EFF",
      name: "",
      avatar: "",
      url: "",
      introduction: ""
    };
    Object.assign(friendForm, newFriendForm);
  }
  addOrUpdate.value = true;
};
const handleSelectionChange = (selection: Friend[]) => {
  friendIdList.value = selection.map((item) => item.id);
};
const handleDelete = (id?: number) => {
  let ids: number[] = [];
  if (id == undefined) {
    ids = friendIdList.value;
  } else {
    ids = [id];
  }
  messageConfirm("确认删除已选中的数据项?")
    .then(() => {
      deleteFriend(ids).then(({ data }) => {
        if (data.flag) {
          notifySuccess(data.message);
          getList();
        }
      });
    })
    .catch(() => {});
};
const submitForm = () => {
  friendFormRef.value?.validate((valid) => {
    if (valid) {
      if (friendForm.id !== undefined) {
        updateFriend(friendForm).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.message);
            getList();
          }
          addOrUpdate.value = false;
        });
      } else {
        addFriend(friendForm).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.message);
            getList();
          }
          addOrUpdate.value = false;
        });
      }
    }
  });
};

const handleQuery = () => {
  queryParams.current = 1;
  getList();
};
onMounted(() => {
  getList();
});
</script>

<style scoped lang="scss"></style>
