import type {RouteRecordRaw} from "vue-router";
import {createRouter, createWebHashHistory} from "vue-router";
import useAppStore from "../store/modules/app";
import {useAuthStore} from "/@/store/modules/auth";

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
        path: '/403',
        name: '403',
        component: () => import('/@/views/error/Error403.vue'),
        meta: {
            title: '用户无权限',
            withoutTab: true,
        },
    },
    {
        path: '/404',
        name: '404',
        component: () => import('/@/views/error/Error404.vue'),
        meta: {
            title: '找不到页面',
            icon: 'icon-park-outline:ghost',
            withoutTab: true,
        },
    },
    {
        path: '/500',
        name: '500',
        component: () => import('/@/views/error/Error500.vue'),
        meta: {
            title: '服务器错误',
            icon: 'icon-park-outline:close-wifi',
            withoutTab: true,
        },
    },
    {
        path: '/:pathMatch(.*)*',
        component: () => import('/@/views/error/Error404.vue'),
        name: '404',
        meta: {
            title: '找不到页面',
            icon: 'icon-park-outline:ghost',
            withoutTab: true,
        },
    },
    {
        path: "/",
        redirect: '/dashboard',
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
                path: "/user-list",
                name: "user-list",
                component: () => import("/@/views/user/index.vue"),
                meta: {
                    title: "User Management",
                    requiresAuth: true,
                },
            },
            {
                path: "/user-roles",
                name: "user-roles",
                component: () => import("/src/views/setting/user-role/index.vue"),
                meta: {title: "User Roles",},
            },
        ],
    },
];
const router = createRouter({
    history: createWebHashHistory(),
    routes,
    scrollBehavior() {
        return {top: 0};
    },
});
// 路由守卫
router.beforeEach(async (to, _from, next) => {
    const appStore = useAppStore();
    const authStore = useAuthStore()
    // 判断是否是外链，如果是直接打开网页并拦截跳转
    if (to.meta.href) {
        window.open(to.meta.href as string);
        return false;
    }
    if (appStore.showProgress) {
        window.$loadingBar?.start();
    }
    const isLogin = Boolean(authStore.accessToken)
    if (!isLogin) {
        if (to.name === 'login')
            next()

        if (to.name !== 'login') {
            const redirect = to.name === '404' ? undefined : to.fullPath
            next({path: '/login', query: {redirect}})
        }
        return false
    }
    // 判断当前页是否在login,则定位去首页
    if (to.name === 'login') {
        next({path: '/'})
        return false
    }

    next()
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
