import {mount} from '@vue/test-utils'
import Profile from "@/components/profiles/Profile.vue";

let wrapper;
beforeEach(() => {
    wrapper = mount(Profile);
})

describe('Profile.vue', () => {
    it('loads content', async () => {
        const SECTION_ONE_TITLE_CONTENT = "Profile information";
        const sectionOneTitle = wrapper.findAll('h1').find(x => x.text() === SECTION_ONE_TITLE_CONTENT)

        const SECTION_TWO_TITLE_CONTENT = "Change password";
        const sectionTwoTitle = wrapper.findAll('h1').find(x => x.text() === SECTION_TWO_TITLE_CONTENT);
        expect(sectionOneTitle.text(),
            `First title should contain: ${SECTION_ONE_TITLE_CONTENT}`)
            .toMatch(SECTION_ONE_TITLE_CONTENT)

        expect(sectionTwoTitle.text(),
            `Second title should contain: ${SECTION_TWO_TITLE_CONTENT}`)
            .toMatch(SECTION_TWO_TITLE_CONTENT)
    })

    it("should have save button for profile information", () => {
        const saveProfileButton = wrapper.find('.save-profile-button');

        expect(saveProfileButton.exists())
            .toBeTruthy()
    })

    it("should have save button for password", () => {
        const savePasswordButton = wrapper.find('.save-password-button');

        expect(savePasswordButton.exists())
            .toBeTruthy()
    })
})



