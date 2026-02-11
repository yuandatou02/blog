import Cookies from "js-cookie";

const TokenKey: string = "Token";

export const setToken = (token: string) => {
    return Cookies.set(TokenKey, token);
};

export const getToken = () => {
    return Cookies.get(TokenKey);
};

export const removeToken = () => {
    return Cookies.remove(TokenKey);
};
