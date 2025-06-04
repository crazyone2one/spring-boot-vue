export type IRoleType = "super" | "admin" | "user";
export interface IRole {
  /** 用户id */
  id?: number;
  /** 用户名 */
  role?: IRoleType;
}
