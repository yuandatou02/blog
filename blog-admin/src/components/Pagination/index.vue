<template>
  <el-pagination class="pagination-container" :background="background"
                 v-model:current-page="currentPage" v-model:page-size="pageSize" :layout="layout"
                 :page-sizes="pageSizes"
                 :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
</template>

<script setup lang="ts">
import { scrollTo } from '@/utils/scroll-to'
import { computed, PropType } from 'vue'

const props = defineProps({
  total: {
    required: true,
    type: Number as PropType<number>,
    default: 0
  },
  page: {
    type: Number,
    default: 1
  },
  limit: {
    type: Number,
    default: 10
  },
  pageSizes: {
    type: Array as PropType<number[]>,
    default() {
      return [10, 20]
    }
  },
  layout: {
    type: String,
    default: 'total, sizes, prev, pager, next, jumper'
  },
  background: {
    type: Boolean,
    default: true
  },
  autoScroll: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['update:page', 'update:limit', 'pagination'])

const currentPage = computed<number | undefined>({
  get: () => props.page,
  set: value => {
    emit('update:page', value)
  }
})

const pageSize = computed<number | undefined>({
  get() {
    return props.limit
  },
  set(val) {
    emit('update:limit', val)
  }
})

function handleSizeChange(val: number) {
  emit('pagination', { page: currentPage, limit: val })
  if (props.autoScroll) {
    scrollTo(0, 800)
  }
}

function handleCurrentChange(val: number) {
  currentPage.value = val
  emit('pagination', { page: val, limit: props.limit })
  if (props.autoScroll) {
    scrollTo(0, 800)
  }
}
</script>

<style scoped>
</style>
