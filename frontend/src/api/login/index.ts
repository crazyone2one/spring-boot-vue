import {alovaInstance} from "/@/api";
import type {ILoginInfo} from "/@/types/user";

interface ILogin {
    username: string;
    password: string;
}

export const fetchLogin = (param: ILogin) => {
    const methodInstance = alovaInstance.Post<ILoginInfo>(
        "api/auth/login",
        param
    );
    methodInstance.meta = {
        authRole: null,
    };
    return methodInstance;
};

export const fetchUpdateToken = (param: { refreshToken: string }) => {
    const methodInstance = alovaInstance.Post<ILoginInfo>("api/auth/refresh", param);
    methodInstance.meta = {
        authRole: "refreshToken",
    };
    return methodInstance;
};
export const fetchLogout = () => alovaInstance.Post("api/auth/logout")
