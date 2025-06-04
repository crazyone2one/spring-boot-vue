<script setup lang="ts">
import { NAlert, NSpin } from "naive-ui";
import { ref } from "vue";
// 使用类型注解
const description = ref("加载中...");
const show = ref(false);

const fetchData = async () => {
  description.value = "正在获取数据...";
  show.value = true;
  try {
    // 模拟API请求延迟
    await new Promise((resolve) => setTimeout(resolve, 1500));
  } catch (error) {
    console.error("加载失败:", error);
    window.$message.error("加载失败，请重试");
  } finally {
    show.value = false;
  }
};

const handleClick = () => {
  window.$message.success("按钮点击成功！");
};
</script>

<template>
  <div class="p-8">
    <h1 class="text-2xl font-bold mb-6">全局加载示例</h1>

    <div class="space-y-4">
      <n-button @click="fetchData"> 加载数据 </n-button>
      <n-button @click="handleClick"> 消息弹框 </n-button>
    </div>
    <n-spin :show="show">
      <n-alert title="啦啦啦" type="success">
        明天再打开行李箱。宝贝，挂电话啦。
      </n-alert>
      <template #description>
        {{ description }}
      </template>
    </n-spin>
  </div>
</template>

<style scoped></style>
