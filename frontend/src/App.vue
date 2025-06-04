<script setup lang="ts">
import { useEventListener, useWindowSize } from "@vueuse/core";
import { enUS, NConfigProvider, NGlobalStyle } from "naive-ui";
import { ref } from "vue";
import ProviderView from "./components/ProviderView.vue";
import useAppStore from "/@/store/modules/app";
const appStore = useAppStore();

const locale = ref(enUS);
/** 屏幕大小改变时重新赋值innerHeight */
useEventListener(window, "resize", () => {
  const { height } = useWindowSize();
  appStore.innerHeight = height.value;
});
</script>

<template>
  <n-config-provider class="wh-full" inline-theme-disabled :locale="locale">
    <n-global-style />
    <provider-view>
      <router-view />
    </provider-view>
  </n-config-provider>
</template>

<style scoped></style>
