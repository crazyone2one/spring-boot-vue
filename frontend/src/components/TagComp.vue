<script setup lang="ts">
import {NTag} from 'naive-ui'
import type {IRole} from "/@/types/role.ts";

withDefaults(defineProps<{
  roles: Array<IRole>,
  size?: 'small' | 'medium' | 'large'
}>(), {
  size: 'small'
})
</script>

<template>
  <n-flex>
    <n-tag v-for="role in roles" :key="role.id" :bordered="false" type="info"
           :size="size">
      {{ role.name }}
    </n-tag>
  </n-flex>
</template>

<style scoped>

</style>