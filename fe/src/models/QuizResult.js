export default class QuizResult {
    userId
    quizID
    score


    constructor(userId, quizID, score) {
        this.userId = userId;
        this.quizID = quizID;
        this.score = score;
    }

    static createSample() {
        return new QuizResult(1, 1, 112);
    }
}