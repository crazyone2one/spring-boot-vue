import type { RouteRecordRaw } from "vue-router";
import { createRouter, createWebHashHistory } from "vue-router";
import useAppStore from "../store/modules/app";

const routes: RouteRecordRaw[] = [
  {
    path: "/login",
    name: "login",
    component: () => import("/@/views/login/index.vue"),
    meta: {
      public: true, // 公共路由，无需认证
      title: "登录页",
    },
  },
  {
    path: "/",
    component: () => import("/@/layout/index.vue"),
    children: [
      {
        name: "dashboard",
        path: "/dashboard",
        component: () => import("/@/views/dashboard/index.vue"),
        meta: {
          title: "工作台",
          requiresAuth: true,
        },
      },
      {
        path: "/users",
        name: "users",
        component: () => import("/@/views/user/index.vue"),
        meta: {
          title: "User Management",
        },
      },
      {
        path: "/settings",
        name: "settings",
        component: () => import("/@/views/setting/index.vue"),
        meta: {
          title: "Settings",
        },
      },
    ],
  },
];
const router = createRouter({
  history: createWebHashHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 };
  },
});
// 路由守卫
router.beforeEach(async (to, _from, next) => {
  const appStore = useAppStore();
  // 判断是否是外链，如果是直接打开网页并拦截跳转
  if (to.meta.href) {
    window.open(to.meta.href as string);
    return false;
  }
  if (appStore.showProgress) {
    window.$loadingBar?.start();
  }
  // if (to.name === "login") next();
  next();
});
router.beforeResolve((to) => {
  console.log(to);

  // 设置菜单高亮
  // routeStore.setActiveMenu(to.meta.activeMenu ?? to.fullPath);
  // 添加tabs
  // tabStore.addTab(to)
  // 设置高亮标签;
  // tabStore.setCurrentTab(to.fullPath as string)
});
router.afterEach((to) => {
  const appStore = useAppStore();
  const title = import.meta.env.VITE_APP_TITLE || "xxx";
  // 修改网页标题
  document.title = `${to.meta.title} - ${title}`;
  // 结束 loadingBar
  if (appStore.showProgress) {
    window.$loadingBar?.finish();
  }
});
export default router;
