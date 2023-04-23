import createNew from "@/components/journal/CreateNew";
import {cardsService} from "@/services/cardsService";
import {mount} from '@vue/test-utils'
import {createMemoryHistory, createRouter} from "vue-router";
import {reactive} from "vue";
import {Card} from "@/models/Card";
import CreateNew from "@/components/journal/CreateNew";
import {UserInMemoryService} from "@/services/user/userInMemoryService";
import User from "@/models/User";


let wrapper;
let cardService;
let usersService;
let journalCard, journalCard1, journalCard2, journalCard3;
const BUTTON_LABEL = 'Create note';
const GO_BACK_LABEL = 'Go back';
const ID = 3;

const journalRoutes = [
    {path: '/profile/journal/createNew', component: createNew}
];

jest.mock('axios', () => jest.fn(() => Promise.resolve({
    status: 200,
})));

beforeEach(async function () {
    usersService = new UserInMemoryService(0, User.createSample);


    const router = createRouter({
        history: createMemoryHistory(),
        routes: journalRoutes,
    });

    journalCard = new Card(1, 1, "Level 1", "Nice")
    journalCard1 = new Card(2, 1, "Level 2", "Nice!")
    journalCard2 = new Card(ID, 1, "Level 3", "Nice!!")
    journalCard3 = Card.createJournalCard(ID + 1);

    cardService = new cardsService("http://localhost:8083/api + /journal");
    await cardService.addCard(journalCard);
    await cardService.addCard(journalCard1);
    await cardService.addCard(journalCard2);


    wrapper = mount(CreateNew, {
        global: {
            provide: {
                "cardsService": reactive(cardService),
                "usersService": reactive(usersService)
            },
            plugins: [router]

        }
    });
    await wrapper.vm.$router.isReady()
});

describe('CreateNew.vue', () => {
    it('loads content', function () {
        const TITLE_OF_PAGE = "Create new note";
        const title = wrapper.find('h2');

        //Checking if header texts is loaded
        expect(title.text(),
            `Title of page should contain: ${TITLE_OF_PAGE}`)
            .toMatch(TITLE_OF_PAGE)
    })

    it('load go back button', function () {
        //Instantiate button
        const button = wrapper.findAll('button').find(b => b.text() === GO_BACK_LABEL)

        //Checking if button exists and if it isn't disabled
        expect(button.exists(),
            `Cannot find button with label ${GO_BACK_LABEL}`)
            .toBe(true);
        expect(button.element.disabled,
            `button with label ${GO_BACK_LABEL} has been disabled`)
            .toBeFalsy();
    });

    it('load create button', function () {
        //Instantiate button
        const button = wrapper.findAll('button').find(b => b.text() === BUTTON_LABEL)

        //Checking if button exists and if it isn't disabled
        expect(button.exists(),
            `Cannot find button with label ${BUTTON_LABEL}`)
            .toBe(true);
        expect(button.element.disabled,
            `button with label ${BUTTON_LABEL} has been disabled`)
            .toBeFalsy();

    });
})

describe('cardsService.js', ()=> {
    it('find all cards', function () {
        //Getting all cards
        let results = cardService.findAll()

        //Checking that the entities of the result of the findAll() method are the same as all entities
        expect(results.entities, `findAll() method does not return all cards`).toStrictEqual(cardService.entities);
    });

    it('find all cards by user id', function () {
        //Making user
        let user = usersService.entities[0];

        //Checking if the same cards are shown with the same user id
        expect(cardService.findByUserId(user.id), `Cards are not found per user`)
            .toStrictEqual(cardService.findByUserId(0))
    });
})

describe('Card.js', function () {
    it('copyConstructor method works', function () {
        //Making copy of the journalcard1 object
        let copy = Card.copyConstructor(journalCard1);

        //Checking if the copy is the same as the original card
        expect(copy, `To be exact copy of journalcard1`).toStrictEqual(journalCard1)
    });
})