/// <reference types="vite/client" />

interface ViteTypeOptions {
  // 添加这行代码，你就可以将 ImportMetaEnv 的类型设为严格模式，
  // 这样就不允许有未知的键值了。
  strictImportMetaEnv: unknown;
}

interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string;
  readonly VITE_API_BASE_URL: string;
  // 更多环境变量...
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
declare module "*.vue" {
  import type {
    DialogProviderInst,
    MessageProviderInst,
    NotificationProviderInst,
  } from "naive-ui";
  import { DefineComponent } from "vue";
  const component: DefineComponent;
  global {
    interface Window {
      $message: MessageProviderInst;
      $dialog: DialogProviderInst;
      $notification: NotificationProviderInst;
      $loadingBar: import("naive-ui").LoadingBarApi;
    }
  }
  export default component;
}
declare namespace App {
  type lang = "zhCN" | "enUS";
}