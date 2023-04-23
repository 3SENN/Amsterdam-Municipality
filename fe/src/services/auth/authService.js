import {AxiosRequest} from "@/assets/js/utils/axiosRequest";
import User from "@/models/User";
import LocalStorageHelper from "@/assets/js/utils/localStoragehelper";
import axios from "axios";

export class AuthService {
    #resourcesUrl
    #axiosRequest
    #GET_REGISTER_URL
    #GET_LOGIN_URL
    #encodedToken

    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        this.#axiosRequest = new AxiosRequest();
        this.#GET_REGISTER_URL = this.#resourcesUrl + '/register'
        this.#GET_LOGIN_URL = this.#resourcesUrl + '/login'
    }

    async register(user) {
        return await this.#axiosRequest.doRequest(this.#GET_REGISTER_URL, 'POST', User.copyConstructor(user));
    }

    async login(user) {
        return await this.#axiosRequest.doRequest(this.#GET_LOGIN_URL, 'POST', user);
    }

    logout() {
        LocalStorageHelper.remove("token")
    }

    saveTokenIntoBrowserStorage(token) {
        LocalStorageHelper.set("token", token)
    }

    encodeToken() {
        if (this.getTokenFromBrowserStorage() === undefined) return
        const base64Url = this.getTokenFromBrowserStorage().split(".")[1];
        const base64 = base64Url.replace("-", "+").replace("_", "/");
        return JSON.parse(window.atob(base64));
    }


    getTokenFromBrowserStorage() {
        return LocalStorageHelper.get("token")
    }

    getProfilePicture() {
        return this.encodeToken() === undefined || this.encodeToken().profilePicture === undefined ? false : this.encodeToken().profilePicture
    }

    getFullName() {
        return this.encodeToken() === undefined || this.encodeToken().sub === undefined ? false : this.encodeToken().sub
    }

    getId() {
        return this.encodeToken() === undefined || this.encodeToken().id === undefined ? false : parseInt(this.encodeToken().id)
    }

    getRole() {
        return this.encodeToken() === undefined || this.encodeToken().role === undefined ? false : parseInt(this.encodeToken().role)
    }

    getEmail() {
        return this.encodeToken() === undefined || this.encodeToken().email === undefined ? false : this.encodeToken().email
    }

    isAdmin() {
        return this.encodeToken() === undefined || this.encodeToken().isAdmin === 'true'
    }
}