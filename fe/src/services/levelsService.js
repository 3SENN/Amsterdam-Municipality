import axios from "axios";

export class LevelsService {
    #resourcesUrl;

    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        console.log("Created LevelsService for " + resourcesUrl);
    }

    async findAll() {
        console.log('LevelsService.asyncFindAll()...');
        try {
            const response = await axios.get(this.#resourcesUrl)
            return response.data
        } catch(e) {
            console.error(`Error occurred while loading levels: ${e}`);
        }
    }
}