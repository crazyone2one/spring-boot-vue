import { useWindowSize } from "@vueuse/core";
import { defineStore } from "pinia";
import type { IAppState } from "./types";

const useAppStore = defineStore("app-store", {
  state: (): IAppState => {
    const { height } = useWindowSize();
    return {
      innerHeight: height.value,
      showProgress: true,
    };
  },
});
export default useAppStore;
