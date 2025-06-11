<script setup lang="ts">
import ModalComp from "/@/components/ModalComp.vue";
import {ref} from "vue";
import type {FormInst, FormItemRule, FormRules} from "naive-ui";
import {validateEmail} from "/@/utils/validate.ts";
import {useForm} from "alova/client";
import {fetchAddUser} from "/@/api/system/user.ts";

const emit = defineEmits(['reload'])
const show = defineModel('show', {default: false})
const formRef = ref<FormInst | null>(null)

const rules: FormRules = {
  username: {required: true, message: '请输入账号', trigger: ['blur', 'input'], max: 255},
  nickname: {required: true, message: '请输入账号', trigger: ['blur', 'input'], max: 255},
  email: {
    required: false,
    validator(_rule: FormItemRule, value: string) {
      if (value === '' || value === undefined) {
        return true
      } else if (!validateEmail(value)) {
        return new Error('请输入正确的邮箱');
      }
    },
    trigger: ['blur']
  }
}
const {form, send, reset} = useForm(form => fetchAddUser(form),
    {
      initialForm: {
        username: '',
        nickname: '',
        email: '',
      },
      resetAfterSubmiting: true
    })
const handleCancel = () => {
  reset()
  show.value = false
  formRef.value?.restoreValidation()
}

const handleSubmit = () => {
  formRef.value?.validate(err => {
    if (!err) {
      send().then(() => {
        window.$message.success('用户添加成功')
        show.value = false
        formRef.value?.restoreValidation()
        emit(('reload'))
      })
    }
  })
}
</script>

<template>
  <modal-comp v-model:show-modal="show" preset="dialog" title="添加用户"
              @cancel-callback="handleCancel" @submit-callback="handleSubmit">
    <template #default>
      <n-form
          ref="formRef"
          :model="form"
          :rules="rules"
          label-placement="left"
          label-width="auto"
          require-mark-placement="right-hanging"
      >
        <n-form-item label="username" path="username">
          <n-input v-model:value="form.username" placeholder="username"/>
        </n-form-item>
        <n-form-item label="nickname" path="nickname">
          <n-input v-model:value="form.nickname" placeholder="nickname"/>
        </n-form-item>
        <n-form-item label="email" path="email">
          <n-input v-model:value="form.email" placeholder="email"/>
        </n-form-item>
      </n-form>
    </template>
  </modal-comp>
</template>

<style scoped>

</style>