export type IRoleType = "super" | "admin" | "user";
export interface IRole {
  /** 用户id */
  id?: string;
  /** 用户名 */
  name?: IRoleType;
}
