import type {RowRoute} from "/@/router/types.ts";

export const staticRoutes: RowRoute[] = [
    {
        name: 'dashboard',
        path: "/dashboard",
        title: '工作台',
        requiresAuth: true,
        componentPath: '/dashboard/index.vue',
        id: 1,
        pid: null,
        menuType: 'page'
    },
]