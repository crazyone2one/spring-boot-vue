<script setup lang="ts">
import globalLoading from "/@/composables/global-load-state.ts";
import {computed} from "vue";

const isLoading = computed(() => globalLoading.isLoading.value);
const loadingMessage = computed(() => globalLoading.loadingMessage.value);
</script>
<template>
  <div class="global-loading" v-show="isLoading">
    <div class="loading-content">
      <div class="wave-loading">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
      </div>
      <p>{{ loadingMessage }}</p>
    </div>
  </div>
</template>

<style scoped>
.global-loading {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
}

.loading-content {

  align-items: center;
}

.wave-loading span {
  display: inline-block;
  width: 10px;
  height: 40px;
  margin: 0 2px;
  border-radius: 4px;
  animation: wave 1.2s ease-in-out infinite;
}

.wave-loading span:nth-child(2) {
  animation-delay: 0.1s;
}

.wave-loading span:nth-child(3) {
  animation-delay: 0.2s;
}

.wave-loading span:nth-child(4) {
  animation-delay: 0.3s;
}

.wave-loading span:nth-child(5) {
  animation-delay: 0.4s;
}

@keyframes wave {
  0%, 100% {
    transform: scaleY(1);
    background-color: #852ae4;
  }
  50% {
    transform: scaleY(0.4);
    background-color: #2980b9;
  }
}

p {
  margin: 0;
  color: #333;
  font-size: 14px;
}
</style>
