<template>
    <!-- 外链图标 -->
    <div v-if="isExternal" :style="styleExternalIcon" class="svg-external-icon svg-icon" v-on="listeners" />

    <!-- 内联 svg -->
    <svg v-else :class="svgClass" aria-hidden="true" v-on="listeners">
        <use :href="iconName" />
    </svg>
</template>

<script setup lang="ts">
import { isExternalFunc } from "@/utils/validate";
import { computed, useAttrs } from 'vue';
const props = defineProps({
    iconClass: { type: String, required: true },
    className: { type: String, default: '' }
});

const isExternal = computed(() => isExternalFunc(props.iconClass));
const iconName = computed(() => `#icon-${props.iconClass}`);
const svgClass = computed(() =>
    props.className ? `svg-icon ${props.className}` : 'svg-icon'
);
const styleExternalIcon = computed(() => ({
    mask: `url(${props.iconClass}) no-repeat 50% 50%`,
    '-webkit-mask': `url(${props.iconClass}) no-repeat 50% 50%`
}));
const listeners = useAttrs();
</script>

<style lang="scss" scoped>
.svg-icon {
    width: 1em;
    height: 1em;
    vertical-align: -0.15em;
    fill: currentColor;
    overflow: hidden;
}

.svg-external-icon {
    background-color: currentColor;
    mask-size: cover !important;
    display: inline-block;
}
</style>