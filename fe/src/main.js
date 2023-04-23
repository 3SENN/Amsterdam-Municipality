import {createApp} from 'vue'
import {router} from '@/router'
import App from './App.vue'
import {createStore} from 'vuex'

import './assets/css/tailwind.css';

const store = createStore({
    state() {
        return {
            counting: {
                countingId: 0
            }
        }
    }
})

createApp(App).use(router).use(store).mount('#app')
