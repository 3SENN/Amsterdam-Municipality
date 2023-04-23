import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

export class cardsService{
    #resourcesURL;
    #axiosRequest;
    #GET_JOURNAL_URL = '/journal'
    #ERR_NETWORK = "ERR_NETWORK"
    #statusCodes = require("../assets/js/utils/httpStatusCodes")

    constructor(resourcesURL) {
        this.#resourcesURL = resourcesURL;
        this.#axiosRequest = new AxiosRequest();
        console.log("Created cardsService for " + resourcesURL)
    }

    async findAll(){
        return await this.#axiosRequest.doRequest(this.#resourcesURL, "GET");
    }

    async findById(id){
        return await this.#axiosRequest.doRequest(`${this.#resourcesURL}/${id}`, "GET");
    }

    async findByUserId(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesURL}/user/${id}`, "GET");
    }

    async addCard(journalCard){
        return await this.#axiosRequest.doRequest(this.#resourcesURL, "POST", journalCard);
    }

    async updateCard(id, journalCard){
        return await this.#axiosRequest.doRequest(`${this.#resourcesURL}/${id}`, "PUT", journalCard);
    }

    async deleteCardById(id){
        return await this.#axiosRequest.doRequest(`${this.#resourcesURL}/${id}`, "DELETE");
    }

}