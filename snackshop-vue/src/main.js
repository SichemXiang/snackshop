import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '././assets/styles/reset.css'
import '././assets/styles/border.css'
import locale from 'element-plus/lib/locale/lang/zh-cn'


createApp(App).use(store).use(router).use(ElementPlus).use(ElementPlus, { locale }).mount("#app");
