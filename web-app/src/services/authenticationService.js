import {getToken, removeToken, setToken} from "./localStorageService";
import httpClient from "../configurations/httpClient";
import {API} from "../configurations/configuration";

export const logIn = async (username, password) => {
    let response = null;
    for (let i = 0; i < 100; i++) {
        response = await httpClient.post(API.LOGIN, {
            username: username,
            password: password,
        });
    }

    setToken(response.data?.result?.token);

    return response;
};

export const logOut = () => {
    removeToken();
};

export const isAuthenticated = () => {
    return getToken();
};