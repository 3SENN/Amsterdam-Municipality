import {QuizService} from "@/services/quiz/quizService";
import quizzesMockData from "../../../data/admin/quiz/quizzesMockData.js";

const QUIZ_API_URL = "http://localhost:8083/api/quiz"
const MOCK_QUIZZES = quizzesMockData.quizzes

// instance for testing
let quizService

// Runs before each test
beforeEach(function () {
    quizService = new QuizService(QUIZ_API_URL)
});

it('quizService.findAll returns all quizzes', async function () {
    // Mocking the return value of .findAll
    quizService.findAll = jest.fn().mockReturnValue(MOCK_QUIZZES);

    // Getting and storing all the quizzes inside the variable
    const data = quizService.findAll()

    // Expecting that the return value of .findAll should return the correct data
    expect(data,
        'quizService.findAll does not return all quizzes')
        .toStrictEqual(MOCK_QUIZZES);

    // Data must exist due to the mocked return value
    expect(data.length,
        `quizService.findAll does not return any quizzes`)
        .toBeGreaterThan(0)

    // Data must contain 4 rows of data due to the mocked return value
    expect(data.length,
        `quizService.findAll does not return 4 quizzes`)
        .toBe(4)
})

it('quizService.getQuizById returns quiz by id', async function () {
    const CREATED_BY_EMAIL = "john@gmail.com"
    // Mocking the return value of .getQuizById
    quizService.getQuizById = jest.fn().mockReturnValue(MOCK_QUIZZES[1]);

    // Getting and storing the quiz inside the variable
    const data = quizService.getQuizById()

    // Expecting that the return value of .getQuizById should return the correct quiz
    expect(data,
        'quizService.getQuizById does not return the correct quiz')
        .toStrictEqual(MOCK_QUIZZES[1]);

    // Expecting that the id of the return value of .getQuizById should contain the correct quiz id
    expect(data.id,
        `quizService.getQuizById does not contain the correct id`)
        .toStrictEqual(MOCK_QUIZZES[1].id)

    // Expecting that the createdBy of the return value of .getQuizById should contain the correct createdBy
    expect(data.createdBy,
        `quizService.getQuizById does not contain the correct data for createdBy`)
        .toStrictEqual(MOCK_QUIZZES[1].createdBy)

    // Expecting that the email of the return value of .getQuizById should contain the correct email
    expect(data.createdBy.email,
        `quizService.getQuizById does not contain the correct email. Expected to be: ${CREATED_BY_EMAIL}`)
        .toStrictEqual(MOCK_QUIZZES[1].createdBy.email)
})