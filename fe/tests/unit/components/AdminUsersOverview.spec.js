import {mount} from '@vue/test-utils'
import {createMemoryHistory, createRouter} from "vue-router";
import {reactive} from "vue";
import AdminUsersOverview from '@/components/Admin/users/index.vue';
import AdminRoutes from "../../data/admin/users/AdminRoutes.js"
import {InMemoryEntitiesService} from "@/services/in-memory-entities-service";
import User from "@/models/User";

// Importing the routes
const userRoutes = AdminRoutes.adminRoutes()

let wrapper
// Instance for testing
let usersService

// Runs before each test
beforeEach(async function () {
    const router = createRouter({
        history: createMemoryHistory(),
        routes: userRoutes
    })

    // usersService to be provided to the component
    usersService = new InMemoryEntitiesService(3000, (id) => User.createRandomSample(id))

    // mount method renders the full DOM including the child components of the parent component for the tests
    wrapper = await mount(AdminUsersOverview, {
        global: {
            // Pass properties for components to use in injection
            provide: {
                "usersService": reactive(usersService),
            },
            // plugin the router into the isolated wrapper
            plugins: [router]
        }
    });

    // Wait till router is ready
    await wrapper.vm.$router.isReady()

    usersService = new InMemoryEntitiesService(3000, (id) => User.createRandomSample(id))
    console.table(usersService.entities)
})


it('Header loaded correctly', async () => {
    const TITLE_CONTENT = "Manage Users"
    // Finds the header title
    const title = wrapper.find('h2')

    // The header title should exist
    expect(title.exists(),
        'Header title should exist')
        .toBe(true);

    // The header title should contain the text value of TITLE_CONTENT
    expect(title.text(),
        `Header title should contain: ${TITLE_CONTENT}`)
        .toMatch(TITLE_CONTENT)
})

describe("Table loaded correctly", () => {
    it('Table should exist', async () => {
        // Finds/Gets the user table
        const usersTable = wrapper.find('.users-table')

        // Expecting that the table exists
        expect(usersTable.exists(),
            `Users table should exists`)
            .toBe(true)
    })

    it('Table rows loaded correctly', async () => {
        // Finds/Gets the table body inside the table
        const tbody = wrapper.find('.users-table tbody');
        // Finds/Gets the table rows inside the table body
        const tableRows = tbody.findAll('tr')

        // Expecting that the table rows exists with data
        expect(tableRows.length,
            `Table rows should exists`)
            .toBeGreaterThan(0)

        // Looping through the table rows and checking if the IDs are set correctly
        tableRows.forEach((tr, rowIndex) => {
            const user = usersService.entities[rowIndex]
            const id = wrapper.find(`tbody tr:nth-of-type(${rowIndex + 1}) .id-data`).text()

            expect(user.id).toBe(parseInt(id))
        });
    })

})



// npm run test:unit -t tests/unit/components/AdminUsersOverview.spec.js