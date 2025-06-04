import {
  // create naive ui
  create,
  // component
  NButton,
  NForm,
  NFormItem,
  NH3,
  NIcon,
  NInput,
} from "naive-ui";
import type { App } from "vue";

const naive = create({
  components: [NButton, NForm, NFormItem, NH3, NIcon, NInput],
});

export const installNaive = (app: App) => {
  app.use(naive);
};
