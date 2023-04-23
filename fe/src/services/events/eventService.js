import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

/**
 * Class event service
 *
 * @author Harmohat Khangura & Arunn Lingeswaren
 */
export class EventService {
    #axiosRequest
    #resourcesUrl

    /**
     * Constructor for class UsersService
     * @param resourcesUrl The url of the resource
     */
    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        // initializing service class
        this.#axiosRequest = new AxiosRequest()
        console.log("Created EventService for " + resourcesUrl)
    }

    /**
     * Async function gets all events
     * @returns {Promise<{data: *, statusCode: *}|undefined>}
     */
    async getAllEvents() {
        return await this.#axiosRequest.doRequest(this.#resourcesUrl, "GET");
    }

    /**
     * Async function gets all events by user id
     * @param userId
     * @returns {Promise<{data: *, statusCode: *}|undefined>}
     */
    async getAllEventsByUserId(userId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/user/${userId}`, "GET")
    }

    /**
     * Async function gets all the events and the amount of users who registered for the specific events
     * @returns {Promise<{data: *, statusCode: *}|undefined>}
     */
    async getCountParticipantsForAllQuizzes() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/total/registered`, "GET")
    }

    /**
     * Async function creates new event
     * @param event The event object
     * @returns {Promise<{data: *, statusCode: *}|undefined>}
     */
    async addToEvent(event) {
        return await this.#axiosRequest.doRequest(this.#resourcesUrl, "POST", event)
    }

    /**
     * Async function deletes event by id
     * @param id The id of the event
     * @returns {Promise<{data: *, statusCode: *}|undefined>}
     */
    async deleteEvent(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "DELETE")
    }
}