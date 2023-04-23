export class Answer {
    id;
    option;
    isAnswer;
    questionId;

    constructor(id, option, isAnswer, questionId) {
        this.id = id;
        this.option = option;
        this.isAnswer = isAnswer;
        this.questionId = questionId;
    }

    static copyConstructor(answer) {
        if (answer == null) return null;
        return Object.assign(new answer(), answer);
    }
}