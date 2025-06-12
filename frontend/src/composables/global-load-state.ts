import {ref} from 'vue';

// 全局加载状态
const isLoading = ref(false);
const loadingCount = ref(0); // 计数器，支持多个并发请求
const loadingMessage = ref('加载中...');

// 显示加载
const showLoading = (message = '加载中...') => {
    loadingCount.value++;
    isLoading.value = true;
    loadingMessage.value = message;
};

// 隐藏加载
const hideLoading = () => {
    if (loadingCount.value > 0) {
        loadingCount.value--;
    }
    if (loadingCount.value === 0) {
        isLoading.value = false;
    }
};

// 重置加载状态
const resetLoading = () => {
    loadingCount.value = 0;
    isLoading.value = false;
};

export default {
    isLoading,
    loadingMessage,
    showLoading,
    hideLoading,
    resetLoading,
};