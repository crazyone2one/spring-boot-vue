<script setup lang="ts">
import {
  NDialogProvider,
  NLoadingBarProvider,
  NMessageProvider,
  NNotificationProvider,
  useDialog,
  useLoadingBar,
  useMessage,
  useNotification,
} from "naive-ui";
import { defineComponent, h } from "vue";
// import router from "/@/router";
// const loadingBar = useLoadingBar();

// router.beforeEach(() => loadingBar?.start());

// router.afterEach(() => loadingBar?.finish());
const registerNaiveTools = () => {
  window.$loadingBar = useLoadingBar();
  window.$dialog = useDialog();
  window.$message = useMessage();
  window.$notification = useNotification();
};
const NaiveProviderContent = defineComponent({
  name: "NaiveProviderContent",
  setup() {
    registerNaiveTools();
  },
  render() {
    return h("div");
  },
});
</script>
<template>
  <n-loading-bar-provider>
    <n-dialog-provider>
      <n-notification-provider>
        <n-message-provider>
          <slot />
          <NaiveProviderContent />
        </n-message-provider>
      </n-notification-provider>
    </n-dialog-provider>
  </n-loading-bar-provider>
</template>

<style scoped></style>
