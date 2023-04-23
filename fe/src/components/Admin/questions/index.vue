<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Manage Questions
      </h2>
      <!-- Create new user link -->
      <router-link to="/admin/questions/create"
                   class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-green-600 border text-white rounded transition
                   duration-300 hover:bg-green-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
        </svg>
        Create question
      </router-link>
    </div>
  </header>

  <div class="w-4/5 mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <!-- Dropdown to change table page -->
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
                <div class="px-1 py-1 h-96 overflow-auto">
                  <MenuItem v-for="(page, index) in questionsSplit" :key="index">
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
        <!-- Questions table -->
        <table class="w-full text-sm text-left text-gray-500">
          <thead class="text-gray-700 uppercase">
          <tr>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">ID
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Question
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Block
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Info
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Points
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="question in questionsPaginate" :key="question.id"
              class="bg-white border-b transition duration-500 hover:bg-gray-200 ">
            <th scope="row" class="py-4 px-6">
              {{ question.id }}
            </th>
            <td class="py-4 px-6">
              {{ question.question }}
            </td>
            <td class="py-4 px-6">
              {{ question.block }}
            </td>
            <td class="py-4 px-6">
              <span v-if="question.info">
                 {{ subString(question.info) }}
              </span>
              <span v-else
                    class="text-xs font-semibold inline-block py-1 px-2 uppercase rounded text-red-600 bg-red-200 uppercase last:mr-0 mr-1">
                No Info
              </span>
            </td>
            <td class="py-4 px-6">
              {{ question.points }}
            </td>
            <td class="py-4 px-6">
              <div class="flex justify-evenly gap-5">
                <router-link :to="{path:'/admin/questions/view', query:{id: question.id}}"
                             class="px-5 py-2 border-blue-500 border text-blue-500 rounded transition duration-300
                                         hover:bg-blue-500 hover:text-white focus:outline-none">
                  view
                </router-link>
                <router-link :to="'/admin/questions/update/' + question.id"
                             class="px-5 py-2 border-yellow-500 border text-yellow-500 rounded transition duration-300
                                          hover:bg-yellow-500 hover:text-white focus:outline-none">
                  update
                </router-link>
                <button
                    @click.prevent="handleDeleteButton(question)"
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

  <div v-if="questionToDelete">
    <router-view :question="questionToDelete" @deleteQuestion="onDelete"></router-view>
  </div>

</template>


<script>
import Pagination from "@/assets/js/utils/pagination";
import {Menu, MenuButton, MenuItem, MenuItems} from "@headlessui/vue";

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
      questionToDelete: null,
      questions: [],
      questionsPaginate: [],
      questionsSplit: [],
      pageNumber: 1
    }
  },
  async created() {
    try {
      // Call to get all the questions
      await this.getAllQuestions()

      // Creating new pagination instance - questions as data and 5 rows for each chunk
      this.pagination = new Pagination(this.questions, 5)
      // Calling the function to paginate the questions
      await this.paginateQuestions()
    } catch (error) {
      // Prints error if any
      console.error(error)
    }

  },
  methods: {
    /**
     * Async function gets all the questions
     * @returns {Promise<void>}
     */
    async getAllQuestions() {
      try {
        // Making call to get all the questions
        const response = await this.quizService.getAllQuestions()
        // Getting the data from the response and storing it
        this.questions = response.data
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function deletes questions by id and reloads the question data
     * @param question The question to delete
     * @returns {Promise<void>}
     */
    async onDelete(question) {
      try {
        // Making call to delete the question by id
        await this.quizService.deleteQuestionById(question.id)
        // Calling function to get all the questions
        await this.getAllQuestions()

        this.pagination.data = this.questions
        this.pagination.splitAndPaginate()
        await this.paginateQuestions()
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
      // Resets the URL - Removes the child modal
      this.$router.push(this.$route.matched[0].path);
    },

    /**
     * Async function paginates the levels
     * @returns {Promise<void>}
     */
    async paginateQuestions() {
      this.questionsPaginate = this.pagination.dataPaginate
      this.questionsSplit = this.pagination.dataSplit
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
      this.questionsPaginate = this.pagination.onPageSelect(number)
    },


    /**
     * Handles the delete button when clicked by user
     * @param question The question to delete
     */
    handleDeleteButton(question) {
      if (question != null) {
        this.questionToDelete = question
        this.$router.push(`/admin/questions/delete/${question.id}`)
      } else if (this.userToDelete != null) {
        this.$router.push(this.$route.matched[0].path);
      }
    },

    /**
     * Function returns a part of the given string
     * @param value The value to shorten
     * @returns {*|string}  a part of the given string
     */
    subString(value) {
      if (value.length >= 15) return `${value.substr(0, 15)}...`
      return value
    }
  }
}
</script>

<style scoped>

</style>