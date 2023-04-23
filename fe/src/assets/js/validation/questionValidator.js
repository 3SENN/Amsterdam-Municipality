/**
 * Class QuestionValidator
 * Validates everything related to the quiz questions
 *
 * @author Harmohat Khangura
 */
export class QuestionValidator {
    #question
    #answers
    #errors

    /**
     * Instantiates a new question validator
     * @param question The question to validate
     * @param answers The answers to validate
     */
    constructor(question, answers) {
        this.#question = question;
        this.#answers = answers;
        this.#errors = {}

        this.validateAll()
    }


    /**
     * Function validates all
     */
    validateAll() {
        if (this.#question.question.length === 0) {
            this.#errors = {...this.#errors, question: {message: 'Question is required!'}};
        }

        if (this.#question.points <= 0 || this.#question.points.length === 0) {
            this.#errors = {...this.#errors, points: {message: 'Not a valid point value!'}};
        }

        if (this.#question.block <= 0 || this.#question.block.length === 0) {
            this.#errors = {...this.#errors, block: {message: 'Not a valid block value!'}};
        }

        this.validateMultipleAnswers()

        this.checkForNoAnswerTicked()

        this.checkAnswersLength()
    }


    /**
     * Function validates for multiple answers
     * If the user did tick for multiple answers, the user must have ticked more than 1 answer
     */
    validateMultipleAnswers() {
        let totalAnswers = 0
        if (this.#question.hasMultipleAnswers) {
            for (let answer of this.#answers) {
                if (answer.isAnswer) totalAnswers++
            }

            if (!(totalAnswers > 1)) {
                this.#errors = {
                    ...this.#errors, hasMultipleAnswers: {
                        message: 'You chose to have multiple answers, but you did not tick more than 1 answer'
                    }
                };
            }
        }
    }

    /**
     * Function validates for ticked answers
     * The user must have ticked an answer
     */
    checkForNoAnswerTicked() {
        let totalAnswers = 0

        for (let answer of this.#answers) {
            if (answer.isAnswer) totalAnswers++
        }

        if (totalAnswers === 0) {
            this.#errors = {
                ...this.#errors, answersEmpty: {
                    message: 'You must tick an answer'
                }
            };
        }

        // If user ticked multiple answers the user mast have ticked true for multipleAnswers
        if (!this.#question.hasMultipleAnswers) {
            if (totalAnswers > 1) {
                this.#errors = {
                    ...this.#errors, hasMultipleAnswers: {
                        message: 'You did not choose to have multiple answers, tick only one answer'
                    }
                };
            }
        }
    }

    /**
     * Function validates the if the answers are empty
     */
    checkAnswersLength() {
        for (let answer of this.#answers) {
            if (answer.option.length <= 0) {
                this.#errors = {
                    ...this.#errors, answersLength: {
                        message: 'The answer fields are required!'
                    }
                };
            }
        }
    }

    get errors() {
        return this.#errors;
    }

    set errors(value) {
        this.#errors = value;
    }
}