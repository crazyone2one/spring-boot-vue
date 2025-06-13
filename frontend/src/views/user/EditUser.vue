<script setup lang="ts">
import ModalComp from "/@/components/ModalComp.vue";
import {ref, watchEffect} from "vue";
import type {FormInst, FormItemRule, FormRules, SelectOption} from "naive-ui";
import {validateEmail} from "/@/utils/validate.ts";
import {useForm, useRequest} from "alova/client";
import {fetchAddUser, fetchEditUser, fetchGetUserInfo} from "/@/api/system/user.ts";
import {fetchUserRoleList} from "/@/api/system/user-role.ts";

const emit = defineEmits(['reload'])
const show = defineModel('show', {default: false})
const userId = defineModel('userId', {default: ''})
const formRef = ref<FormInst | null>(null)
const roleOptions = ref<Array<SelectOption>>([])
const title = ref('add user')
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
const {form, send, reset, updateForm} = useForm(form => form.id ? fetchEditUser(form) : fetchAddUser(form),
    {
      initialForm: {
        id: '',
        username: '',
        nickname: '',
        email: '',
        roleIds: [] as string[]
      },
      resetAfterSubmiting: true
    })

const handleCancel = () => {
  reset()
  show.value = false
  formRef.value?.restoreValidation()
  userId.value = ''
  title.value = 'add user'
}

const handleSubmit = () => {
  formRef.value?.validate(err => {
    if (!err) {
      console.log(form.value)
      send().then(() => {
        window.$message.success(userId.value ? '编辑成功' : '用户添加成功')
        show.value = false
        formRef.value?.restoreValidation()
        emit(('reload'))
      })
    }
  })
}
const {send: loadUserRoles} = useRequest(fetchUserRoleList(), {immediate: false})
watchEffect(() => {
  if (show.value) {
    // 获取用户信息
    if (userId.value.length !== 0) {
      title.value = '编辑用户'
      useRequest(fetchGetUserInfo(userId.value)).onSuccess(({data}) => {
        // console.log(data)
        updateForm({
          id: data.id,
          username: data.username,
          nickname: data.nickname,
          email: data.email,
          roleIds: data.roles ? data.roles.map(role => role.id) : []
        })
      });
    }
    // 加载角色信息
    roleOptions.value = []
    loadUserRoles().then(res => {
      res.forEach(item=>roleOptions.value.push({...item}))
    })
  }
})
</script>

<template>
  <modal-comp v-model:show-modal="show" preset="dialog" :title="title"
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
        <n-form-item label="role">
          <n-select v-model:value="form.roleIds" multiple :options="roleOptions"
                    label-field="name" value-field="id"/>
        </n-form-item>
      </n-form>
    </template>
  </modal-comp>
</template>

<style scoped>

</style>