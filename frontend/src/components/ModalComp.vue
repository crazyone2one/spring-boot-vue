<script setup lang="ts">
import {NModal} from "naive-ui";

interface IProps {
  preset: 'dialog' | 'card',
  title: string
}

const props = withDefaults(defineProps<IProps>(), {
  preset: 'dialog',
  title: ''
})
const showModal = defineModel('showModal', {default: false})
const emit = defineEmits(['cancelCallback', 'submitCallback'])
</script>

<template>
  <n-modal v-model:show="showModal" :preset="preset" :title="props.title">
    <template #header>
      <div>
        {{ props.title }}
        <slot name="header"></slot>
      </div>
    </template>
    <div>
      <slot></slot>
    </div>
    <template #action>
      <div>
        <slot name="action"></slot>
        <n-flex>
          <n-button size="small" @click="emit('cancelCallback')">cancel</n-button>
          <n-button type="primary" size="small" @click="emit('submitCallback')">submit</n-button>
        </n-flex>
      </div>
    </template>
  </n-modal>
</template>

<style scoped>

</style>