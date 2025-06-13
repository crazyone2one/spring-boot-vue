<script setup lang="ts">
import { useForm } from "alova/client";
import type { FormInst, FormRules } from "naive-ui";
import { ref } from "vue";
import { fetchSaveRole } from "/@/api/system/user-role";
import ModalComp from "/@/components/ModalComp.vue";

const emit = defineEmits(["reload"]);
const show = defineModel<boolean>("show", { default: false });
const formRef = ref<FormInst | null>(null);
const rules: FormRules = {
  name: {
    required: true,
    message: "请输入name",
    trigger: ["blur", "input"],
    max: 255,
  },
};
const { form, send, reset } = useForm((form) => fetchSaveRole(form), {
  initialForm: {
    id: "",
    name: "",
  },
  resetAfterSubmiting: true,
});
const handleCancel = () => {
  reset();
  show.value = false;
  formRef.value?.restoreValidation();
};
const handleSubmit = () => {
  formRef.value?.validate((err) => {
    if (!err) {
      send().then(() => {
        show.value = false;
        formRef.value?.restoreValidation();
        emit("reload");
      });
    }
  });
};
</script>
<template>
  <modal-comp
    v-model:show-modal="show"
    preset="dialog"
    title="title"
    @cancel-callback="handleCancel"
    @submit-callback="handleSubmit"
  >
    <template #default>
      <n-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-placement="left"
        label-width="auto"
        require-mark-placement="right-hanging"
      >
        <n-form-item label="name" path="name">
          <n-input v-model:value="form.name" placeholder="name" />
        </n-form-item>
      </n-form>
    </template>
  </modal-comp>
</template>

<style scoped></style>
