<template>
  <header class=" bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        View question
      </h2>
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


  <div class="max-w-5xl mx-auto py-12 sm:px-6 lg:px-8 content-box">
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-2 overflow-x-auto relative">
        <div class="flex flex-col items-center">
          <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
            <h1 class="text-3xl font-bold">{{ question.id }}</h1>
          </div>
          <p class="mb-4 mt-6 text-base text-center text-gray-900">
            <span class="font-bold">Block:</span> {{ question.block }}
          </p>
          <p
              v-if="question.info"
              class="mb-4 mt-6 text-base text-center text-gray-900">
            {{ question.info }}
          </p>
          <h4 class="mb-4 mt-2 text-2xl text-center font-bold text-gray-900">{{ question.question }}</h4>
          <div class="w-96 h-1 bg-red-500 rounded-full mx-auto"></div>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-6 mb-6">
          <div v-for="answer in question.questionOptions" :key="answer.id"
               class="text-center text-2xl text-white font-extrabold rounded p-12  ease-in-out duration-500"
               :class="answer.isAnswer ? 'bg-green-500 hover:bg-green-400'  : 'bg-red-500 hover:bg-red-400'">
            {{ answer.option }}
          </div>
        </div>
      </div>
    </div>
  </div>


</template>

<script>
export default {
  inject: ['quizService'],
  name: "show",
  data() {
    return {
      question: ""
    }
  },
  async created() {
    await this.getQuestionById()
  },
  methods: {
    /**
     * Async function gets the question by id (id = value from query e.g, ?id=2)
     * @returns {Promise<void>}
     */
    async getQuestionById() {
      // Storing the value from the param id (e.g., ?id=2)
      const id = this.getQueryID()

      // Only executes the code in if when it is a number
      if (parseInt(id)) {
        try {
          // Making call to get the question by id
          const response = await this.quizService.getQuestionById(id)
          // Getting the data from the response and storing it
          this.question = response.data
        } catch (error) {
          this.errors.push(error.message.message)
        }
      }
    },

    /**
     * @returns {String} the id of the question
     */
    getQueryID() {
      return this.$route.query.id
    }
  }
}
</script>

<style scoped>

</style>