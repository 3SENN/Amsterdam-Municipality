import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

/**
 * Class users service
 *
 * @author Harmohat Khangura & Arunn Lingeswaran
 */
export class UsersService {
    resourcesUrl
    axiosRequest

    /**
     * Constructor for class UsersService
     * @param resourcesUrl The url of the resource
     */
    constructor(resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
        this.axiosRequest = new AxiosRequest();
        console.log("Created UsersService for " + resourcesUrl)
    }

    /**
     * Async function to get all the users (makes call to the back-end)
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async findAll() {
        return await this.axiosRequest.doRequest(this.resourcesUrl, "GET");
    }

    async getTotalAmountOfUsers() {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/total/amount`, "GET");
    }

    /**
     * Async function to get a specific user (makes call to the back-end)
     * @param id - The id of the user to get
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async getUserById(id) {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/${id}`, "GET");
    }

    /**
     * Async function to get a specific user (makes call to the back-end)
     * @param email - The email of the user to get
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async getUserByEmail(email) {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/email/${email}`, "GET");
    }

    /**
     * Async function deletes the user by id (makes call to the back-end)
     * @param id - The id of the user to delete
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async deleteById(id) {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/${id}`, "DELETE");
    }

    /**
     * Async function adds the user
     * @param user - The user to add
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async add(user) {
        return await this.axiosRequest.doRequest(this.resourcesUrl, "POST", user);
    }

    /**
     * Async functions updates/edits the user by id
     * @param id - The id of the user to edit
     * @param user - The new user data
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async saveUser(id, user) {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/${id}`, "PUT", user);
    }

    /**
     * Async function to update profile information
     * @param email - The email of the user to update
     * @param user - The new user data
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async updateProfile(email, user) {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/email/profile/${email}`, "PUT", user);
    }

    /**
     * Async function to update password
     * @param email - The email of the user to update
     * @param user - The new user data
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async updatePassword(email, user) {
        return await this.axiosRequest.doRequest(`${this.resourcesUrl}/email/password/${email}`, "PUT", user);
    }
}
