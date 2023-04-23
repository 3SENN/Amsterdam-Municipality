import {shallowMount} from "@vue/test-utils";
import Counting from "@/components/levels/counting/Counting";
import {CountingService} from "@/services/countingService";
import {AuthService} from "@/services/auth/authService";
import Vuex from "vuex";

let wrapper;

let countingService;
let authService;

// Runs before each test
beforeEach( () => {
    // Creating services
    countingService = new CountingService("http://localhost:8083/api/counting");
    authService = new AuthService("http://localhost:8083/api/auth");

    // Mocking Vuex store
    const store = new Vuex.Store({
        state: {
            counting: {
                countingId: 0
            }
        }
    });

    wrapper = shallowMount(Counting, {
        global: {
            provide: {
                "countingService": countingService,
                "authService": authService,
            },
            mocks: {
               $store: store
            }
        },
    });
});

describe('Counting', () => {
    it('renders properly', () => {
        expect(wrapper.element.children.length,
            `Counting component starting with ${wrapper.element.tagName} has no child elements`)
            .toBeGreaterThan(0);
    })

    it('loads content', () => {
        const sectionOneTitleContent = "Route data";
        const sectionOneTitle = wrapper.findAll("h1").find(x => x.text() === sectionOneTitleContent);

        const sectionTwoTitleContent = "Counting";
        const sectionTwoTitle = wrapper.findAll("h1").find(x => x.text() === sectionTwoTitleContent);

        expect(sectionOneTitle.text(),
            `First title should contain: ${sectionOneTitleContent}`)
            .toMatch(sectionOneTitleContent);

        expect(sectionTwoTitle.text(),
            `Second title should contain: ${sectionTwoTitleContent}`)
            .toMatch(sectionTwoTitleContent);
    })

    it('should have save button for the route data', () => {
        const saveRouteDataButton = wrapper.find('.route-save-button');

        expect(saveRouteDataButton.exists,
            'Save button for the route data should exist').toBeTruthy();
    })

    it('should have add category button for counting', () => {
        const addCategoryButton = wrapper.find('.add-extra-category-button');

        expect(addCategoryButton.exists,
            'Add extra category button should exist').toBeTruthy();
    })

    it('should have done button for counting level', () => {
        const countingDoneButton = wrapper.find('.counting-done-button');

        expect(countingDoneButton.exists,
            'Counting done button should exist').toBeTruthy();
    })
})