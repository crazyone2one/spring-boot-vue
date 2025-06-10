<script setup lang="ts">
import {useRouter} from "vue-router";
import {NResult, NSpace} from 'naive-ui'

defineProps<{
  /** 异常类型 403 404 500 */
  type: '403' | '404' | '500'
}>()
const router = useRouter()
const title = {
  '403': {'title': '403 禁止访问', 'desc': '总有些门是对你关闭的'},
  '404': {'title': '404 资源不存在', 'desc': '生活总归带点荒谬'},
  '500': {'title': '500 服务器错误', 'desc': '服务器出错可能说明该雇更多程序员了'},
}
</script>

<template>
  <n-space
      style="height: 100%"
      justify="center"
      align="center"
  >
    <n-result
        size="huge"
        :status="type"
        :title="title[type].title"
        :description="title[type].desc"
    >
      <template #footer>
        <n-button
            type="primary"
            @click="router.push('/')"
        >
          回到首页
        </n-button>
      </template>
    </n-result>
  </n-space>
</template>

<style scoped>

</style>