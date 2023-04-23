/**
 * Class QuestionValidator
 * Validates everything related to the quiz
 *
 * @author Harmohat Khangura
 */
export class QuizValidator {
    #quiz
    #errors

    /**
     * Instantiates a new quiz validator
     * @param quiz The quiz to validate
     */
    constructor(quiz) {
        this.#quiz = quiz;
        this.#errors = {}

        this.validateAll()
    }


    /**
     * Function validates the questions list size by the input value of the total amount of questions to add
     * @param inputValue The input value of the total amount of question to add
     * @param listSize The amount of question added to the list
     */
    validateQuestions(inputValue, listSize) {
        if (inputValue !== listSize) {
            this.#errors = {...this.#errors, questions: {
                message: ' The input value for \'Total Questions\' does not match the amount of questions you added!'}
            };
        }
    }

    /**
     * Function validates all
     */
    validateAll() {
        if (this.#quiz.title.length === 0) {
            this.#errors = {...this.#errors, title: {message: 'Title is required!'}};
        }
        if (this.#quiz.amountToRegister <= 0) {
            this.#errors = {...this.#errors, amountToRegister: {message: 'Value can not be 0 or less then 0'}};
        }
        if (this.#quiz.startsAt.length === 0) {
            this.#errors = {...this.#errors, startsAt: {message: 'Starts at is required!'}};
        }
        if (this.#quiz.endsAt.length === 0) {
            this.#errors = {...this.#errors, endsAt: {message: 'Ends at is required!'}};
        }
        if (this.#quiz.location === undefined ||  this.#quiz.location < 1 || this.#quiz.location > 2) {
            this.#errors = {...this.#errors, location: {message: 'Not a valid location!'}};
        }

        if (this.#quiz.location === 1) {
            let url;
            try {
                url = new URL(this.#quiz.link);
            } catch (_) {
                this.#errors = {...this.#errors, link: {message: 'Not a valid link!'}};
            }
        }
        if (this.#quiz.location === 2 && this.#quiz.address.length === 0) {
            this.#errors = {...this.#errors, address: {message: 'Address is required!'}};
        }
    }


    get errors() {
        return this.#errors;
    }

    set errors(value) {
        this.#errors = value;
    }
}