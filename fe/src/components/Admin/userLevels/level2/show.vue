<template>
  <header class=" bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        View user data: {{ quizData.userId.firstName }}  {{ quizData.userId.lastName }}
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

  <div class="container max-w-3xl mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <div class="box-header">
          <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
            <h1 class="text-3xl font-bold">2</h1>
          </div>
          <h3 class="text-3xl font-bold text-center mt-3">Level</h3>
          <h5 class="font-bold text-center level-subtitle">{{ quizData.quizId.title }}</h5>
          <div class="w-60 h-1 bg-red-500 rounded-full mx-auto mt-2"></div>
        </div>

        <!-- Input values -->
        <div class="box-content flex flex-wrap justify-center">
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Registrations</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quizData.quizId.amountToRegister }}</p>
            </div>
          </div>
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Started at</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quizData.quizId.startsAt }}</p>
            </div>
          </div>
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Location</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quizData.quizId.location }}</p>
            </div>
          </div>
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Ended at</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quizData.quizId.endsAt }}</p>
            </div>
          </div>
          <div class="location">
            <div v-if="quizData.quizId.location === 'Digital'" class="card rounded p-5 w-64 m-5">
              <div class="card-label-wrapper">
                <p class="card-label">Link</p>
              </div>
              <div class="card-data-wrapper">
                <p class="card-data font-semibold">
                  {{ quizData.quizId.link }}
                </p>
              </div>
            </div>
            <div v-else class="card rounded p-5 w-64 m-5">
              <div class="card-label-wrapper">
                <p class="card-label">Location Description</p>
              </div>
              <div class="card-data-wrapper">
                <p class="card-data font-semibold">
                  {{ quizData.quizId.address }}
                </p>
              </div>
            </div>
          </div>
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Questions</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quizData.quizId.totalQuestions }}</p>
            </div>
          </div>
          <div class="card rounded p-5 w-64 m-5">
            <div class="card-label-wrapper">
              <p class="card-label">Points Achieved</p>
            </div>
            <div class="card-data-wrapper">
              <p class="card-data font-semibold">{{ quizData.score }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- Carousel for quiz questions -->
  <splide :options="primaryOptions" rel="primary" class="w-10/12 mx-auto">
    <splide-slide v-for="question in questions" :key="question.id">
      <div class="max-w-5xl mx-auto py-12 sm:px-6 lg:px-8 content-box">
        <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
          <div class="p-10 overflow-x-auto relative">
            <div class="flex flex-col items-center">
              <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
                <h1 class="text-3xl font-bold">{{ question.id }}</h1>
              </div>
              <h4 class="mb-4 mt-2 text-2xl text-center font-bold text-gray-900">{{ question.question }}</h4>
              <div class="w-96 h-1 bg-red-500 rounded-full mx-auto"></div>
              <h5 class="mb-4 mt-6 text-xl text-gray-900">
                Answer given by User:
                <span
                    class="font-semibold inline-block py-1 px-2  rounded  last:mr-0 mr-1"
                    :class="question.answers[question.answered].isAnswer ? 'text-green-600 bg-green-200' : 'text-red-600 bg-red-200' ">
                {{ question.answers[question.answered].option }}
              </span>
              </h5>
            </div>
            <h5 class="mb-4 mt-2 text-xl text-gray-900">
              Correct Answer:
            </h5>
            <div class="grid grid-cols-2 gap-4 mb-6">
              <div v-for="answer in question.answers" :key="answer.id"
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
import QuizData from '@/assets/data/quiz.json'

export default {
  inject: ["quizResultService"],
  name: "view",
  components: {
    Splide,
    SplideSlide,
  },
  data() {
    return {
      questions: QuizData,
      quizData: "",
      primaryOptions: {
        type: "loop",
        pagination: true,
      }
    }
  },
  async created() {
    // Calling the function to load the level data
    await this.loadData()
  },
  methods: {
    async loadData() {
      try {
        // Making call to get the quiz result by user id and quiz id
        const response = await this.quizResultService.getByUserIdAndQuizId(this.getQueryUserId(), this.getQueryQuizId())
        // Getting the data from the response and storing it
        this.quizData = response.data
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },

    /**
     * @returns {string} the user id
     */
    getQueryUserId() {
      return this.$route.query.userId
    },

    /**
     * @returns {string} the quiz id
     */
    getQueryQuizId() {
      return this.$route.query.quizId
    }
  }
}
</script>

<style scoped>
@import '@splidejs/splide/dist/css/themes/splide-default.min.css';
.card {
  box-shadow: 0 18px 40px rgba(112, 144, 176, 0.12);
}

.card-label {
  font-size: 14px;
  color: #A3AED0;
}

</style>