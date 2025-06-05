import {createAlova} from 'alova';
import adapterFetch from 'alova/fetch';
import VueHook from 'alova/vue';
import {createServerTokenAuthentication} from "alova/client";
import {handleRefreshToken} from "/@/api/http/handle.ts";

const {onAuthRequired, onResponseRefreshToken} = createServerTokenAuthentication({
    // 添加token
    assignToken(method) {
        const token = localStorage.getItem('token');
        if (token) {
            method.config.headers.Authorization = `Bearer ${token}`;
        }
    },
    refreshTokenOnSuccess: {
        // 响应时触发，可获取到response和method，并返回boolean表示token是否过期
        // 当服务端返回401时，表示token过期
        isExpired: (response, method) => {
            const isExpired = method.meta && method.meta.isExpired
            return response.status === 401 && !isExpired;
        },

        // 当token过期时触发，在此函数中触发刷新token
        handler: async (_response, method) => {
            try {
                if (!method.meta) {
                    method.meta = {isExpired: true};
                } else {
                    method.meta.isExpired = true
                }
                await handleRefreshToken();
            } catch (error) {
                // token刷新失败，跳转回登录页
                location.href = '/login';
                // 并抛出错误
                throw error;
            }
        }
    },
});
// 创建alova实例
export const alovaInstance = createAlova({
    baseURL: import.meta.env.VITE_APP_BASE_API ,
    timeout: 10000,
    statesHook: VueHook,
    requestAdapter: adapterFetch(),
    // 请求拦截器
    beforeRequest: onAuthRequired(method => {
        // ...原请求前拦截器
        console.log(method);
    }),
    responded: onResponseRefreshToken({
        // 请求成功的拦截器
        // 当使用 `alova/fetch` 请求适配器时，第一个参数接收Response对象
        // 第二个参数为当前请求的method实例，你可以用它同步请求前后的配置信息
        onSuccess: async (response, method) => {
            if (response.status == 500) {
                window.$message.error("服务器异常")
                throw new Error("服务器异常")
            }
            if (response.status >= 400) {
                throw new Error(response.statusText);
            }
            const json = await response.json();
            if (json.code !== 200) {
                // 抛出错误或返回reject状态的Promise实例时，此请求将抛出错误
                throw new Error(json.message);
            }
            // 返回blob数据
            if (method.meta?.isBlob) {
                return response.blob()
            }
            // 解析的响应数据将传给method实例的transform钩子函数，这些函数将在后续讲解
            return json.data;
        },
        // 请求失败的拦截器
        // 请求错误时将会进入该拦截器。
        // 第二个参数为当前请求的method实例，你可以用它同步请求前后的配置信息
        onError: (error, method) => {
            const tip = `[${method.type}] - [${method.url}] - ${error.message}`
            window.$message.error(tip)
        },
    })
})