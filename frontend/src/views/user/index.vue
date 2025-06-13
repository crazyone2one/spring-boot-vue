<script setup lang="ts">
import {usePagination} from "alova/client";
import {fetchUserPage} from "/@/api/system/user.ts";
import type {ITableQueryParams, IUserItem} from "/@/api/types.ts";
import {type DataTableColumns, type DataTableRowKey, NButton, NSpace, NSwitch} from "naive-ui";
import {h, onMounted, ref} from "vue";
import {EditOutlined} from "@vicons/antd"
import PaginationComponent from "/@/components/PaginationComponent.vue";
import EditUser from "/@/views/user/EditUser.vue";
import TagComp from "/@/components/TagComp.vue";

const pattern = ref('')
const keyword = ref('')
const userId = ref('')
const show = ref(false)
const {data, pageSize, page, total, send} = usePagination((page, pageSize) => {
      const param: Partial<ITableQueryParams> = {page, pageSize}
      param.keyword = keyword.value
      return fetchUserPage(param)
    },
    {
      initialData: {total: 0, data: []},
      initialPage: 1, // 初始页码，默认为1
      initialPageSize: 10,// 初始每页数据条数，默认为10
      total: response => response.totalRow,
      data: response => response.records,
      immediate: false,
      watchingStates: [keyword],
      debounce: 300 // 防抖参数，单位为毫秒数，也可以设置为数组对watchingStates单独设置防抖时间
    },
)
const handleEdit = (row: IUserItem) => {
  userId.value = row.id
  show.value = true
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
      return h(NSwitch, {
        value: row.enabled, size: 'small',
        onUpdateValue: () => handleUpdateUserStatus(row)
      }, {})
    }
  },
  {
    title: 'role', key: 'roles',
    render(row) {
      return h(TagComp, {roles: row.roles}, {})
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
const handlePage = (param: number) => {
  page.value = param
}
const handlePageSize = (param: number) => {
  pageSize.value = param
}
onMounted(() => {
  send()
})
</script>

<template>
  <n-card>
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
    <pagination-component :page-size="pageSize" :page="page" :count="total" @update-page="handlePage"
                          @update-page-size="handlePageSize"/>
    <edit-user v-model:show="show" v-model:user-id="userId" @reload="send"/>
  </n-card>

</template>

<style scoped>
</style>