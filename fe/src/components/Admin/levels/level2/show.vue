<template>
  <!-- Component header -->
  <header class=" bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        View level: Level 2
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

  <div class="container max-w-3xl mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <div class="box-header">
          <!-- Rounded level indicator -->
          <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
            <h1 class="text-3xl font-bold">2</h1>
          </div>
          <h3 class="text-3xl font-bold text-center mt-3">Level</h3>
          <h5 class="font-bold text-center level-subtitle">{{ quiz.title }}</h5>
          <div class="w-60 h-1 bg-red-500 rounded-full mx-auto mt-2"></div>
        </div>

        <!-- Data -->
        <div class="box-content flex flex-wrap justify-center">
          <!-- Created by -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Created by</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quiz.createdBy.firstName}} {{ quiz.createdBy.lastName }}</p>
            </div>
          </div>
          <!-- Total amount of registrations -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Registrations</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quiz.amountToRegister }}</p>
            </div>
          </div>
          <!-- Starts at -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Starts at</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quiz.startsAt }}</p>
            </div>
          </div>
          <!-- Location (IRL/Digital) -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Location</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ parseInt(quiz.location) === 1 ? 'Online/digital' : 'Real-life' }}</p>
            </div>
          </div>
          <!-- Ends at -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Ends at</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quiz.endsAt }}</p>
            </div>
          </div>
          <!-- Location (link value/IRL value) -->
          <div class="location">
            <!-- link -->
            <div v-if="quiz.location === 'digital'" class="card rounded p-5 w-64 m-5">
              <div class="card-label-wrapper">
                <p class="card-label">Link</p>
              </div>
              <div class="card-data-wrapper">
                <p class="card-data font-semibold">
                  {{ quiz.link }}
                </p>
              </div>
            </div>
            <!-- Address -->
            <div v-else class="card rounded p-5 w-64 m-5">
              <div class="card-label-wrapper">
                <p class="card-label">Location Description</p>
              </div>
              <div class="card-data-wrapper">
                <p class="card-data font-semibold">
                  {{ quiz.address }}
                </p>
              </div>
            </div>
          </div>
          <!-- Questions -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Questions</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quiz.totalQuestions }}</p>
            </div>
          </div>
          <!-- Points -->
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Points to achieve</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">2500</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div v-if="errors.length > 0" class="container max-w-3xl mx-auto py-12 sm:px-6 lg:px-8">
    <!-- Shows alert when there are no questions available  -->
    <info-alert v-for="(error, index) in errors" @onClose="errors = []" category="Info" :message="error"
                :key="index"></info-alert>
  </div>

  <!-- Carousel for quiz questions -->
  <splide v-if="questions.length > 0" :options="primaryOptions" rel="primary"
          class="w-10/12 mx-auto">
    <splide-slide v-for="question in questions" :key="question.id">
      <div class="max-w-5xl mx-auto py-12 sm:px-6 lg:px-8 content-box">
        <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
          <div class="p-2 overflow-x-auto relative">
            <div class="flex flex-col items-center">
              <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
                <h1 class="text-3xl font-bold">{{ question.id }}</h1>
              </div>
              <p
                  v-if="question.info"
                  class="mb-4 mt-6 text-base text-center text-gray-900">
                {{ question.info }}
              </p>
              <h4 class="mb-4 mt-2 text-2xl text-center font-bold text-gray-900">{{ question.question }}</h4>
              <div class="w-96 h-1 bg-red-500 rounded-full mx-auto"></div>
            </div>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-6 mb-6">
              <div v-for="answer in shuffleArray(question.questionOptions)" :key="answer.id"
                   class="text-center text-2xl text-white font-extrabold rounded p-12  ease-in-out duration-500"
                   :class="answer.isAnswer ? 'bg-green-500 hover:bg-green-400'  : 'bg-red-500 hover:bg-red-400'">
                {{ answer.option }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </splide-slide>
  </splide>
</template>

<script>
import {Splide, SplideSlide} from '@splidejs/vue-splide';
import infoAlert from "@/components/includes/alerts/infoAlert";

export default {
  inject: ["quizService"],
  name: "show",
  components: {
    Splide,
    SplideSlide,
    infoAlert
  },
  data() {
    return {
      quiz: '',
      isOnline: true,
      questions: [],
      errors: [],
      primaryOptions: {
        type: "loop",
        pagination: true,
      }
    }
  },
  watch: {
    /**
     * Looks for changes for the param id in the URL
     * @returns {Promise<void>}
     */
    async '$route.params.id'() {
      // If changes in the param id, the code will load the level data again
      await this.loadLevelData()
    }
  },
  async created() {
    // Calling the function to load the level data
    await this.loadLevelData()
  },
  methods: {
    /**
     * Async function loads the quiz data and the questions which belongs to the quiz
     * @returns {Promise<void>}
     */
    async loadLevelData() {
      // Calling the function to load the level data
      await this.loadLevel()
      // Calling the function to load the questions for the level
      await this.loadQuestionsForLevel()
    },

    /**
     * Async function loads the quiz data by the param id
     * @returns {Promise<void>}
     */
    async loadLevel() {
      // Storing the value from the param id (e.g., ?id=2)
      const id = this.getParamID()

      // Only executes the code in if when it is a number
      if (parseInt(id)) {
        // Making call to get the quiz by id
        const response = await this.quizService.getQuizById(id)
        // Getting the data from the response and storing it
        this.quiz = response.data
      }
    },

    /**
     * Async function loads the questions data which belongs to the quiz
     * @returns {Promise<void>}
     */
    async loadQuestionsForLevel() {
      // Storing the value from the param id (e.g., ?id=2)
      const id = this.getParamID()

      // Only executes the code in if when it is a number
      if (parseInt(id)) {
        try {
          // Making call to get the questions which belongs to the quiz by id
          const response = await this.quizService.getQuestionsByQuizId(id)
          // Getting the data from the response and storing it
          this.questions = response.data
        } catch (error) {
          this.errors.push(error.message.message)
        }
      }
    },

    /**
     * Function shuffles the given array
     * @param array The array to shuffle
     * @returns {Array} The shuffled array
     */
    shuffleArray(array) {
      return array.sort((a, b) => 0.5 - Math.random());
    },

    /**
     * @returns {String} The quiz id
     */
    getParamID() {
      return this.$route.params.id
    }
  }
}
</script>

<style scoped>
@import '@splidejs/splide/dist/css/themes/splide-default.min.css';

.level-subtitle {
  color: #A3AED0;
}

.card {
  box-shadow: 0 18px 40px rgba(112, 144, 176, 0.12);
}

.card-label {
  font-size: 14px;
  color: #A3AED0;
}
</style>