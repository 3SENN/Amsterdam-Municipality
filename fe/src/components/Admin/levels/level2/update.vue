<template>
  <!-- Component header -->
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Update level 2
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

  <div class="container max-w-4xl mx-auto py-12 px-3">
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
          <form @submit.prevent="onQuizUpdate">
            <div class="grid gap-6 mb-6 lg:grid-cols-2">
              <!-- Title input -->
              <div class="input-wrapper">
                <label for="title" class="mb-2 text-sm font-medium text-black-900 font-bold">Title</label>
                <input type="text"
                       v-model="quiz.title"
                       id="title"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                       required/>
              </div>
              <!-- Amount for registrations input -->
              <div class="input-wrapper">
                <label for="available-registrations" class="mb-2 text-sm font-medium text-black-900 font-bold">Amount for
                  registrations </label>
                <input type="number"
                       v-model="quiz.amountToRegister"
                       id="available-registrations"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                       required/>
              </div>
              <!-- Starts at input -->
              <div class="input-wrapper">
                <label for="starts-at" class="mb-2 text-sm font-medium text-black-900 font-bold">Starts at</label>
                <input type="datetime-local"
                       v-model="quiz.startsAt"
                       id="starts-at"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                       required/>
              </div>
              <!-- Ends at input -->
              <div class="input-wrapper">
                <label for="ends-at" class="mb-2 text-sm font-medium text-black-900 font-bold">Ends at</label>
                <input type="datetime-local"
                       v-model="quiz.endsAt"
                       id="ends-at"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                       required/>
              </div>
              <!-- Location input -->
              <div class="input-wrapper">
                <label for="starts-at" class="mb-2 text-sm font-medium text-black-900 font-bold">Location</label>
                <select
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                    v-model="quiz.location">
                  <option disabled selected>select location</option>
                  <option value="1">Online/digital</option>
                  <option value="2">Real life</option>
                </select>
              </div>
              <!-- Quiz location input -->
              <div class="input-wrapper">
                <!-- Link input -->
                <div v-if="quiz.location === '1'">
                  <label for="link" class="mb-2 text-sm font-medium text-black-900 font-bold">Link</label>
                  <input type="url"
                         v-model="quiz.link"
                         id="link"
                         class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                         required/>
                </div>
                <!-- Address input -->
                <div v-else>
                  <label for="address" class="mb-2 text-sm font-medium text-black-900 font-bold">Address</label>
                  <input type="text"
                         v-model="quiz.address"
                         id="address"
                         class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                         required/>
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
            <!-- Save button -->
            <button
                type="submit"
                class="mt-4 text-white bg-red-600 hover:bg-red-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 text-center float-right">
              Save
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>

  <!-- Questions displayed to show added question by user -->
  <div class="container flex justify-between  flex-col md:flex-row max-w-7xl gap-10 mx-auto py-12 px-3">
    <div class="bg-white h-fit shadow-xl sm:rounded-lg">
      <div class="p-10 relative">
        <div class="questions-box flex justify-between items-center">
          <h1 class="text-2xl font-bold my-6">Added questions:</h1>
        </div>
        <div v-for="question in questions" :key="question.id"
             class="flex justify-between bg-blue-100 rounded-lg p-4 mb-4 text-sm text-blue-700" role="alert">
          <span class="font-extrabold">{{ question.id }}</span>
          <span class="ml-4">{{ question.question }}</span>
          <button @click="onQuestionRemove(question)"
                  class="h-5 w-5 rounded flex items-center justify-center border text-white
                  duration-300 bg-red-400 hover:bg-red-500 focus:outline-none">
            -
          </button>
        </div>
      </div>
    </div>

    <!-- Questions overview where user the user can choose from to which questions to add -->
    <div class="bg-white shadow-xl sm:rounded-lg pb-4">
      <div class="p-10 overflow-x-auto relative">
        <div class="questions-box flex justify-between items-center">
          <h1 class="text-2xl font-bold my-6">Questions to choose: [{{ quiz.totalQuestions }}]</h1>
        </div>
        <!-- Table -->
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
              {{ question.points }}
            </td>
            <td class="py-4 px-6">
              <div class="flex justify-evenly gap-5">
                <button v-if="!questions.has(question)" @click="onQuestionAdd(question)"
                        class="px-5 py-2 border-blue-500 border text-blue-500 rounded transition
                            duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
                  Add
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
        <!-- Save button -->
        <button
            @click.prevent="onQuestionUpdate"
            class="text-white bg-red-600 hover:bg-red-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 mt-2 text-center float-right">
          Save
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Pagination from "@/assets/js/utils/pagination";

export default {
  inject: ["quizService"],
  name: "update",
  data() {
    return {
      pageNumber: 1,
      quiz: [],
      allQuestions: [],
      questionsPaginate: [],
      questionsSplit: [],
      questionsToDelete: [],
      questions: new Set(),
    }
  },
  watch: {
    /**
     * Looks for changes for the param id in the URL
     * @returns {Promise<void>}
     */
    async '$route.params.id'() {
      // Calling the function to load the level data
      await this.loadLevelData()
    }
  },
  async created() {
    // Calling the function to load the level data
    await this.loadLevelData()
    // Calling the function to load all the question
    await this.loadAllQuestions()

    const questions = this.allQuestions
    // Creating new pagination instance - questions as data and 5 rows for each chunk
    this.pagination = new Pagination(questions, 5)
    // Calling the function to paginate the questions
    await this.paginateQuestions()
  },
  methods: {
    /**
     * Async function loads the quiz data and the questions which belongs to the quiz
     * @returns {Promise<void>}
     */
    async loadLevelData() {
      // Calling function to load level data
      await this.loadLevel()
      // Calling function to load the questions for the level
      await this.loadQuestionsForLevel()
    },

    /**
     * Async function is called when user click save for the quiz
     * @returns {Promise<void>}
     */
    async onQuizUpdate() {
      try {
        // Making call to update the quiz
        const response = await this.quizService.updateQuiz(this.getParamID(), this.quiz)
        // After the update was successfully the user will be redirected to -> /admin/levels
        this.$router.push("/admin/levels")
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function is called when user clicks save for the questions
     * @returns {Promise<void>}
     */
    async onQuestionUpdate() {
      try {
        // questions is a set, for this reason the loop is required
        for (let value of this.questions.values()) {
          // Making call to add the questions to the quiz
          await this.quizService.addQuestionsToQuiz(this.getParamID(), value.id)
        }

        // questions is a set, for this reason the loop is required
        for (let questionsToDelete of this.questionsToDelete) {
          // Making call to remove the questions from the quiz
          await this.quizService.removeQuestionsFromQuiz(this.getParamID(), questionsToDelete.id)
        }

        // After the update was successfully the user will be redirected to -> /admin/levels
        this.$router.push("/admin/levels")
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function loads the data for the quiz
     * @returns {Promise<void>}
     */
    async loadLevel() {
      // Storing the value from the param id (e.g., ?id=2)
      const id = this.getParamID()
      // Stops the method if the id was not a number
      if (!parseInt(id)) return
      // Making call to get the quiz by id
      const response = await this.quizService.getQuizById(id)
      // Getting the data from the response and storing it
      this.quiz = response.data
    },

    /**
     * Async function loads the data for the questions which belongs to the quiz
     * @returns {Promise<void>}
     */
    async loadQuestionsForLevel() {
      // Storing the value from the param id (e.g., ?id=2)
      const id = this.getParamID()
      // Stops the method if the id was not a number
      if (!parseInt(id)) return

      try {
        // Making call to get the questions which belong to the quiz
        const response = await this.quizService.getQuestionsByQuizId(id)
        // Getting the data from the response and storing it
        response.data.forEach(item => this.questions.add(item))
        this.initialQuestions = response.data
      } catch (error) {
        // Prints error if anyy
        console.error(error)
      }
    },

    /**
     * Async function loads all the questions
     * @returns {Promise<void>}
     */
    async loadAllQuestions() {
      try {
        // Making call to get all the questions
        const response = await this.quizService.getAllQuestions()
        // Getting the data from the response and storing it
        let questions = response.data

        // Doesn't add the questions to the array if it already exists in the set
        for (let question of this.questions) {
          questions = questions.filter(item => {
            if (item.id !== question.id) return question
          })
        }
        this.allQuestions = questions
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function paginates the questions
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
     * Async function adds the question to the set
     * @param question The question to add to the set
     * @returns {Promise<void>}
     */
    async onQuestionAdd(question) {
      this.questions.add(question)
      // Removes the question to add from the array which holds the questions to delete
      this.questionsToDelete = this.questionsToDelete.filter(item => item.id !== question.id)

      // Reloads the data after adding the question to the set, which is done to keep the table data relevant
      await this.reloadData()
    },

    /**
     * Async function removes the question from the set
     * @param question The question to remove from the set
     * @returns {Promise<void>}
     */
    async onQuestionRemove(question) {
      // When the question to remove belonged to the initial questions (When the quiz was created), then the question
      // will be added to the array of the questions to delete
      for (let value of this.initialQuestions) {
        if (value.id === question.id) this.questionsToDelete.push(question)
      }

      // Deletes the question from the set
      this.questions.delete(question)

      // Reloads the data after removing the question from the set, which is done to keep the table data relevant
      await this.reloadData()
    },

    /**
     * Async function updates the paginated data to keep the data relevant
     * @returns {Promise<void>}
     */
    async reloadData() {
      await this.loadAllQuestions()
      this.pagination.data = this.allQuestions
      this.pagination.splitAndPaginate()
      await this.paginateQuestions()
    },

    /**
     * @returns {String} The id of the quiz
     */
    getParamID() {
      return this.$route.params.id
    },
  }
}
</script>