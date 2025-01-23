import '@/assets/styles/index.scss'
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import '@/permission'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import SvgIcon from '@/components/SvgIcon/index.vue'
import VMdEditor from '@kangc/v-md-editor'
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css'
import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index'
import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index'
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css'
import '@kangc/v-md-editor/lib/style/base-editor.css'
import '@kangc/v-md-editor/lib/theme/style/vuepress.css'
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js'
import 'virtual:svg-icons-register'
import Prism from 'prismjs'

const app = createApp(App)
VMdEditor.use(vuepressTheme, { Prism }).use(createEmojiPlugin()).use(createTodoListPlugin())
app.use(createPinia())
app.use(router)
app.use(VMdEditor)
app.component('svg-icon', SvgIcon)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.mount('#app')
