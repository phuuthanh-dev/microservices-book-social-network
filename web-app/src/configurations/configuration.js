export const CONFIG = {
    API_GATEWAY: "http://localhost:8888/api",
};

export const OAuthConfig = {
    CLIENT_ID: "1068097809138-iespprrehar52fbuabvsahs81mtnoqtc.apps.googleusercontent.com",
    REDIRECT_URI: "http://localhost:3001/authenticate",
    AUTH_URI: "https://accounts.google.com/o/oauth2/auth",
}

export const API = {
    LOGIN: "/identity/auth/token",
    MY_INFO: "/profile/users/my-profile",
    MY_IDENTITY: "/identity/users/my-info",
    CREATE_PASSWORD: "/identity/users/create-password",
    MY_POST: "/post/my-posts",
};