import {local} from "/@/utils/storage.ts";
import {useAuthStore} from "/@/store/modules/auth";


export const handleRefreshToken = async () => {
    const authStore = useAuthStore()
    const rToken = local.get("refreshToken") ?? ''
    await authStore.handleRefreshToken(rToken)
}