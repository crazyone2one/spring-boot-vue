type RequestErrorType = "Response Error" | "Business Error" | null;
type RequestCode = string | number;
interface IRequestError<T> {
  /** 请求服务的错误类型 */
  errorType: RequestErrorType;
  /** 错误码 */
  code: RequestCode;
  /** 错误信息 */
  message: string;
  /** 返回的数据 */
  data?: T;
}
export interface IResponseResult<T> extends IRequestError<T> {
  /** 请求服务是否成功 */
  isSuccess: boolean;
  /** 请求服务的错误类型 */
  errorType: RequestErrorType;
  /** 错误码 */
  code: RequestCode;
  /** 错误信息 */
  message: string;
  /** 返回的数据 */
  data: T;
}
