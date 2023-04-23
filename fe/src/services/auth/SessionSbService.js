import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

export class SessionSbService {
    BROWSER_STORAGE_ITEM_NAME
    RESOURCES_URL
    #GET_LOGIN_URL
    #axiosRequest

    constructor(browserStorageItemName, resourcesUrl) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;

        this.#axiosRequest = new AxiosRequest();
        this.#GET_LOGIN_URL = this.RESOURCES_URL + '/login'
    }

    async signIn(user) {
        return await this.#axiosRequest.doRequest(this.#GET_LOGIN_URL, 'POST', user);
    }

    getId() {
    }

    getEmail() {

    }

    getRole() {}

    getFullName() {}

}