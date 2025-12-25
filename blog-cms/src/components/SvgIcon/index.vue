<template>
  <svg aria-hidden="true" class="svg-icon"
       :style="'width:'+size+';height:'+size">
    <use :xlink:href="symbolId" :fill="color"></use>
  </svg>
</template>

<script setup lang="ts">
import {computed} from "vue";

/**
 * 图标组件的属性接口定义
 * 定义了图标的前缀、样式类、颜色和尺寸等可配置属性
 */
interface Props {
  /**
   * 图标前缀字符串，用于标识图标类型或分类
   */
  prefix?: string;
  /**
   * 图标的CSS样式类，用于自定义图标外观
   */
  iconClass?: string;
  /**
   * 图标的颜色值，支持颜色名称或十六进制颜色码
   */
  color?: string;
  /**
   * 图标的尺寸大小，如 small、medium、large 或具体数值
   */
  size?: string;
}


const props = withDefaults(defineProps<Props>(), {
  prefix: "icon",
  size: "1rem",
});
const symbolId = computed(() => `#${props.prefix}-${props.iconClass}`);
</script>

<style scoped lang="scss">
.svg-icon {
  vertical-align: -0.25em;
  overflow: hidden;
  fill: currentColor;
}
</style>