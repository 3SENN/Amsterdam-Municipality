<template>
  <!-- Component header -->
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Create level 2
      </h2>
      <!-- Go back button -->
      <button @click="$router.go(-1)"
              class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-blue-600 border text-white rounded transition duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M11.25 9l-3 3m0 0l3 3m-3-3h7.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
        Go back
      </button>
    </div>
  </header>

  <div class="container max-w-5xl mx-auto py-12 px-3">
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative my-4">
        <div class="box-header">
          <!-- Rounded level indicator -->
          <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
            <h1 class="text-3xl font-bold">2</h1>
          </div>
          <h3 class="text-3xl font-bold text-center mt-3">Level</h3>
          <h5 class="font-bold text-center level-subtitle">The Quiz</h5>
          <div class="w-60 h-1 bg-red-500 rounded-full mx-auto mt-2"></div>
        </div>

        <!-- Input values -->
        <div class="mt-4">
          <div class="grid gap-6 mb-6 lg:grid-cols-2">
            <!-- Title input -->
            <div class="input-wrapper">
              <label for="title" class="mb-2 text-sm font-medium text-black-900 font-bold">Title</label>
              <input type="text"
                     v-model="quiz.title"
                     id="title"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <!-- Title error displayed -->
              <span v-if="errors.title"
                    class="text-sm font-bold text-municipality-500">{{ errors.title.message }}</span>
            </div>
            <!-- Amount for registrations input -->
            <div class="input-wrapper">
              <label for="available-registrations" class="mb-2 text-sm font-medium text-black-900 font-bold">Amount for
                registrations</label>
              <input type="number"
                     v-model="quiz.amountToRegister"
                     id="available-registrations"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <!-- Amount for registrations input error displayed -->
              <span v-if="errors.amountToRegister"
                    class="text-sm font-bold text-municipality-500">{{ errors.amountToRegister.message }}</span>
            </div>
            <!-- Starts at input -->
            <div class="input-wrapper">
              <label for="starts-at" class="mb-2 text-sm font-medium text-black-900 font-bold">Starts at</label>
              <input type="datetime-local"
                     v-model="quiz.startsAt"
                     id="starts-at"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <!-- Starts at error displayed -->
              <span v-if="errors.startsAt"
                    class="text-sm font-bold text-municipality-500">{{ errors.startsAt.message }}</span>
            </div>
            <!-- Ends at input -->
            <div class="input-wrapper">
              <label for="ends-at" class="mb-2 text-sm font-medium text-black-900 font-bold">Ends at</label>
              <input type="datetime-local"
                     v-model="quiz.endsAt"
                     id="ends-at"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <!-- Ends at error displayed -->
              <span v-if="errors.endsAt"
                    class="text-sm font-bold text-municipality-500">{{ errors.endsAt.message }}</span>
            </div>
            <!-- Location input -->
            <div class="input-wrapper">
              <label for="starts-at" class="mb-2 text-sm font-medium text-black-900 font-bold">Location</label>
              <select @change="onSelection()"
                      class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500">
                <option disabled selected>select location</option>
                <option value="1">Online/digital</option>
                <option value="2">Real life</option>
              </select>
              <!-- Location error displayed -->
              <span v-if="errors.location"
                    class="text-sm font-bold text-municipality-500">{{ errors.location.message }}</span>
            </div>
            <!-- Quiz location input -->
            <div class="input-wrapper">
              <!-- Link input -->
              <div v-if="quiz.location === 1">
                <label for="link" class="mb-2 text-sm font-medium text-black-900 font-bold">Link</label>
                <input type="url"
                       v-model="quiz.link"
                       id="link"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                />
                <!-- Link error displayed -->
                <span v-if="errors.link"
                      class="text-sm font-bold text-municipality-500">{{ errors.link.message }}</span>
              </div>
              <!-- Address input -->
              <div v-else-if="quiz.location === 2">
                <label for="address" class="mb-2 text-sm font-medium text-black-900 font-bold">Address</label>
                <input type="text"
                       v-model="quiz.address"
                       id="address"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                />
                <!-- Address error displayed -->
                <span v-if="errors.address"
                      class="text-sm font-bold text-municipality-500">{{ errors.address.message }}</span>

              </div>
            </div>
            <!-- Total question input -->
            <div class="input-wrapper">
              <label for="total-questions" class="mb-2 text-sm font-medium text-black-900 font-bold">Total
                Questions</label>
              <input type="number"
                     v-model="quiz.totalQuestions"
                     id="total-questions"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                     required/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <div class="container flex justify-between flex-col md:flex-row max-w-7xl mx-auto py-12 px-3">
    <div class="bg-white h-fit shadow-xl sm:rounded-lg">
      <div class="p-10 relative">
        <div class="questions-box flex justify-between items-center">
          <h1 class="text-2xl font-bold my-6">Added questions:</h1>
        </div>
        <!-- Question error displayed -->
        <span v-if="errors.questions"
              class="text-sm font-bold text-municipality-500 mb-4">{{ errors.questions.message }}</span>

        <!-- Loops through the question and displays the added questions -->
        <div v-for="question in questions" :key="question.id"
             class="flex justify-between bg-blue-100 rounded-lg p-4 mb-4 text-sm text-blue-700" role="alert">
          <div>
            <span class="font-extrabold">{{ question.id }}</span>
            <span class="ml-4">{{ subString(question.question) }}</span>
          </div>
        </div>
      </div>
    </div>
    <div class="bg-white shadow-xl sm:rounded-lg pb-4">
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
                            class="group text-left mb-1 w-full rounded-md px-2 py-2 text-sm cursor-pointer hover:bg-gray-200">
                      {{ index + 1 }}
                    </button>
                  </MenuItem>
                </div>
              </MenuItems>
            </transition>
          </Menu>
        </div>
        <div class="questions-box flex justify-between items-center">
          <h1 class="text-2xl font-bold my-6">Questions to choose: [{{ quiz.totalQuestions }}]</h1>
        </div>
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
            <td class="py-4 px-6 flex items-center">
              <span>{{ subString(question.question) }}</span>
            </td>
            <td class="py-4 px-6">
              {{ question.points }}
            </td>
            <td class="py-4 px-6">
              <div class="flex justify-evenly gap-5">
                <button v-if="!questions.has(question)" @click="onQuestionAdd(question)"
                        class="px-5 py-2 border-blue-500 border text-blue-500 rounded transition
                            duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
                  Add
                </button>
                <button v-else @click="onQuestionRemove(question)"
                        class="px-5 py-2 border-red-500 border text-red-500 rounded transition
                            duration-300 hover:bg-red-500 hover:text-white focus:outline-none">
                  Remove
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <!-- Create button -->
        <button
            @click.prevent="onSubmit"
            class="text-white bg-red-600 hover:bg-red-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 mt-2 text-center float-right">
          create
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Quiz from "@/models/Quiz";
import timeHelper from "@/assets/js/utils/timeHelper";
import Pagination from "@/assets/js/utils/pagination";
import {Menu, MenuButton, MenuItem, MenuItems} from "@headlessui/vue";
import {QuizValidator} from "@/assets/js/validation/quizValidator";

export default {
  inject: ['quizService', 'authService'],
  name: "create",
  components: {
    Menu,
    MenuButton,
    MenuItems,
    MenuItem
  },
  data() {
    return {
      pageNumber: 1,
      quizId: undefined,
      isShowing: false,
      selectedQuestion: null,
      quiz: {
        title: "",
        amountToRegister: 0,
        startsAt: "",
        endsAt: "",
        location: undefined,
        totalQuestions: 0,
        link: "",
        address: ""
      },
      questionsList: [],
      questionsPaginate: [],
      questionsSplit: [],
      errors: {},
      questions: new Set(),
    }
  },
  async created() {
    // Getting the current user id
    this.userId = this.authService.getId()
    // Making call to get all questions
    await this.getAllQuestions()
    // Creating new pagination instance - questionsList as data and 5 rows for each chunk
    this.pagination = new Pagination(this.questionsList, 5)
    await this.paginateQuestions()
  },
  methods: {
    /**
     * Function is called when user submits
     * @returns {Promise<void>}
     */
    async onSubmit() {
      // resetting the errors display
      this.errors = {}
      // Making call to validate the inputs
      this.validate()
      // If there are errors available the function will not go further and stops
      if (Object.keys(this.errors).length !== 0) return

      try {
        await this.createQuiz()
        // Creates the question when the quiz id is defined
        if (this.quizId !== undefined) await this.createQuestion()

        // Redirecting the user back to the overview when everything was ok
        this.$router.push("/admin/levels")
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function creates a new quiz
     * @returns {Promise<void>}
     */
    async createQuiz() {
      try {
        const quiz = Quiz.copyConstructor(this.quiz)
        // Setting the .createdAt & .updatedAt values to the current datetime
        quiz.createdAt = quiz.updatedAt = timeHelper.getCurrentDatetime()

        // Calls the function to create a new quiz with the service
        const response = await this.quizService.createNewQuiz(this.userId, quiz)
        const data = response.data

        // Setting the returned quiz id to the variable
        this.quizId = data.id
      } catch (error) {
        // Prints error if any
        console.log(error)
      }
    },

    /**
     * Async function creates a new question
     * @returns {Promise<void>}
     */
    async createQuestion() {
      try {
        // the questions to add to the quiz are stored inside the set
        for (let question of this.questions) {
          // creates 2 variables, one for the questions options and the other for the question
          const {questionOptions, ...rest} = question;

          // Making call to add the questions to the quiz
          await this.quizService.addQuestionsToQuiz(this.quizId, rest.id)
        }
      } catch (error) {
        console.log(error)
      }
    },

    /**
     * Async gets all questions
     * @returns {Promise<void>}
     */
    async getAllQuestions() {
      try {
        // Making call to get all questions
        const response = await this.quizService.getAllQuestions()

        // Getting the data from the response and storing it
        this.questionsList = response.data
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Paginating the questions
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
     * Validates the quiz and questions
     */
    validate() {
      // Creates new quiz validator instance
      const validator = new QuizValidator(this.quiz)
      // Validating the questions
      validator.validateQuestions(this.quiz.totalQuestions, this.questions.size)
      // Storing the errors
      this.errors = validator.errors
    },

    /**
     * Selection function is called when user switches between location -> IRL & digital
     */
    onSelection() {
      this.quiz.location = parseInt(event.target.value)
      // Resetting the values
      this.quiz.link = ""
      this.quiz.address = ""
    },

    /**
     * Adds the question to the set
     * @param question The question object
     */
    onQuestionAdd(question) {
      this.questions.add(question)
    },

    /**
     * Removes the question from the set
     * @param question
     */
    onQuestionRemove(question) {
      this.questions.delete(question)
    },

    /**
     * Function returns the shorter version of the value
     * @param value The value to shorten
     * @returns {*|string}
     */
    subString(value) {
      if (value.length >= 30) return `${value.substr(0, 30)}...`
      return value
    }
  }
}
</script>