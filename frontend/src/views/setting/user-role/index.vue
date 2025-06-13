<script setup lang="ts">
import { ref, useTemplateRef } from "vue";
import EditRole from "./components/EditRole.vue";
import type { IRole } from "/@/types/role.ts";
import RoleLeft from "/@/views/setting/user-role/components/RoleLeft.vue";
import RoleRight from "/@/views/setting/user-role/components/RoleRight.vue";

const role = ref({});
const editRoleRef =
  useTemplateRef<InstanceType<typeof EditRole>>("editRoleRef");
const roleLeftRef =
  useTemplateRef<InstanceType<typeof RoleLeft>>("roleLeftRef");
const showAdd = ref(false);
const handleSelectRole = (param: IRole) => {
  role.value = param;
};
const handleOpenEditModal = () => {
  showAdd.value = true;
};
const handleReloadRole = () => {
  roleLeftRef.value?.handleLoadRoles();
};
</script>

<template>
  <n-card>
    <n-split :size="0.2">
      <template #1>
        <role-left
          ref="roleLeftRef"
          @select-role="handleSelectRole"
          @add-role="handleOpenEditModal"
        />
      </template>
      <template #2>
        <role-right v-model:role="role" />
      </template>
    </n-split>
  </n-card>
  <edit-role
    ref="editRoleRef"
    v-model:show="showAdd"
    @reload="handleReloadRole"
  />
</template>

<style scoped></style>
