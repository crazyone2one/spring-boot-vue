<script setup lang="ts">
import {usePagination} from "alova/client";
import {fetchUserPage} from "/@/api/system/user.ts";
import type {IUserItem} from "/@/api/types.ts";
import {type DataTableColumns, type DataTableRowKey, NSpace, NButton, NSwitch} from "naive-ui";
import {ref, onMounted, h} from "vue";
import {EditOutlined} from "@vicons/antd"
import PaginationComponent from "/@/components/PaginationComponent.vue";
import EditUser from "/@/views/user/EditUser.vue";

const pattern = ref('')
const keyword = ref('')
const show = ref(false)
const {loading, data, pageSize, page, total, send} = usePagination((page, pageSize) => fetchUserPage({page, pageSize}),
    {
      initialData: {total: 0, data: []},
      initialPage: 1, // 初始页码，默认为1
      initialPageSize: 10,// 初始每页数据条数，默认为10
      total: response => response.totalRow,
      data: response => response.records,
      immediate: false
    },
)
const handleEdit = (row: IUserItem) => {
  window.$message.info(row.username)
}
const handleUpdateUserStatus = (row: IUserItem) => {
  console.log(row)
}
const checkedRowKeysRef = ref<DataTableRowKey[]>([])
const columns: DataTableColumns<IUserItem> = [
  {type: 'selection',},
  {title: 'username', key: 'username'},
  {title: 'nickname', key: 'nickname'},
  {title: 'email', key: 'email'},
  {
    title: 'status', key: 'enabled',
    render(row) {
      return h(NSwitch, {value: row.enabled, size: 'small',
        onUpdateValue: () => handleUpdateUserStatus(row)}, {})
    }
  },
  {
    title: 'Action',
    key: 'actions',
    fixed: 'right',
    width: '200',
    render(row) {
      return h(NSpace, {}, {
        default: () => {
          return h(NButton, {text: true, type: 'warning', onClick: () => handleEdit(row)},
              {default: () => 'edit', icon: () => h(EditOutlined)})
        }
      })
    }
  },
]
const rowKey = (row: IUserItem) => row.id
const handleCheck = (rowKeys: DataTableRowKey[]) => {
  checkedRowKeysRef.value = rowKeys
}
const handleCreateUser = () => {
  show.value = true
}
onMounted(() => {
  send()
})
</script>

<template>
  <div>
    <n-grid :x-gap="10">
      <n-grid-item :span="5">
        <n-card class="h-full"
                :content-style="{ padding: '5px' }"
                :header-style="{ padding: '5px' }"
                :segmented="true">
          <template #header>
            <div class="flex items-center">
              <n-input class="mr-2" v-model:value="pattern" placeholder="搜索" size="small"/>
              <!--              <n-switch size="small" v-model:value="expandAllFlag" />-->
            </div>
          </template>
        </n-card>
      </n-grid-item>
      <n-grid-item :span="19">
        <div class="flex flex-row items-center justify-between mb-2">
          <n-flex>
            <n-button size="small" type="info" @click="handleCreateUser">添加用户</n-button>
          </n-flex>
          <div class="flex flex-row gap-[8px]">
            <n-input v-model:value="keyword" placeholder="输入查询条件" clearable/>
          </div>
        </div>
        <n-data-table
            :columns="columns"
            :data="data"
            :row-key="rowKey"
            @update:checked-row-keys="handleCheck"
        />
        <pagination-component :page-size="pageSize" :page="page" :count="total"/>
      </n-grid-item>
    </n-grid>
    <edit-user v-model:show="show" @reload="send"/>
  </div>

</template>

<style scoped>
</style>