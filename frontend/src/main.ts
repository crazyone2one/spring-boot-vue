import { createApp } from "vue";
// import './style.css'

import "virtual:uno.css";
import App from "./App.vue";
import router from "./router";
import { installPinia } from "/@/store";
import { installNaive } from "/@/utils/naive";
// import { installDiscreteApi } from "./utils/naive";

const setupApp = async () => {
  const app = createApp(App);
  app.use(router);
  // 安装discreteApi插件
  // installDiscreteApi(app);
  await installPinia(app);
  await installNaive(app);
  app.mount("#app");
};
setupApp();
