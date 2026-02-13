import "@/assets/style/index.scss";

import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "./App.vue";
import router from "./router";
import SvgIcon from "@/components/SvgIcon/index.vue";
import "virtual:svg-icons-register";
import "@/permission.ts";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.component("svg-icon", SvgIcon);
app.mount("#app");
