import {createMemoryHistory, createRouter} from "vue-router";
import {QuizResultService} from "@/services/quiz/result/quizResultService";
import {CountingService} from "@/services/countingService";
import {mount} from "@vue/test-utils";
import Leaderboards from "@/components/leaderboard/Leaderboards.vue";
import LeaderboardOverall from "@/components/leaderboard/Carousel.vue";
import LeaderboardLevel from "@/components/leaderboard/CarouselPerLevel.vue";
import Homepage from "@/components/home/Homepage";
import {reactive} from "vue";

let wrapper;

const leaderboardRoute = [
    {path: '/', component: Homepage},
    {path: '/leaderboards', component: Leaderboards, LeaderboardOverall, LeaderboardLevel},
]

let quizResultService
let countingService


beforeEach(async function () {
    const router = createRouter({
        history: createMemoryHistory(),
        routes: leaderboardRoute,
    });

    quizResultService = new QuizResultService("http://localhost:8083/api/quiz/result")
    countingService = new CountingService("http://localhost:8083/api/counting/highscores")

    wrapper = await mount(Leaderboards, {
        global: {
            provide: {
                "quizResultService": quizResultService,
                "countingService": countingService
            },
            mocks: {
                $route: {params: {id: "", message: ""}}
            },
            plugins: [router]
        }
    });
    await wrapper.vm.$router.isReady()
});

describe("Leaderboard", () => {
    it('loads content', async () => {
        const SECTION_ONE_TITLE_CONTENT = "Leaderboards";
        const title = wrapper.find('h1')
        expect(title.text(),
            `Header title should contain: ${SECTION_ONE_TITLE_CONTENT}`)
            .toMatch(SECTION_ONE_TITLE_CONTENT)
    })

    it('load carousel-overall', async () => {
        const carousel = wrapper.find('carousel')
        expect(carousel.exists(),"overall-carousel should exist");
    })

    it('load carousel-per-level', async () => {
        const carousel = wrapper.find('carousel-per-level')
        expect(carousel.exists(),"per-level-carousel should exist");
    })

    it('loader', async () => {
        const loader = wrapper.find('.loader')
        expect(loader.exists(),"loader should exist");
    })
})

describe("QuizResultsService.js", () => {
    // Source: https://gitlab.fdmci.hva.nl/se-ewa/unit-testing-workshop/-/blob/main/wk1.5-frontend-with-jest/src/services/books-service.spec.js

    it('constructs a proper quizResultService', async() => {
        expect(quizResultService.entities,
            'quizResultService was not set up properly')
            .not.toBeNull();
    })


    it('findAll returns all quizResults', async () => {
        let quizResults = quizResultService.findAll();
        expect(quizResults.entities,'quizResultService.findAll does not return all quizResults').toStrictEqual(quizResultService.entities);
    })
})
