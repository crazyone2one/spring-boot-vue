<template>
  <div class="app-container">
    <n-layout has-sider>
      <!-- 侧边栏导航 -->
      <n-layout-sider
          v-model:collapsed="collapsed"
          :width="240"
          :collapsed-width="64"
          bordered
          collapse-mode="width"
          @collapse="collapsed = true"
          @expand="collapsed = false"
      >
        <div class="logo" :class="{ collapsed: collapsed }">
          <img v-if="!collapsed" src="/@/assets/vue.svg" alt="Logo"/>
          <span v-if="!collapsed">Vue3 Admin</span>
          <span v-else>A</span>
        </div>
        <n-menu
            :options="menuOptions"
            :collapsed="collapsed"
            :collapsed-width="64"
            :collapsed-icon-size="22"
            @update:value="handleMenuSelect"
        />
      </n-layout-sider>

      <!-- 主内容区 -->
      <n-layout>
        <!-- 顶部导航 -->
        <n-layout-header style="height: 64px; padding: 20px" bordered>
          <div class="header-container">
            <n-button
                size="small"
                :style="{ marginRight: '16px' }"
                @click="collapsed = !collapsed"
            >
              <template #icon>
                <n-icon>
                  <MenuFoldOutlined v-if="collapsed"/>
                  <MenuUnfoldOutlined v-else/>
                </n-icon>
              </template>
            </n-button>

            <div class="header-right">
              <n-dropdown
                  trigger="click"
                  :options="userMenuItems"
                  @select="handleUserMenuSelect"
              >
                <div class="user-info">
                  <n-avatar size="small" :src="user.avatar"/>
                  <span v-if="!collapsed" class="user-name">
                    {{ user.name }}
                  </span>
                </div>
              </n-dropdown>
            </div>
          </div>
        </n-layout-header>

        <!-- 内容区域 -->
        <n-layout-content class="content-container">
          <router-view/>
        </n-layout-content>

        <!-- 页脚 -->
        <n-layout-footer bordered>
          <div class="footer-text">© 2025 Vue3 Admin Framework</div>
        </n-layout-footer>
      </n-layout>
    </n-layout>
  </div>
</template>

<script setup lang="ts">
import {DashboardOutlined, MenuFoldOutlined, MenuUnfoldOutlined, SettingOutlined, UserOutlined,} from "@vicons/antd";
import type {MenuOption} from "naive-ui";
import {
  NAvatar,
  NButton,
  NDropdown,
  NIcon,
  NLayout,
  NLayoutContent,
  NLayoutFooter,
  NLayoutHeader,
  NLayoutSider,
  NMenu,
  NText,
} from "naive-ui";
import {type Component, h, ref} from "vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "/@/store/modules/auth";

const authStore = useAuthStore()
const router = useRouter();
const collapsed = ref(false);
const renderIcon = (icon: Component) => {
  return () => h(NIcon, null, {default: () => h(icon)});
};
const menuOptions: MenuOption[] = [
  {
    label: "Dashboard",
    key: "dashboard",
    icon: renderIcon(DashboardOutlined),
  },
  {
    label: "Users",
    key: "users",
    icon: renderIcon(UserOutlined),
    children: [
      {
        label: "User List",
        key: "user-list",
      },
    ],
  },
  {
    label: "Settings",
    key: "settings",
    icon: renderIcon(SettingOutlined),
    children: [
      {
        label: "User Roles",
        key: "user-roles",
      },
    ]
  },
];
const renderCustomHeader = () => {
  return h(
      "div",
      {
        style: "display: flex; align-items: center; padding: 8px 12px;",
      },
      [
        h(NAvatar, {
          round: true,
          style: "margin-right: 12px;",
          src: "https://picsum.photos/200/200?random=1",
        }),
        h("div", null, [
          h("div", null, [h(NText, {depth: 2}, {default: () => "打工仔"})]),
          h("div", {style: "font-size: 12px;"}, [
            h(
                NText,
                {depth: 3},
                {default: () => "毫无疑问，你是办公室里最亮的星"}
            ),
          ]),
        ]),
      ]
  );
};
const handleMenuSelect = (key: string) => {
  router.push({name: key});
};
// 处理用户菜单选择
const handleUserMenuSelect = (key: string) => {
  if (key === "logout") {
    window.$dialog.info({
      title: '退出登录',
      content: '确认退出当前账号？',
      positiveText: '确认',
      negativeText: '取消',
      onPositiveClick: () => {
        authStore.logout()
      },
    })
  }
};
const user = ref({
  name: authStore.userInfo?.user.name ?? '--',
  avatar: "https://picsum.photos/200/200?random=1",
});

const userMenuItems = [
  {
    key: "header",
    type: "render",
    render: renderCustomHeader,
  },
  {
    key: "header-divider",
    type: "divider",
  },
  {
    label: "Profile",
    key: "profile",
  },
  {
    label: "Settings",
    key: "user-settings",
  },
  {
    label: "Logout",
    key: "logout",
  },
];
</script>

<style scoped>
.app-container {
  height: 100vh;
  overflow: hidden;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 64px;
  padding: 0 20px;
  border-bottom: 1px solid #e8e8e8;
}

.logo img {
  height: 32px;
  margin-right: 10px;
}

.logo span {
  font-size: 18px;
  font-weight: 600;
}

.header-container {
  display: flex;
  align-items: center;
  height: 100%;
  padding: 0 10px;
}

.header-right {
  margin-left: auto;
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.user-name {
  margin-left: 8px;
}

.content-container {
  padding: 20px;
  overflow-y: auto;
  height: calc(100vh - 128px);
}

.footer-text {
  text-align: center;
  font-size: 14px;
}
</style>
