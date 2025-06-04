import type { ILoginInfo } from "./user";

export interface ILocal {
  /* 存储用户信息 */
  userInfo: ILoginInfo;
  /* 存储访问token */
  accessToken: string;
  /* 存储刷新token */
  refreshToken: string;
}
