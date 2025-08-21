import '@/assets/index.scss'
import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import SvgIcon from '@/components/SvgIcon/index.vue'
import "virtual:svg-icons-register"

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.component('SvgIcon', SvgIcon)
app.mount('#app')
