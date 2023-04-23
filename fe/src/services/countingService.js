import {AxiosRequest} from "@/assets/js/utils/axiosRequest";
import {Counting} from "@/models/Counting";
import {CountingLabel} from "@/models/CountingLabel";

/**
 * Class counting service
 *
 * @author Jordy Mol
 */

export class CountingService {
    #resourcesUrl
    #axiosRequest

    /**
     * Constructor for class CountingService
     * @param resourcesUrl - The url to the back-end
     */
    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        this.#axiosRequest = new AxiosRequest();
        console.log("Created CountingService for " + resourcesUrl);
    }

    /**
     * Async function to get all counting levels (makes call to the back-end)
     * @returns {Promise<*>}
     */
    async findAll() {
        const response = await this.#axiosRequest.doRequest(this.#resourcesUrl, "GET");
        return response.data.map(Counting.copyConstructor);
    }

    /**
     * Async function to get all the scores from the counting levels (makes call to the back-end)
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getScores() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/scores`, "GET");
    }

    /**
     * Async function to get the amount of counting levels played (makes call to the back-end)
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getTotalAmountOfCountingResults() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/total/amount`, "GET");
    }

    /**
     * Async function deletes a counting level by a counting id (makes call to the back-end)
     * @param id - The id of the counting level to delete
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async deleteById(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "DELETE");
    }

    /**
     * Async function gets a counting level by a counting id (makes call to the back-end)
     * @param id - The id of the counting level to get
     * @returns {Promise<Counting & *>}
     */
    async getCountingById(id) {
        const response = await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "GET");
        return Counting.copyConstructor(response.data);
    }

    /**
     * Async function to get all the counting levels for a specific user (makes call to the back-end)
     * @param userId - The id of the user to get counting levels for
     * @returns {Promise<*>}
     */
    async getAllCountingLevelsByUserId(userId) {
        const response = await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/user/${userId}`, "GET");
        return response.data.map(Counting.copyConstructor);
    }

    /**
     * Async function to save a counting level (makes call to the back-end)
     * @param counting - The counting level to be saved
     * @param userId - The id of the user that has played the counting level
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async addCounting(counting, userId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}?userId=${userId}`, "POST", counting);
    }

    /**
     * Async function gets the highscore from every player that has participated in the counting game (makes call to the back-end)
     * @returns {Promise<*>}
     */
    async getHighscore() {
        const response = await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/highscores`, "GET");
        return response.data.map(Counting.copyConstructor);
    }

    /**
     * Async function to save a counting label (makes call to the back-end)
     * @param countingLabel - The counting label to be saved
     * @param countingId -  The id of the counting level to link the label to
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async addLabel(countingLabel, countingId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/label?countingId=${countingId}`, "POST", countingLabel);
    }

    /**
     * Async function to get all the labels for a specific counting level (makes call to the back-end)
     * @param countingId - The if of the counting level to get labels for
     * @returns {Promise<*>}
     */
    async getLabelsFromCountingId(countingId) {
        const response = await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/label/${countingId}`);
        return response.data.map(CountingLabel.copyConstructor);
    }
}