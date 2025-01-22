<template>
  <div class="app-container">
    <el-tabs type="border-card" class="demo-tabs">
      <!-- 网站信息 -->
      <el-tab-pane>
        <template #label>
          <span class="custom-tabs-label">
            <el-icon>
              <Platform />
            </el-icon>
            <span>网站信息</span>
          </span>
        </template>
        <el-form label-width="80px" :model="siteConfig" label-position="left">
          <el-row>
            <el-col :md="6">
              <el-form-item label="用户头像">
                <el-upload class="avatar-uploader" :headers="authorization" action="/api/admin/site/upload"
                           :show-file-list="false" accept="image/*" :before-upload="beforeUpload"
                           :on-success="handleUserAvatarSuccess">
                  <img v-if="siteConfig.userAvatar" :src="siteConfig.userAvatar" class="avatar" alt="userAvatar" />
                  <el-icon v-else class="avatar-uploader-icon">
                    <Plus />
                  </el-icon>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { computed, reactive, ref, toRefs } from 'vue'
import { getToken, token_prefix } from '@/utils/token.ts'
import type { SiteConfig } from '@/api/site/types.ts'
import type { UploadRawFile } from 'element-plus'
//简单易用JS图像转换工具
import * as imageConversion from 'image-conversion'
import type { AxiosResponse } from 'axios'

const data = reactive({
  siteConfig: {} as SiteConfig
})
const {
  siteConfig
} = toRefs(data)
const authorization = computed(() => {
  return {
    Authorization: token_prefix + getToken()
  }
})
// 上传前的方法
const beforeUpload = (rawFile: UploadRawFile) => {
  return new Promise(resolve => {
    // 压缩到200KB,这里的200就是要压缩的大小,可自定义
    if (rawFile.size / 1024 < 200) {
      resolve(rawFile)
    }
    imageConversion
      .compressAccurately(rawFile, 200)
      .then(res => {
        resolve(res)
      })
  })
}
// 文件上传成功时的钩子
const handleUserAvatarSuccess = (response: AxiosResponse) => {
  siteConfig.value.userAvatar = response.data
}
</script>

<style scoped lang="scss">
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;

}

.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}

.avatar-uploader .avatar {
  width: 120px;
  height: 120px;
  object-fit: contain;
}

.article-cover {
  width: 300px;
}
</style>
