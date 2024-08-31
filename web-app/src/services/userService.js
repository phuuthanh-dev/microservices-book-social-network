import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";

export const getMyInfo = async () => {
    const headers = { Authorization: `Bearer ${getToken()}` };

    const [identity, profile] = await Promise.all([
        httpClient.get(API.MY_IDENTITY, { headers }),
        httpClient.get(API.MY_INFO, { headers }),
    ]);

    profile.data.result.noPassword = identity.data.result.noPassword;

    return profile;
}

export const createPassword = async (password) => {
    return await httpClient
        .post(API.CREATE_PASSWORD, 
            { password }, 
            { headers: { Authorization: `Bearer ${getToken()}` },
        });
}