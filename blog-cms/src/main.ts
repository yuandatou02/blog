import '@/assets/index.scss'
import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
import SvgIcon from '@/components/SvgIcon/index.vue'
import "element-plus/dist/index.css"
import "virtual:svg-icons-register"
import "@/permission";

const app = createApp(App)
const pinia = createPinia()

app.use(pinia);
app.use(router);
app.component('SvgIcon', SvgIcon);
app.mount('#app');
