import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";

export const getMyInfo = async () => {
    return await httpClient
        .get(API.MY_IDENTITY,
            { headers: { Authorization: `Bearer ${getToken()}` },
            });
}

export const createPassword = async (password) => {
    return await httpClient
        .post(API.CREATE_PASSWORD, 
            { password }, 
            { headers: { Authorization: `Bearer ${getToken()}` },
        });
}