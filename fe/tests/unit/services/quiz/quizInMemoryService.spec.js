import {InMemoryEntitiesService} from "@/services/in-memory-entities-service";
import Quiz from "@/models/Quiz";

// instance for testing
let quizService

// Runs before any of the tests
beforeAll(() => {
    quizService = new InMemoryEntitiesService(30000, (id) => Quiz.createSample(id))
});

// !TEST: quizService
describe('quiz-service was set up properly', () => {
    it('Entities should hold data', () => {
        // The entities should contain data due to the sample creator
        expect(quizService.entities.length,
            'the entities have no data')
            .toBeGreaterThan(0);
    })

    it('Last id should be set ', () => {
        // The lastId should contain the last id due to the sample creator
        expect(quizService.lastId,
            'the last id was not set')
            .toBeDefined()
    });
})

// !TEST: quizService.findAll
describe('quizService.findAll', () => {
    it('quizService.findAll return all the correct quizzes', () => {
        // Getting and storing all the quizzes inside the variable
        const response = quizService.findAll();
        const quizzes = response.data

        // Expecting that the return value of .findAll should return the correct entities
        expect(quizzes,
            'quizService.findAll does not return all books')
            .toStrictEqual(quizService.entities);
    })
})

// !TEST: quizService.findById
describe('quizService.findById', () => {
    let entitiesLength, quiz1, quizRandom, quizLast

    // Runs  before each of the tests in the `Describe`
    beforeEach(() => {
        quizService = new InMemoryEntitiesService(30000, (id) => Quiz.createSample(id))

        // Getting the amount of entities
        entitiesLength = quizService.entities.length
        // Getting the first quiz
        quiz1 = quizService.entities[0];
        // Getting a random quiz
        quizRandom = quizService.entities[Math.floor(Math.random() * entitiesLength)];
        // Getting the first quiz
        quizLast = quizService.entities[entitiesLength - 1];
    });

    it('quizService.findById returns the first quiz ', () => {
        // Expected to find the first quiz
        expect(quizService.findById(quiz1.id),
            `quizService.findById cannot find the quiz with id:[${quiz1.id}]`)
            .toStrictEqual(quiz1);
    })

    it('quizService.findById returns the random quiz ', () => {
        // Expected to find the random quiz
        expect(quizService.findById(quizRandom.id),
            `quizService.findById cannot find the quiz with id:[${quizRandom.id}]`)
            .toStrictEqual(quizRandom);
    })

    it('quizService.findById returns the last quiz ', () => {
        // Expected to find the last quiz
        expect(quizService.findById(quizLast.id),
            `quizService.findById cannot find the quiz with id:[${quizLast.id}]`)
            .toStrictEqual(quizLast);
    })
})

// !TEST: quizService.deleteById
describe('quizService.deleteById', () => {
    let quiz2, entitiesLength, quiz1

    beforeAll(() => {
        quizService = new InMemoryEntitiesService(30000, (id) => Quiz.createSample(id))

        // Getting the amount of entities
        entitiesLength = quizService.entities.length
        // Getting the first quiz
        quiz1 = quizService.entities[0]
        // Getting the second quiz when there are 2 or more rows of data
        if (entitiesLength >= 1) quiz2 = quizService.entities[1]
    });

    it('Entities length should be the same before any quiz is deleted', () => {
        // Expected that the length should be the same before any quiz is deleted
        expect(quizService.entities.length,
            `The length of quizService.entities does not contain the correct value`)
            .toStrictEqual(entitiesLength)
    });

    it('Should delete the quiz', () => {
        // Deletes the first quiz
        quizService.deleteById(quiz1.id)
    });

    it('The deleted quiz should not be found', () => {
        console.log(quizService.findById(quiz1.id))
        // Expected that quiz1 cannot be found
        expect(quizService.findById(quiz1.id),
            `quizService.deleteById did not delete the quiz with id-${quiz1.id}`)
            .toBeUndefined();
    });

    it('Entities length should decrease by 1 after the quiz was deleted', () => {
        // Expected that the length should be the same after quiz1 is deleted
        expect(quizService.entities.length,
            `The length of quizService.entities does not contain the correct value`)
            .toStrictEqual(entitiesLength - 1)
    });

    it('The second quiz should be the first quiz after the first quiz was deleted', () => {
        // Expected that the second quiz should be the first quiz after the first quiz was deleted
        expect(quizService.entities[0],
            `The second quiz should be the first quiz after the first quiz was deleted`)
            .toStrictEqual(quiz2)
    })
})

// !TEST: quizService.save
describe('quizService.save', () => {
    let entitiesLength, quiz

    // Runs a before any of the tests
    beforeAll(() => {
        // Getting the amount of entities
        entitiesLength = quizService.entities.length
        // Creating and storing the sample quiz
        quiz = Quiz.createSample(0)
    });

    it('Entities length should be the same before any quiz is saved ', () => {
        // Expected that the length should be the same before any quiz is created
        expect(quizService.entities.length,
            `The length of quizService.entities does not contain the correct value before any quiz was created`)
            .toStrictEqual(entitiesLength)
    });

    it('Should save the quiz', () => {
        // Saving the quiz
        quizService.save(quiz)
    });

    it('Entities length should be incremented by 1 after the quiz was saved', () => {
        // Expected that the length should be the same before any quiz is created
        expect(quizService.entities.length,
            `The length of quizService.entities does not contain the correct value after the quiz was created`)
            .toStrictEqual(entitiesLength + 1)
    });

    it('Should find the created quiz', () => {
        // Expected to find the created quiz
        expect(quizService.findById(quiz.id),
            `quizService.findById cannot find the created quiz with id:[${quiz.id}]`)
            .toStrictEqual(quiz);
    });
})
// npm run test:unit -t tests/unit/services/quiz/quizInMemoryService.spec.js