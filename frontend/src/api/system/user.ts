import {alovaInstance} from "/@/api";
import type {ICommonPage, ITableQueryParams, IUserItem} from "/@/api/types.ts";

/**
 * 获取用户列表
 * @param param
 */
export const fetchUserPage = (param: ITableQueryParams) => alovaInstance.Post<ICommonPage<IUserItem>>("system/user/page", param)