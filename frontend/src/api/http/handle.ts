import {ERROR_STATUS} from "/@/api/http/config.ts";

type ErrorStatus = keyof typeof ERROR_STATUS
export const handleRefreshToken = async () => {

}
export const handleResponseError = (response: Response) => {
    const error = {
        errorType: 'Response Error',
        code: 0,
        message: ERROR_STATUS.default,
        data: null,
    }
    const errorCode: ErrorStatus = response.status as ErrorStatus
    const message = ERROR_STATUS[errorCode] || ERROR_STATUS.default
    Object.assign(error, {code: errorCode, message})
    return error
}