import axios from "axios";
import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

/**
 * Class users service
 *
 * @author Senne Chin
 */

export class QuizResultService {
    #resourcesUrl
    #axiosRequest
    #GET_QUIZ_RESULTS = '/quiz/result'
    #ERR_NETWORK = "ERR_NETWORK"
    #statusCodes = require("../../../assets/js/utils/httpStatusCodes")

    /**
     * Constructor for class QuizResultService
     * @param resourcesUrl The url of the resource
     */
    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        console.log("Created QuizResultService for " + resourcesUrl)
        this.#axiosRequest = new AxiosRequest()
    }

    /**
     * Async function to get all the quiz results (makes call to the back-end)
     * @returns {Promise<{data: any, statusCode: number}>}
     */
    async findAll() {
        return await this.#axiosRequest.doRequest(this.#resourcesUrl, "GET")
    }

    async findAllResults() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/all`, "GET")
    }

    async deleteById(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "DELETE");
    }

    async getTotalAmountOfQuizResults() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/total/amount`, "GET");
    }

    async getScores() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/scores`, "GET");
    }


    /**
     * Async function to get a specific user quiz results(makes call to the back-end)
     * @param id The id of the user to get the quiz results
     * @returns {Promise<{data: any, statusCode: number}>}
     */
    async getQuizResultByUserId(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "GET")
    }

    /**
     * Async function to get a specific quiz result
     * @param id - The id of the quiz to retrieve
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getQuizResultByQuizId(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/by/${id}`, "GET")
    }

    async getByUserIdAndQuizId(userId, quizId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${quizId}/user/${userId}`, "GET")
    }

    async addScore(score) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/results`, "POST", score)
    }
}