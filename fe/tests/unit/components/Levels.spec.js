import Homepage from "@/components/home/Homepage";
import Levels from "@/components/levels/Levels";
import {createMemoryHistory, createRouter} from "vue-router";
import {mount} from "@vue/test-utils";
import {reactive} from "vue";
import {LevelsLockedService} from "@/services/levelsLockedService";
import {AuthService} from "@/services/auth/authService";
import mockAxios from 'jest-mock-axios';
import axios from "axios";

const levelRoutes = [
    {path: '/', component: Homepage},
    {path: '/levels', component: Levels}
]

const user = [
        {
            "id": 29,
            "userId": {
                "id": 28,
                "firstName": "senne",
                "lastName": "sen",
                "email": "senne@gmail.com",
                "profilePhoto": "https://ui-avatars.com/api/?name=senne+sen"
            },
            "levels": {
                "levelOne": false,
                "levelTwo": false,
                "levelThree": true,
                "levelFour": true,
                "levelFive": true,
                "levelSix": true
            }
        }
        ]
mockAxios.mockResponseFor({url: '"http://localhost:8083/api/levelsLocked"'}, {data: user});
mockAxios.mockResponseFor({url: 'http://localhost:8083/api/auth'}, {data: 25});

let wrapper;

beforeEach(async function () {
    const router = createRouter({
        history: createMemoryHistory(),
        routes: levelRoutes
    })
    mockAxios.mockResponseFor({url: '/get'}, {data: user});
    let levelsLockedService = new LevelsLockedService("http://localhost:8083/api" + "/levelsLocked")
    let auth = new AuthService("http://localhost:8083/api" + "/auth")
    wrapper = await mount(Levels, {
        global: {
            provide: {
                "levelsLockedService": reactive(levelsLockedService),
                "authService": reactive(auth),
            },
            mocks: {
                $route: {params: {id: "", message: ""}}
            },
            plugins: [router]
        }
    });

    await wrapper.vm.$router.isReady()
});

it('should ', function () {
    
}); 