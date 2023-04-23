import {LevelsLocked} from "@/models/LevelsLocked";
import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

/**
 * Class levelsLocked service
 *
 * @author Jordy Mol
 */

export class LevelsLockedService {
    #resourcesUrl
    #axiosRequest

    /**
     * Constructor for class LevelsLockedService
     * @param resourcesUrl - The url to the back-end
     */
    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        this.#axiosRequest = new AxiosRequest();
        console.log("Created LevelsLockedService for " + resourcesUrl);
    }

    /**
     * Async function to get all the levelsLocked data (makes call to the back-end)
     * @returns {Promise<*>}
     */
    async findAll() {
        const response = await this.#axiosRequest.doRequest(this.#resourcesUrl, "GET");
        return response.data.map(LevelsLocked.copyConstructor);
    }

    /**
     * Async function to get the levelsLocked object for a specific user (makes call to the back-end)
     * @param userId - The id of the user to get the levelsLocked object for
     * @returns {Promise<LevelsLocked & *>}
     */
    async getLevelsLockedByUserId(userId) {
        const response = await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${userId}`, "GET")
            .catch(function (error) {
                if  (error.message.status === 404) {
                    return null;
                }
            })
        return LevelsLocked.copyConstructor(response?.data);
    }

    /**
     * Async function to save a new levelsLocked object (makes call to the back-end)
     * @param levelsLocked - The levelsLocked object to save
     * @param userId - The id of the user to save the levelsLocked object for
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async addLevelsLocked(levelsLocked, userId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}?userId=${userId}`, "POST", levelsLocked);
    }

    /**
     * Async function to update a levelsLocked object (makes call to the back-end)
     * @param levelsLocked - The new levelsLocked object
     * @param userId - The id of the user to update the levelsLocked object for
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async updateLevelsLocked(levelsLocked, userId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${userId}`, "PUT", levelsLocked);
    }
}