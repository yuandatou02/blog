<template>
  <svg aria-hidden="true" class="svg-icon" :style="'width:'+size+';height:'+size">
    <use :xlink:href="symbolId" :fill="color"/>
  </svg>
</template>

<script setup lang="ts">
import {computed} from "vue";

interface IconProps {
  prefix?: string;
  iconClass: string;
  color?: string;
  size?: string;
}

const props = withDefaults(defineProps<IconProps>(), {
  prefix: "icon",
  size: "1rem"
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