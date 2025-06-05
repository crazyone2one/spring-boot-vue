import {defineStore} from "pinia";
import {reactive, unref} from "vue";
import {fetchLogin} from "/@/api/login";
import router from "/@/router";
import type {ILoginInfo} from "/@/types/user";
import {local} from "/@/utils/storage";

export const useAuthStore = defineStore("auth-store", () => {
    const sessionInfo = reactive({
        userInfo: local.get("userInfo"),
        token: local.get("accessToken") || "",
    });
    const isLogin = () => {
        return Boolean(sessionInfo.token);
    };
    const clearAuthStorage = () => {
        local.remove("accessToken");
        local.remove("refreshToken");
        local.remove("userInfo");
    };
    const handleLoginInfo = (data: ILoginInfo) => {
        local.set("userInfo", data);
        local.set("accessToken", data.access_token);
        local.set("refreshToken", data.refresh_token);
        sessionInfo.userInfo = data
        sessionInfo.token = data.access_token
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
    const logout = async () => {
        const route = unref(router.currentRoute);
        clearAuthStorage();
        sessionInfo.token = ''
        sessionInfo.userInfo = local.get("userInfo")
        if (route.meta.requiresAuth) {
            router.push({
                name: "login",
                query: {
                    redirect: route.fullPath,
                },
            });
        }
    };
    return {sessionInfo, isLogin, clearAuthStorage, login, logout};
});
