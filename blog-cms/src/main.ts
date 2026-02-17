import "@/assets/style/index.scss";

import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import SvgIcon from "@/components/SvgIcon/index.vue";
import Pagination from "@/components/Pagination/index.vue";
import RightToolbar from "@/components/RightToolbar/index.vue";
import "virtual:svg-icons-register";
// 解决非被动事件监听警告，提升滚动性能
import "default-passive-events";
import "@/permission.ts";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.component("svg-icon", SvgIcon);
app.component("Pagination", Pagination);
app.component("RightToolbar", RightToolbar);
app.mount("#app");
