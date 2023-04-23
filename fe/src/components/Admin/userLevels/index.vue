<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Manage Played Levels
      </h2>
    </div>
  </header>


  <div class="w-4/5 mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <div class="flex justify-start mt-5">
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
                <div class="px-1 py-1 h-fit max-h-[370px] overflow-auto">
                  <MenuItem v-for="(page, index) in 111" :key="index">
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
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">User ID
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">User name
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Email
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Level ID
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Level
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="playedLevel in sort(playedLevelsPaginate)" :key="playedLevel.id"
              class="bg-white border-b transition duration-500 hover:bg-gray-200 ">
            <td class="py-4 px-6">
              {{ playedLevel.userId.id }}
            </td>
            <td class="py-4 px-6">
              {{ playedLevel.userId.firstName }} {{ playedLevel.userId.lastName }}
            </td>
            <td class="py-4 px-6">
              {{ playedLevel.userId.email }} {{ playedLevel.userId.lastName }}
            </td>
            <th scope="row" class="py-4 px-6">
              {{ playedLevel.id }}
            </th>
            <td class="py-4 px-6">
              {{ playedLevel.hasOwnProperty("quizId") ? 'Level 2 - Quiz' : 'Level 1 - Counting' }}
            </td>
            <td class="py-4 px-6">
              <div class="flex justify-evenly gap-5">
                <button @click="viewPlayedLevelData(playedLevel)"
                        class="px-5 py-2 border-blue-500 border text-blue-500 rounded transition duration-300
                               hover:bg-blue-500 hover:text-white focus:outline-none">
                  view
                </button>
                <button @click="handleDeleteButton(playedLevel)"
                        class="px-5 py-2 border-municipality-500 border text-municipality-500 rounded transition
                          duration-300 hover:bg-municipality-500 hover:text-white focus:outline-none">
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


  <div v-if="userLevelToDelete">
    <router-view :userLevel="userLevelToDelete" @deleteUserLevel="onDelete"></router-view>
  </div>

</template>

<script>
import {Menu, MenuButton, MenuItems, MenuItem} from '@headlessui/vue'
import Pagination from "@/assets/js/utils/pagination";
import {NOT_FOUND_CODE} from "@/assets/js/utils/httpStatusCodes";

export default {
  inject: ["usersService", "countingService", "quizResultService"],
  name: "index",
  components: {
    Menu,
    MenuButton,
    MenuItems,
    MenuItem
  },
  data() {
    return {
      playedLevelsData: [],
      playedLevelsPaginate: [],
      playedLevelsSplit: [],
      userLevelToDelete: null,
      newUser: null,
      deleteModal: false,
      serviceUnavailable: false,
      pageNumber: 1,
      userLevelRoutesDelete: [
        {level: 1, path: "/admin/played-levels/level/1/delete"},
        {level: 2, path: "/admin/played-levels/level/2/delete"}
      ],
    };
  },
  async created() {
    await this.loadData()

    this.pagination = new Pagination(this.playedLevelsData, 10)
    await this.paginateUserLevels()
  },
  methods: {
    /**
     * Sorts the array by user id
     * @param array The array to sort
     * @returns {Array|number} sorted array
     */
    sort(array) {
      if (array === undefined) return 0
      return array.sort((a, b) => {
        if (a.userId.id < b.userId.id) return -1
        if (a.userId.id > b.userId.id) return 1
        return 0
      })
    },

    /**
     * Redirects the user to the view page based on level
     * @param data Contains the row data from table
     */
    viewPlayedLevelData(data) {
      const userId = data.userId.id
      // eslint-disable-next-line no-prototype-builtins
      if (data.hasOwnProperty("quizId")) {
        const quizId = data.quizId.id
        this.$router.push({path: `/admin/played-levels/2/view`, query: {userId: userId, quizId: quizId}})
      } else {
        this.$router.push({path: `/admin/played-levels/1/view`, query: {countingId: data.id}})
      }
    },

    /**
     * Loads the quiz en counting data
     * @returns {Promise<void>}
     */
    async loadData() {
      try {
        await this.loadQuiz()
        await this.loadCounting()

      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Loads the quiz result data
     * @returns {Promise<void>}
     */
    async loadQuiz() {
      try {
        const quizResultResponse = await this.quizResultService.findAll()
        quizResultResponse.data.forEach(item => this.playedLevelsData.push(item))
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Loads the counting data
     * @returns {Promise<void>}
     */
    async loadCounting() {
      try {
        const countingResponse = await this.countingService.findAll()
        countingResponse.forEach(item => this.playedLevelsData.push(item))
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Deletes the played level based on level en resets data
     * @param userLevel
     * @returns {Promise<void>}
     */
    async onDelete(userLevel) {
      const isQuiz = 'quizId' in userLevel

      try {
        if (isQuiz) {
          // Deletes played quiz
          await this.deleteUserQuiz(userLevel)
        } else {
          // Deletes played counting level
          await this.deleteUserCounting(userLevel)
        }

        // Reload data
        await this.loadData()
        this.pagination.data = this.playedLevelsData
        this.pagination.splitAndPaginate()
        await this.paginateUserLevels()

        this.$router.push(this.$route.matched[0].path);
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Deletes the quiz results by id
     * @param userLevel The quiz result level to delete
     * @returns {Promise<void>}
     */
    async deleteUserQuiz(userLevel) {
      try {
        const response = await this.quizResultService.deleteById(userLevel.id)
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Deletes the played counting level by id
     * @param userLevel The played counting level to delete
     * @returns {Promise<void>}
     */
    async deleteUserCounting(userLevel) {
      try {
        const response = await this.countingService.deleteById(userLevel.id)
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Handles the delete button and changes the path when given object is not null to show the delete modal
     * @param userLevel The played level to delete
     */
    handleDeleteButton(userLevel) {
      if (userLevel != null) {
        this.userLevelToDelete = userLevel
        // Check if is a quiz level
        const isQuiz = 'quizId' in userLevel
        // Get route based on level
        const route = this.userLevelRoutesDelete.find(item => isQuiz ? item.level === 2 : item.level === 1)
        this.$router.push(`${route.path}/${userLevel.id}`)
      } else if (this.userLevelToDelete != null) {
        this.$router.push(this.$route.matched[0].path);
      }
    },

    /**
     * Async function paginates the user levels
     * @returns {Promise<void>}
     */
    async paginateUserLevels() {
      this.playedLevelsPaginate = this.pagination.dataPaginate
      this.playedLevelsSplit = this.pagination.dataSplit
    },

    /**
     * Gets the chunk from the spit array based on the selected page number
     * @param number The number of the selected page
     * @returns {Promise<void>}
     */
    async onPageSelect(number) {
      this.pageNumber = number + 1
      this.playedLevelsPaginate = this.pagination.onPageSelect(number)
    },
  }
}
</script>

<style scoped>

</style>