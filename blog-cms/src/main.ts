// 引入样式重置
import "normalize.css/normalize.css";
import "@/assets/index.scss";
import { createApp } from "vue";
import { createPinia } from "pinia";
import SvgIcon from "@/components/svgIcon/index.vue";
import "virtual:svg-icons-register";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.component("svg-icon", SvgIcon);

app.mount("#app");
