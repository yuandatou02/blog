<template>
  <div ref="editorRef" class="edit-container" v-html="content" contenteditable :placeholder="placeholder"
       @focus="onFocus" @blur="onBlur" @input="onInput">
  </div>
</template>

<script setup lang="ts">
import {type ComponentInternalInstance, getCurrentInstance, reactive, ref, toRefs, watch} from 'vue';

const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const emit = defineEmits(["update:text"]);
const editorRef = ref();
const props = defineProps({
  text: {
    type: String,
    default: ""
  },
  placeholder: {
    type: String,
    default: ""
  }
});
const data = reactive({
  content: props.text,
  focusIn: false,
  range: null as unknown as Range,
});
const {content, focusIn, range} = toRefs(data);
watch(
    () => props.text,
    (value) => {
      if (!focusIn.value) {
        content.value = value;
      }
    });
const onInput = (e: any) => {
  emit("update:text", e.target.innerHTML);
};
const onFocus = () => {
  focusIn.value = true;
};
const onBlur = () => {
  if (window.getSelection) {
    const selection = window.getSelection();
    if (selection && selection.rangeCount > 0) {
      range.value = selection.getRangeAt(0);
    } else {
      range.value = null as any;
    }
  }
  focusIn.value = false;
};
const clear = () => {
  content.value = "";
  proxy!.$el.innerHTML = "";
};
const addText = (emoji: string) => {
  if (typeof window === 'undefined' || !window.getSelection) return;

  const selection = window.getSelection();
  const editor = editorRef.value;
  if (!editor || !selection) return;

  editor.focus();

  let activeRange: Range | null = null;

  // 1. 优先用之前记住的 range
  if (range.value && editor.contains(range.value.commonAncestorContainer)) {
    activeRange = range.value;
  }
  // 2. 否则用当前选区
  else if (selection.rangeCount > 0) {
    activeRange = selection.getRangeAt(0);
  }
  // 3. 都没有就新建一个
  else {
    activeRange = document.createRange();
    activeRange.selectNodeContents(editor);
    activeRange.collapse(false);
    selection.removeAllRanges();
    selection.addRange(activeRange);
  }

  if (!activeRange) return;

  activeRange.deleteContents();
  activeRange.insertNode(activeRange.createContextualFragment(emoji));
  activeRange.collapse(false);

  selection.removeAllRanges();
  selection.addRange(activeRange);

  emit('update:text', editor.innerHTML);
};
defineExpose({addText, clear});
</script>

<style scoped>
.edit-container {
  position: relative;
  width: 100%;
  height: 100%;
  min-height: 150px;
  border-radius: 8px;
  background: var(--talkBg);
  font-size: 14px;
  line-height: 1.5;
  padding: 6px 12px;
  box-sizing: border-box;
  overflow: auto;
  word-break: break-all;
  outline: none;
  user-select: text;
  white-space: pre-wrap;
  text-align: left;
}

.edit-container:empty::before {
  cursor: text;
  content: attr(placeholder);
  color: #999;
}
</style>