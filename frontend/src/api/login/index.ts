import type { IResponseResult } from "../types";
import { alovaInstance } from "/@/api";
import type { ILoginInfo } from "/@/types/user";

interface ILogin {
  username: string;
  password: string;
}

export const fetchLogin = (param: ILogin) => {
  const methodInstance = alovaInstance.Post<IResponseResult<ILoginInfo>>(
    "api/auth/login",
    param
  );
  methodInstance.meta = {
    authRole: null,
  };
  return methodInstance;
};

export const fetchUpdateToken = (param: { refreshToken: string }) => {
  const methodInstance = alovaInstance.Post("api/auth/refresh", param);
  methodInstance.meta = {
    authRole: "refreshToken",
  };
  return methodInstance;
};
