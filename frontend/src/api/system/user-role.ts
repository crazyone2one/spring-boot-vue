import { alovaInstance } from "/@/api";
import type { IRole } from "/@/types/role.ts";

/**
 * 获取角色列表
 */
export const fetchUserRoleList = () =>
  alovaInstance.Get<Array<IRole>>("user/role/list");

export const fetchSaveRole = (param: Partial<IRole>) =>
  alovaInstance.Post("/user/role/save", param);
