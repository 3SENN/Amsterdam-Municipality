<template>
  <header class=" bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Edit question
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

  <div class="w-4/5 mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <div>
          <div class="question-wrapper">
            <div class="input-wrapper">
              <label class="mb-2 text-sm font-bold text-black-900 font-bold">
                Question
              </label>
              <input type="text"
                     v-model="question.question"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <span v-if="errors.question"
                    class="text-sm font-bold text-municipality-500 mb-4">{{ errors.question.message }}</span>
            </div>
            <div class="input-wrapper">
              <label class="mb-2 text-sm font-bold text-black-900 font-bold">
                Info
              </label>
              <textarea
                  v-model="question.info"
                  class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-1 focus:outline-none focus:border-green-500">
            </textarea>
            </div>
            <div class="input-wrapper flex flex-col items-baseline mt-2">
              <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Has multiple answers?</label>
              <input type="checkbox" v-model="question.hasMultipleAnswers"/>
              <span v-if="errors.hasMultipleAnswers"
                    class="text-sm font-bold text-municipality-500 mb-4">{{ errors.hasMultipleAnswers.message }}</span>
            </div>
            <div class="flex justify-between">
               <span v-if="errors.answersEmpty"
                     class="text-sm font-bold text-municipality-500">{{ errors.answersEmpty.message }}</span>
              <span v-if="errors.answersLength"
                    class="text-sm font-bold text-municipality-500">{{ errors.answersLength.message }}</span>
            </div>
            <div class="grid gap-6 mb-6 mt-4 lg:grid-cols-2">
              <div v-for="(answer, index) in question.questionOptions" :key="index" class="input-wrapper">
                <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Answer {{ index }}</label>
                <input type="text"
                       v-model="answer.option"
                       :id="'answer' + index"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                       required/>
                <div class="input-wrapper flex flex-col items-baseline mt-2">
                  <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Is answer?</label>
                  <input type="checkbox" v-model="answer.isAnswer"/>
                </div>
              </div>
            </div>
            <div class="grid gap-6 mb-6 mt-4 lg:grid-cols-2">
              <div class="input-wrapper">
                <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Points</label>
                <input type="number"
                       v-model="question.points"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                />
                <span v-if="errors.points"
                      class="text-sm font-bold text-municipality-500 mb-4">{{ errors.points.message }}</span>
              </div>
            </div>
            <div class="w-full h-1 bg-gray-200 rounded-full mx-auto mt-4 mb-4"></div>
          </div>
        </div>
        <button
            @click.prevent="onSubmit"
            class="text-white bg-red-600 hover:bg-red-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 text-center float-right">
          Save
        </button>
      </div>
    </div>
  </div>
</template>

<script>

import {QuestionValidator} from "@/assets/js/validation/questionValidator";

export default {
  inject: ["quizService"],
  name: "update",
  data() {
    return {
      question: "",
      errors: {}
    }
  },
  async created() {
    await this.getQuestion()
  },
  methods: {
    /**
     * Async functions called when user clicks the submit button.
     * @returns {Promise<void>}
     */
    async onSubmit() {
      // Creates new question validator instance
      const validator = new QuestionValidator(this.question, this.question.questionOptions)
      // Storing the errors
      this.errors = validator.errors
      // If there are errors available the function will not go further and stops
      if (Object.keys(this.errors).length !== 0) return
      try {
        // Making call to update the question
        await this.updateQuestion()

        // Redirecting the user back to the overview when everything was ok
        this.$router.push("/admin/questions")
      } catch (error) {
        // Prints error
        console.error(error)
      }
    },

    /**
     * Async function gets the question to update by id (id = value from query e.g, ?id=2)
     * @returns {Promise<void>}
     */
    async getQuestion() {
      // Storing the value from the param id (e.g., ?id=2)
      const id = this.getParamId()
      // If the id is not a number the function will not go further and stops
      if (!parseInt(id)) return
      try {
        // Making call to get the question by id
        const response = await this.quizService.getQuestionById(id)
        // Getting the data from the response and storing it
        this.question = response.data
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function updates the question
     * @returns {Promise<void>}
     */
    async updateQuestion() {
      try {
        // creates 2 variables, one for the questions options and the other for the question
        const {questionOptions, ...rest} = this.question;

        // making call to update the question
        await this.quizService.updateQuestion(rest.id, rest)
        // Calling the function to update the question options
        await this.updateOptions(questionOptions)
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function updates the question options
     * @param answers Th answers to update
     * @returns {Promise<void>}
     */
    async updateOptions(answers) {
      try {
        // Loops through the answers array
        for (let answer of answers) {
          // Making call to update the question option
          await this.quizService.updateQuestionOption(answer.id, answer)
        }
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * @returns {String} the id of the question
     */
    getParamId() {
      return this.$route.params.id
    }

  }
}
</script>

<style scoped>

</style>