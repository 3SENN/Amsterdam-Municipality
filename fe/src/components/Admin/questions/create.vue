<template>
  <!-- Component header -->
  <header class=" bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Create question
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


  <div class="w-4/5 mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <div class="questions-box flex justify-between items-center">
          <h1 class="text-2xl font-bold my-6">Question</h1>
        </div>
        <!-- Inputs -->
        <div>
          <!-- Question input -->
          <div class="question-wrapper">
            <div class="input-wrapper mb-2">
              <label class="mb-2 text-sm font-bold text-black-900 font-bold">
                Question
              </label>
              <input type="text"
                     v-model="question.question"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <!-- Question error displayed -->
              <span v-if="errors.question"
                    class="text-sm font-bold text-municipality-500 mb-4">{{ errors.question.message }}</span>
            </div>
            <!-- Info input -->
            <div class="input-wrapper">
              <label class="mb-2 text-sm font-bold text-black-900 font-bold">
                Info
              </label>
              <textarea
                  v-model="question.info"
                  class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-1 focus:outline-none focus:border-green-500">
              </textarea>
            </div>
            <!-- Block input -->
            <div class="input-wrapper mb-2">
              <label class="mb-2 text-sm font-bold text-black-900 font-bold">
                Block
              </label>
              <input type="number"
                     v-model="question.block"
                     class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
              />
              <!-- Block error displayed -->
              <span v-if="errors.block"
                    class="text-sm font-bold text-municipality-500 mb-4">{{ errors.block.message }}</span>
            </div>
            <!-- Has multiple answers checkbox -->
            <div class="input-wrapper flex flex-col items-baseline mt-2">
              <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Has multiple answers?</label>
              <input type="checkbox" v-model="question.hasMultipleAnswers"/>
              <!-- Has multiple answers error displayed -->
              <span v-if="errors.hasMultipleAnswers"
                    class="text-sm font-bold text-municipality-500 mb-4">{{ errors.hasMultipleAnswers.message }}</span>
            </div>
            <!-- Answers error displayed -->
            <div class="flex justify-between">
              <!-- Answers empty error displayed -->
               <span v-if="errors.answersEmpty"
                     class="text-sm font-bold text-municipality-500">{{ errors.answersEmpty.message }}</span>
              <!-- Answers length error displayed -->
              <span v-if="errors.answersLength"
                    class="text-sm font-bold text-municipality-500">{{ errors.answersLength.message }}</span>
            </div>

            <!-- Question options inputs -->
            <div class="grid gap-6 mb-6 mt-4 lg:grid-cols-2">
              <div v-for="(answer, index) in question.answers" :key="index" class="input-wrapper">
                <!-- Question option input -->
                <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Answer {{ index + 1 }}</label>
                <input type="text"
                       v-model="answer.option"
                       :id="'answer' + index"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                />
                <!-- Is answer checkbox -->
                <div class="input-wrapper flex flex-col items-baseline mt-2">
                  <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Is answer?</label>
                  <input type="checkbox" v-model="answer.isAnswer"/>
                </div>
              </div>
            </div>
            <div class="grid gap-6 mb-6 mt-4 lg:grid-cols-2">
              <!-- Points input -->
              <div class="input-wrapper">
                <label class="mb-2 text-sm font-medium text-gray-500 font-bold">Points</label>
                <input type="number"
                       v-model="question.points"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 focus:outline-none focus:border-green-500"
                />
                <!-- Points error displayed -->
                <span v-if="errors.points"
                      class="text-sm font-bold text-municipality-500 mb-4">{{ errors.points.message }}</span>
              </div>
            </div>
            <!-- Gray border -->
            <div class="w-full h-1 bg-gray-200 rounded-full mx-auto mt-4 mb-4"></div>
          </div>
        </div>
        <!-- Save button -->
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
  name: "create",
  data() {
    return {
      errors: {},
      question: {
        "question": "",
        "info": "",
        "points": 0,
        "block": 0,
        "hasMultipleAnswers": false,
        "answers": [
          {
            "option": "",
            "isAnswer": false
          },
          {
            "option": "",
            "isAnswer": false
          },
          {
            "option": "",
            "isAnswer": false
          },
          {
            "option": "",
            "isAnswer": false
          }
        ]
      },
    }
  },
  methods: {
    /**
     * Async function executed when users saves data
     * @returns {Promise<void>}
     */
    async onSubmit() {
      // Creating new question validator instance
      const validator = new QuestionValidator(this.question, this.question.answers)
      // Storing the errors
      this.errors = validator.errors
      // If there are errors available the function will not go further and stops
      if (Object.keys(this.errors).length !== 0) return

      try {
        // Calling the function to create the question
        await this.createQuestion()

        // Redirecting the user back to the overview when everything was ok
        this.$router.push("/admin/questions")
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function creates new question
     * @returns {Promise<void>}
     */
    async createQuestion() {
      try {
        // Splitting the JSON value. Extracting the answers and question data from `this.question`
        const {answers, ...rest} = this.question;
        // Making call to create a new question
        const response = await this.quizService.createNewQuestion(rest)
        // Getting the data from the response and storing it
        const data = response.data

        // Calling the function to create the question options when question was created
        await this.creatOptions(data.id, answers)
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * Async function to create the question options
     * @param id The id of the question
     * @param answers The answers to create
     * @returns {Promise<void>}
     */
    async creatOptions(id, answers) {
      try {
        // answers contains multiple answers, for that reason the loop is required
        for (let answer of answers) {
          // Making call to create new question options
          const response = await this.quizService.createNewQuizQuestionOption(id, answer)
        }
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },
  }
}
</script>
