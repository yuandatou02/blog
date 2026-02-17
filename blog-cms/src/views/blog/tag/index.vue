<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <el-form @submit.native.prevent :inline="true" v-show="showSearch">
      <el-form-item label="标签名称">
        <el-input
          @keyup.enter="handleQuery"
          v-model="queryParams.keyword"
          style="width: 200px"
          placeholder="请输入标签名称"
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
        <el-button type="primary" plain :icon="Plus" @click="openModel(undefined)">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          :disabled="tagIdList.length === 0"
          :icon="Delete"
          @click="handleDelete(undefined)"
          >批量删除</el-button
        >
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <!-- 表格展示 -->
    <el-table border :data="tagList" @selection-change="handleSelectionChange" v-loading="loading">
      <!-- 表格列 -->
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <!-- 标签名 -->
      <el-table-column prop="tagName" width="300" label="标签名" align="center"></el-table-column>
      <!-- 文章量 -->
      <el-table-column prop="articleCount" label="文章量" align="center"></el-table-column>
      <!-- 创建时间 -->
      <el-table-column prop="createTime" width="300" label="创建时间" align="center">
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
      <el-table-column width="300" label="操作" align="center">
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
    <el-dialog :title="title" v-model="addOrUpdate" width="500px" append-to-body>
      <el-form ref="tagFormRef" label-width="100px" :model="tagForm" :rules="rules" @submit.native.prevent>
        <el-form-item label="标签名称" prop="tagName">
          <el-input placeholder="请输入标签名称" v-model="tagForm.tagName" style="width: 250px" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="addOrUpdate = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { addTag, deleteTag, getTagList, updateTag } from "@/api/tag";
import { Search, Plus, Delete, Edit } from "@element-plus/icons-vue";
import type { Tag as ApiTag, TagForm, TagQuery } from "@/api/tag/types";
import { formatDate } from "@/utils/data";
import { messageConfirm, notifySuccess } from "@/utils/modal";
import type { FormInstance, FormRules } from "element-plus";
import { onMounted, reactive, ref } from "vue";
import { Clock } from "@element-plus/icons-vue";

const tagFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  tagName: [{ required: true, message: "请输入标签名称", trigger: "blur" }]
});
const count = ref(0);
const showSearch = ref(true);
const loading = ref(false);
const title = ref("");
const addOrUpdate = ref(false);
const queryParams = reactive<TagQuery>({} as TagQuery);
const tagForm = reactive<TagForm>({} as TagForm);
const tagIdList = ref([] as number[]);
const tagList = ref([] as ApiTag[]);

const handleSelectionChange = (selection: ApiTag[]) => {
  tagIdList.value = selection.map((item) => item.id);
};
const openModel = (tag?: ApiTag) => {
  tagFormRef.value?.clearValidate();
  if (tag !== undefined) {
    title.value = "修改标签";
    let Form = {
      id: tag.id,
      tagName: tag.tagName
    };
    Object.assign(tagForm, Form);
  } else {
    title.value = "添加标签";
    let Form = {
      id: undefined,
      tagName: ""
    };
    Object.assign(tagForm, Form);
  }
  addOrUpdate.value = true;
};
const submitForm = () => {
  tagFormRef.value?.validate((valid) => {
    if (valid) {
      if (tagForm.id !== undefined) {
        updateTag(tagForm).then(({ data }) => {
          if (data.flag) {
            notifySuccess(data.message);
            getList();
          }
          addOrUpdate.value = false;
        });
      } else {
        addTag(tagForm).then(({ data }) => {
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
const handleDelete = (id?: number) => {
  let ids: number[] = [];
  if (id == undefined) {
    ids = tagIdList.value;
  } else {
    ids = [id];
  }
  messageConfirm("确认删除已选中的数据项?")
    .then(() => {
      deleteTag(ids).then(({ data }) => {
        if (data.flag) {
          notifySuccess(data.message);
          getList();
        }
      });
    })
    .catch(() => {});
};
const getList = () => {
  loading.value = true;
  getTagList(queryParams).then(({ data }) => {
    tagList.value = data.data.recordList as unknown as ApiTag[];
    count.value = data.data.count;
    loading.value = false;
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
