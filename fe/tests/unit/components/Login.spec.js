import Login from "@/components/auth/Login.vue";
import { mount } from '@vue/test-utils'
import { createMemoryHistory, createRouter } from 'vue-router'
import { reactive } from 'vue'



describe('Login component test', () => {
    let wrapper, router
    const history = createMemoryHistory()
    const routes = [
        { path: '/auth', component: Login }
    ]
    const state = reactive({
        email: '',
        password: '',
        loginMessage: ''
    })
    beforeEach(() => {
        router = createRouter({ history, routes })
        wrapper = mount(Login, {
            global: {
                provide: {
                    $router: router,
                    state
                }
            }
        })
    })

    test('should render', () => {
        expect(wrapper.exists()).toBeTruthy()
    })

    test('should have a link to the home page', () => {
        expect(wrapper.find('[to="/"]').exists()).toBeTruthy()
    })

    test('should have an email input', () => {
        expect(wrapper.find('#email').exists()).toBeTruthy()
    })

    test('should have a password input', () => {
        expect(wrapper.find('#password').exists()).toBeTruthy()
    })

    test('should have a "remember me" checkbox', () => {
        expect(wrapper.find('#remember-me').exists()).toBeTruthy()
    })

    test('should have a link to the lost password page', () => {
        expect(wrapper.find('[to="/lost-password"]').exists()).toBeTruthy()
    })

    test('should call "onLogin" method when the form is submitted', () => {
        const spy = jest.spyOn(wrapper.vm, 'onLogin')
        wrapper.find('form').trigger('submit')
        expect(spy).toHaveBeenCalled()
    })
})

















