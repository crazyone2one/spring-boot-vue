import {defineStore} from "pinia";
import {ref, unref} from "vue";
import {fetchLogin, fetchLogout, fetchUpdateToken} from "/@/api/login";
import router from "/@/router";
import type {ILoginInfo} from "/@/types/user";

export const useAuthStore = defineStore("auth", () => {
    const accessToken = ref('');
    const refreshToken = ref('');
    const userInfo = ref<ILoginInfo | null>(null);
    const isAuthenticated = ref(false);

    const clearAuthStorage = () => {
        accessToken.value = '';
        refreshToken.value = '';
        userInfo.value = null;
        isAuthenticated.value = false;
    };
    const handleLoginInfo = (data: ILoginInfo) => {
        userInfo.value = data
        accessToken.value = data.access_token
        refreshToken.value = data.refresh_token
        // 进行重定向跳转
        const route = unref(router.currentRoute);
        const query = route.query as { redirect: string };
        router.push({
            path: query.redirect || "/",
        });
    };
    const login = async (username: string, password: string) => {
        fetchLogin({username, password}).then(res => handleLoginInfo(res))
    };
    const handleRefreshToken = async (refreshToken: string) => {
        fetchUpdateToken({refreshToken}).then(res => handleLoginInfo(res))
    }
    const logout = async () => {
        await fetchLogout()
        const route = unref(router.currentRoute);
        clearAuthStorage();
        if (route.meta.requiresAuth) {
            router.push({
                name: "login",
                query: {
                    redirect: route.fullPath,
                },
            });
        }
    };
    return {accessToken, refreshToken, userInfo, clearAuthStorage, login, logout, handleRefreshToken};
}, {
    persist: {
        key: 'auth'
    }
});
