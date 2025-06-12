import {useAuthStore} from "/@/store/modules/auth";


export const handleRefreshToken = async () => {
    const authStore = useAuthStore()
    await authStore.handleRefreshToken(authStore.refreshToken)
}