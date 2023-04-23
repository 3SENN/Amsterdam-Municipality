<template>
  <!-- Component header -->
  <header class="bg-white shadow relative z-40">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Manage Levels
      </h2>
      <!-- Create event menu -->
      <Menu as="div" class="relative inline-block text-left">
        <div>
          <MenuButton
              class="inline-flex w-full justify-center items-center gap-2 rounded-md bg-green-500 px-4 py-2 text-sm font-medium text-white hover:bg-green-500 focus:outline-none focus-visible:ring-2 focus-visible:ring-white focus-visible:ring-opacity-75">
            Create event
            <svg width="12" height="7" viewBox="0 0 12 7" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path opacity="0.8" d="M1 1L6 6L11 1" stroke="white" stroke-width="1.4" stroke-linecap="round"
                    stroke-linejoin="round"/>
            </svg>

          </MenuButton>
        </div>

        <transition
            enter-active-class="transition duration-100 ease-out"
            enter-from-class="transform scale-95 opacity-0"
            enter-to-class="transform scale-100 opacity-100"
            leave-active-class="transition duration-75 ease-in"
            leave-from-class="transform scale-100 opacity-100"
            leave-to-class="transform scale-95 opacity-0"
        >
          <MenuItems
              class="absolute right-0 mt-2 w-56 origin-top-right divide-y divide-gray-100 rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
            <div class="px-1 py-1">
              <!-- Menu item for level 1 -->
              <MenuItem>
                 <span class="group flex w-full items-center rounded-md px-2 py-2 text-sm line-through">
                  Level 1
                </span>
              </MenuItem>
              <!-- Menu item for level 2 -->
              <MenuItem>
                <router-link
                    exact-active-class="bg-gray-500 text-white"
                    class="group flex w-full items-center rounded-md px-2 py-2 text-sm hover:bg-gray-100"
                    to="/admin/levels/level/2/create">
                  Level 2
                </router-link>
              </MenuItem>
              <!-- Menu item for level 3 -->
              <MenuItem>
                <span class="group flex w-full items-center rounded-md px-2 py-2 text-sm line-through">
                  Level 3
                </span>
              </MenuItem>
              <!-- Menu item for level 4 -->
              <MenuItem>
                <span class="group flex w-full items-center rounded-md px-2 py-2 text-sm line-through">
                  Level 4
                </span>
              </MenuItem>
              <!-- Menu item for level 5 -->
              <MenuItem>
                <span class="group flex w-full items-center rounded-md px-2 py-2 text-sm line-through">
                  Level 5
                </span>
              </MenuItem>
              <!-- Menu item for level 6 -->
              <MenuItem>
                <span class="group flex w-full items-center rounded-md px-2 py-2 text-sm line-through">
                  Level 6
                </span>
              </MenuItem>
            </div>
          </MenuItems>
        </transition>
      </Menu>
    </div>
  </header>

  <div class="max-w-7xl mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <!-- Dropdown to change table page -->
        <div class="flex justify-start mt-5">
          <!-- Dropdown (pagination) -->
          <Menu as="div" class="relative inline-block text-left">
            <div>
              <MenuButton
                  class="inline-flex w-full justify-center items-center gap-2 rounded-md bg-green-500 px-4 py-2 text-sm
                  font-medium text-white hover:bg-green-500 focus:outline-none focus-visible:ring-2 focus-visible:ring-white
                  focus-visible:ring-opacity-75">
                Select page
                <svg width="12" height="7" viewBox="0 0 12 7" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path opacity="0.8" d="M1 1L6 6L11 1" stroke="white" stroke-width="1.4" stroke-linecap="round"
                        stroke-linejoin="round"/>
                </svg>

              </MenuButton>
            </div>

            <transition
                enter-active-class="transition duration-100 ease-out"
                enter-from-class="transform scale-95 opacity-0"
                enter-to-class="transform scale-100 opacity-100"
                leave-active-class="transition duration-75 ease-in"
                leave-from-class="transform scale-100 opacity-100"
                leave-to-class="transform scale-95 opacity-0"
            >
              <MenuItems
                  class="absolute left-0 mt-2 w-56 origin-top-right divide-y divide-gray-100 rounded-md bg-white
                  shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                <div class="px-1 py-1 h-96 overflow-auto">
                  <MenuItem v-for="(page, index) in levelsSplit" :key="index">
                    <button @click="onPageSelect(index)"
                            :class="pageNumber === index+1 ? 'bg-green-200' : 'bg-white' "
                            class="group text-left w-full rounded-md px-2 py-2 text-sm cursor-pointer hover:bg-gray-200">
                      {{ index + 1 }}
                    </button>
                  </MenuItem>
                </div>
              </MenuItems>
            </transition>
          </Menu>
        </div>
        <table class="w-full text-sm text-left text-gray-500">
          <thead class="text-gray-700 uppercase">
          <tr>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">ID
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Level
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Name
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Starts at
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider"></th>
          </tr>
          </thead>
          <tbody v-for="level in levelsPaginate" :key="level.id">
          <tr class="bg-white border-b">
            <th scope="row" class="py-4 px-6">
              {{ level.id }}
            </th>
            <td class="py-4 px-6">
              {{ level.level }}
            </td>
            <td class="py-4 px-6">
              {{ level.title }}
            </td>
            <td class="py-4 px-6">
              <span v-if="level.level !== 1">
                    {{ new Date(level.startsAt).toLocaleString() }}
              </span>
              <span v-else
                    class="text-xs font-semibold inline-block py-1 px-2 uppercase rounded text-green-600 bg-green-200 uppercase last:mr-0 mr-1">
                Always available
              </span>
            </td>
            <td class="py-4 px-6">
              <div
                  class="flex justify-evenly gap-5">
                <button @click="onView(level.id, level.level)"
                        class="px-5 py-2 border-blue-500 border text-blue-500 rounded transition duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
                  view
                </button>
                <button v-if="level.level === 2" @click="onUpdate(level.id, level.level)"
                        class="px-5 py-2 border-yellow-500 border text-yellow-500 rounded transition duration-300 hover:bg-yellow-500 hover:text-white focus:outline-none">
                  update
                </button>
                <button v-if="level.level === 2" @click.prevent="handleDeleteButton(level)"
                        class="px-5 py-2 border-red-500 border text-red-500 rounded transition duration-300 hover:bg-red-500 hover:text-white focus:outline-none">
                  delete
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <div v-if="levelToDelete">
    <router-view :level="levelToDelete" @deleteLevel="onDelete"></router-view>
  </div>
</template>


<script>
import {Menu, MenuButton, MenuItems, MenuItem} from '@headlessui/vue'
import Pagination from "@/assets/js/utils/pagination";

export default {
  inject: ['quizService'],
  name: "index",
  components: {
    Menu,
    MenuButton,
    MenuItems,
    MenuItem
  },
  data() {
    return {
      pageNumber: 1,
      levelToDelete: null,
      DeleteModal: false,
      quizzes: [],
      levels: [],
      levelsPaginate: [],
      levelsSplit: [],
      levelRoutesView: [
        {level: 1, path: "/admin/levels/level/1/view"},
        {level: 2, path: "/admin/levels/level/2/view"}
      ],
      levelRoutesUpdate: [
        {level: 1, path: "/admin/levels/level/1/update"},
        {level: 2, path: "/admin/levels/level/2/update"}
      ],
      levelRoutesDelete: [
        {level: 1, path: "/admin/levels/level/1/delete"},
        {level: 2, path: "/admin/levels/level/2/delete"}
      ]
    };
  },
  async created() {
    await this.getAllQuizzes()

    this.pagination = new Pagination(this.levels, 10)
    await this.paginateLevels()
  },
  methods: {
    /**
     * Async function gets all the created quizzes
     * @returns {Promise<void>}
     */
    async getAllQuizzes() {
      // Level 1 is always available, for this reason it always adds it to the list
      this.addLevel1()

      try {
        const response = await this.quizService.findAll();
        const quizzes = response.data
        // Pushing each quiz row to the levels array
        for (let quiz of quizzes) {
          this.levels.push(quiz)
        }
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Function adds the level 1 data to the levels array
     */
    addLevel1() {
      this.levels.push({
        id: 1,
        level: 1,
        title: "Counting exercise",
        createdAt: new Date(2022, 11, 10, 12, 0, 0)
      })
    },

    /**
     * Async function is called when user wants to delete the level
     * @param level The level to delete
     * @returns {Promise<void>}
     */
    async onDelete(level) {
      try {
        await this.quizService.deleteQuizById(level.id)
        await this.getAllQuizzes()

        this.pagination.data = this.levels
        this.pagination.splitAndPaginate()
        await this.paginateLevels()
      } catch (error) {
        console.error(`Error occurred while deleting the level ${error}`)
      }
      // Router push will remove the delete-modal from the user view
      this.$router.push(this.$route.matched[0].path);
    },

    /**
     * Function is called when the view button is clicked in the table
     * and redirects the user to the relevant view page of the level.
     * @param {number} id The of the level object
     * @param {number} levelId The level value
     */
    onView(id, levelId) {
      const route = this.levelRoutesView.find((route) => route.level === parseInt(levelId))
      this.$router.push(`${route.path}/${id}`)
    },

    /**
     * Function is called when the update button is clicked in the table
     * and redirects the user to the relevant update page of the level.
     * @param {number} id The of the level object
     * @param {number} levelId The level value
     */
    onUpdate(id, levelId) {
      const route = this.levelRoutesUpdate.find((route) => route.level === parseInt(levelId))
      this.$router.push(`${route.path}/${id}`)
    },

    /**
     * Async function paginates the levels
     * @returns {Promise<void>}
     */
    async paginateLevels() {
      this.levelsPaginate = this.pagination.dataPaginate
      this.levelsSplit = this.pagination.dataSplit
    },

    /**
     * When the user clicks for another value in the dropdown, the right chunk will be returned from the split data
     *    array[0] -> 1..nRows, where 0 is the value clicked by user in dropdown
     *    array[1] -> 1..nRows, where 1 is the value clicked by user in dropdown
     * @param number The selected number from the dropdown
     * @returns {Promise<void>}
     */
    async onPageSelect(number) {
      this.pageNumber = number + 1
      this.levelsPaginate = this.pagination.onPageSelect(number)
    },

    /**
     * Handles the delete button when clicked by user
     * @param level The level to delete
     */
    handleDeleteButton(level) {
      if (level != null) {
        this.levelToDelete = level
        const route = this.levelRoutesDelete.find((route) => route.level === parseInt(level.level))
        this.$router.push(`${route.path}/${level.id}`)
      } else if (this.levelToDelete != null) {
        this.$router.push(this.$route.matched[0].path);
      }
    }
  }
}
</script>
