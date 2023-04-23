import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

/**
 * Class QuizService
 *
 * @author Harmohat Khangura
 */
export class QuizService {
    #resourcesUrl
    #axiosRequest

    /**
     * Constructor for class UsersService
     * @param resourcesUrl The url of the resource
     */
    constructor(resourcesUrl) {
        this.#resourcesUrl = resourcesUrl;
        // initializing axiosRequest class
        this.#axiosRequest = new AxiosRequest()
        console.log("Created QuizService for " + resourcesUrl)
    }

    /**
     * Async function to get all the quizzes
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async findAll() {
        return await this.#axiosRequest.doRequest(this.#resourcesUrl, "GET")
    }

    /**
     * Async function to get a specific quiz
     * @param id The id of the quiz
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async getQuizById(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "GET")
    }

    /**
     * Async function to delete a specific quiz (makes call to the back-end)
     * @param id The id of the quiz
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async deleteQuizById(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "DELETE")
    }

    /**
     * Async function to retrieve all qusetions
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getAllQuestions() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/questions`, "GET")
    }

    /**
     * Async function to get total count of all questions
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getTotalAmountOfQuestions() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/questions/total/amount`, "GET");
    }

    /**
     * Async function to get total count of quizzes
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getTotalAmountOfQuizzes() {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/total/amount`, "GET");
    }

    /**
     * Async function to get all the questions for quiz
     * @param id The id of the quiz
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     * */
    async getQuestionById(id) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/questions/${id}`, "GET")
    }

    /**
     * Async function to retrieve all questions by quiz id
     * @param quizId - The id of the quiz
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async getQuestionsByQuizId(quizId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${quizId}/questions`, "GET")
    }

    /**
     * Async function to remove question by question id
     * @param questionId - The id of the question
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async deleteQuestionById(questionId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/question/${questionId}`, "DELETE")
    }

    /**
     * Async function gets all the quizzes where the user is not signed up for
     * @param userId The id of the user
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async getQuizzesWhereUserIsNotSignedUpFor(userId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/not/registered/${userId}`, "GET")
    }

    /**
     * Async function to create a new quiz
     * @param userId The id of the user who created the quiz
     * @param quiz The quiz object which holds the data to post
     * @returns {Promise<{data: *, statusCode: number}|undefined|{message: *, statusCode: number}>}
     */
    async createNewQuiz(userId, quiz) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}`, "POST", quiz, {id: userId})
    }

    /**
     * Async function creates a new  question
     * @param question The quiz question object which holds the data to post
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async createNewQuestion(question) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/questions`, "POST", question)
    }

    /**
     * Add question to a specific quiz
     * @param quizId
     * @param questionId
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async addQuestionsToQuiz(quizId, questionId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${quizId}/questions/${questionId}`, "POST")
    }

    /**
     * Remove question from a specific quiz
     * @param quizId
     * @param questionId
     * @returns {Promise<{data: *, statusCode: number}|undefined>}
     */
    async removeQuestionsFromQuiz(quizId, questionId) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${quizId}/questions/${questionId}`, "PUT")
    }

    /**
     * Async function creates a new quiz question option
     * @param quizQuestionId The id of the quiz question
     * @param quizQuestionOption The quiz question option object which holds the data to post
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async createNewQuizQuestionOption(quizQuestionId, quizQuestionOption) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${quizQuestionId}/option`, "POST", quizQuestionOption)
    }

    /**
     * Async function updates the quiz
     * @param id The id of the quiz to edit
     * @param quiz The quiz object which holds the data for edit
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async updateQuiz(id, quiz) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/${id}`, "PUT", quiz)
    }

    /**
     * Async function updates the question
     * @param id The id of the question to edit
     * @param question The question object which holds the data for edit
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async updateQuestion(id, question) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/question/${id}`, "PUT", question)
    }

    /**
     * Async function updates the quiz question option
     * @param id The id of the quiz question option to edit
     * @param quizQuestionOption The quiz question option object which holds the data for edit
     * @returns {Promise<{data: *, statusCode: number}|undefined|{data: *, statusCode: number}|{data: *, statusCode: number}|*>}
     */
    async updateQuestionOption(id, quizQuestionOption) {
        return await this.#axiosRequest.doRequest(`${this.#resourcesUrl}/question/option/${id}`, "PUT", quizQuestionOption)
    }


}