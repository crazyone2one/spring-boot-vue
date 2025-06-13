<script setup lang="ts">
import { PlusCircleOutlined } from "@vicons/antd";
import { useRequest } from "alova/client";
import { NButton, type TreeOption } from "naive-ui";
import { h, onMounted, ref } from "vue";
import { fetchUserRoleList } from "/@/api/system/user-role.ts";

const options = ref<Array<TreeOption>>([]);
const pattern = ref("");
const emit = defineEmits(["selectRole", "addRole"]);
const nodeProps = ({ option }: { option: TreeOption }) => {
  return {
    onClick() {
      emit("selectRole", option);
    },
  };
};
const renderPrefix = ({ option }: { option: TreeOption }) => {
  if (option.internal) {
    return h(
      NButton,
      { text: true, type: "primary" },
      { default: () => "internal" }
    );
  }
};
const { loading, send } = useRequest(() => fetchUserRoleList(), {
  immediate: false,
  force: true,
});
const handleLoadRoles = () => {
  send().then((res) => {
    options.value = [];
    res.forEach((item) =>
      options.value.push({ key: item.id, label: item.name, ...item })
    );
  });
};
defineExpose({ handleLoadRoles });
onMounted(() => {
  handleLoadRoles();
});
</script>

<template>
  <n-space vertical class="m-2">
    <div class="flex flex-row items-center justify-between mb-2">
      <div class="gap-[8px]">
        <n-input v-model:value="pattern" placeholder="搜索" clearable />
      </div>
      <n-button strong secondary circle type="info" @click="emit('addRole')">
        <template #icon>
          <n-icon>
            <PlusCircleOutlined />
          </n-icon>
        </template>
      </n-button>
    </div>
    <n-spin :show="loading">
      <n-tree
        block-line
        :data="options"
        :pattern="pattern"
        :selectable="false"
        :node-props="nodeProps"
        :render-prefix="renderPrefix"
        :show-irrelevant-nodes="false"
      />
    </n-spin>
  </n-space>
</template>

<style scoped></style>
