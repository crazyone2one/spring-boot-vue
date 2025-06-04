type MenuType = 'dir' | 'page'
interface RouteMeta {
    /* 页面标题，通常必选。 */
    title: string
    /* 图标，一般配合菜单使用 */
    icon?: string
    /* 是否需要登录权限。 */
    requiresAuth?: boolean
    menuType?: MenuType
}
interface BaseRoute {
    /** 路由名称(路由唯一标识) */
    name: string
    /** 路由路径 */
    path: string
    /** 路由重定向 */
    redirect?: string
    /* 页面组件地址 */
    componentPath?: string | null
    /* 路由id */
    id: number
    /* 父级路由id，顶级页面为null */
    pid: number | null
}
export type RowRoute = RouteMeta & BaseRoute