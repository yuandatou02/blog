import "@/assets/styles/index.scss";
import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import SvgIcon from "@/components/SvgIcon/index.vue";
import "virtual:svg-icons-register";
import {createPinia} from "pinia";

const app = createApp(App);
const pinia = createPinia();
app.use(router);
app.use(pinia);
app.component("svg-icon", SvgIcon);
app.mount("#app");
