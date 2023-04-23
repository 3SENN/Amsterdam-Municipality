<template>
  <div v-if="question !== null" class="container mx-auto max-w-4xl mt-14">
    <div v-if="isAdmin">
      <div class="w-4/5 mx-auto">
        <div class="flex flex-col m-2">
          <button
              v-if="currentBlock < nextBlock && !blockFeedback"
              @click="adminNextQuestion('block-pause')"
              class="mt-4 text-center ml-auto bg-orange-300 text-2xl text-white font-extrabold rounded p-2 hover:bg-orange-400 active:bg-orange-700 ease-in-out duration-500 w-52 mb-3">
            Block feedback
          </button>

          <button
              v-else
              @click="!limitReached() ?  adminNextQuestion('next') : endQuiz()"
              class="mt-4 text-center ml-auto bg-orange-300 text-2xl text-white font-extrabold rounded p-2 hover:bg-orange-400 active:bg-orange-700 ease-in-out duration-500 w-52 mb-3">
            {{ !limitReached() ? "Next" : "End" }}
          </button>

          <p class="text-white font-extrabold text-2xl bg-municipality-400 rounded text-center p-6">
            Question: {{ questionIndex + 1 }}/{{ questions.length }}: {{ question.question }}
          </p>
        </div>
      </div>
      <div class="grid grid-cols-2 md:grid-cols-4 gap-10 mt-10 p-6">
        <div v-for="user in users" :key="user.user.id"
             class="card flex bg-red-300 w-max justify-evenly items-center ml-10 p-2 rounded-lg">
          <img :src="user.user.profilePhoto" class="rounded-full mr-4" alt="profile_photo">
          <p class="name text-white font-bold text-xl">{{ user.user.firstName }}</p>
        </div>
      </div>
    </div>

    <div v-if="currentBlock < nextBlock">
      <div v-if="blockUsers.length === 2 && blockFeedback">
        <div
            class="players-wrapper flex justify-around items-center flex-wrap gap-10 gap-10 flex-col md:flex-row mb-16">
          <div class="flex items-start rounded-xl bg-white p-5 shadow-lg w-3/5 md:w-2/5 flex-wrap">
            <div class="flex h-16 w-16 items-center justify-center rounded-full">
              <img :src="blockUsers[0].user.profilePhoto" class="rounded-full" alt="photo">
            </div>
            <div class="ml-4">
              <h2 class="font-semibold text-xl">{{ blockUsers[0].user.firstName }} {{
                  blockUsers[0].user.lastName
                }}</h2>
              <p class="mt-2 text-sm text-gray-500">{{ blockUsers[0].points }}</p>

              <div v-if="isAdmin" class="mt-2 flex flex-wrap gap-1">
                <input type="number" v-model="player1BlockPoints"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-20 p-1 focus:outline-none focus:border-green-500">
                <button @click="assignPointsFromBlock(blockUsers[0].user.id, player1BlockPoints);player1BlockPoints = 0"
                        type="submit"
                        class="text-white bg-green-500 hover:bg-green-800 font-medium rounded-lg text-sm w-full sm:w-auto px-7 py-1 text-center">
                  Add points
                </button>
              </div>
            </div>
          </div>

          <div>
            <h1 class="font-extrabold text-5xl">VS</h1>
          </div>

          <div class="flex items-start rounded-xl bg-white p-5 shadow-lg w-3/5 md:w-2/5 flex-wrap">
            <div class="flex h-16 w-16 items-center justify-center rounded-full">
              <img :src="blockUsers[1].user.profilePhoto" class="rounded-full" alt="photo">
            </div>
            <div class="ml-4">
              <h2 class="font-semibold text-xl">{{ blockUsers[1].user.firstName }} {{
                  blockUsers[1].user.lastName
                }}</h2>
              <p class="mt-2 text-sm text-gray-500">{{ blockUsers[1].points }}</p>

              <div v-if="isAdmin" class="mt-2 flex flex-wrap gap-1">
                <input type="number" v-model="player2BlockPoints"
                       class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-20 p-1 focus:outline-none focus:border-green-500">
                <button @click="assignPointsFromBlock(blockUsers[1].user.id, player2BlockPoints);player2BlockPoints = 0"
                        type="submit"
                        class="text-white bg-green-500 hover:bg-green-800 font-medium rounded-lg text-sm w-full sm:w-auto px-7 py-1 text-center">
                  Add points
                </button>
              </div>
            </div>
          </div>

        </div>

        <div v-if="casusQuestions[currentBlock-1]" class="flex flex-col m-2 md:m-40 md:mt-6">
          <div v-for="(question, index) in casusQuestions[currentBlock-1].questions" :key="question.id"
               class="question flex items-center shadow-lg rounded-xl py-5 p-2 bg-white mb-4 mt-2 gap-5">
            <div class="w-fit">
              <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
                <h1 class="text-3xl font-bold">{{ index + 1 }}</h1>
              </div>
            </div>

            <div>
              <p class="text-xl text-center">{{ question.question }}</p>
            </div>
          </div>
        </div>
      </div>
      <div v-else>

      </div>
    </div>

    <div v-if="!isAdmin && !blockFeedback" class="quiz-wrapper">
      <div class="flex flex-col m-2">
        <div class="quiz-header">
          <p class="text-white text-xl bg-municipality-25 rounded text-center p-6 mb-5">
            {{ question.info }}
          </p>

          <p class="text-white font-extrabold text-2xl bg-municipality-400 rounded text-center p-6">
            Block {{ question.block}} | {{ question.question }}
          </p>
        </div>
        <div class="quiz-body m-3">
          <div class="relative">
            <img src="../../../assets/img/quiz_picture.png" alt="quiz-pic" class="rounded"/>
            <p class="absolute text-4xl font-extrabold text-white top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
              <!-- Notify player to wait until admin press next.-->
              {{ userAnswer != null || this.answered.length > 1 ? "Waiting for others" : "" }}</p>
          </div>
        </div>
      </div>

      <p v-if="question.hasMultipleAnswers" class="mt-2 font-bold">Has multiple answers</p>

      <div class="quiz-footer mx-2">
        <div v-if="userAnswer && userAnswer.isAnswer"
             class="relative p-6 mb-4 flex justify-around"
             :class="userAnswer && userAnswer.isAnswer ? 'bg-green-600' : 'bg-red-600'">
          <p class="text-xl text-white font-extrabold rounded text-center ">Correct</p>
          <p v-if="userAnswer && userAnswer.isAnswer" class="text-2xl text-white font-extrabold rounded text-center">
            Points: + {{ question.points }}</p>
        </div>
        <div class="grid grid-cols-2 gap-4 mb-6">
          <button v-for="(answer) in question.questionOptions" :key="answer.id"
                  class="bg-purple-400 text-2xl text-white font-extrabold rounded p-12 ease-in-out duration-500"
                  @click="onAnswerClick(answer, question.hasMultipleAnswers, $event)"
                  :disabled="!question.hasMultipleAnswers && userAnswer">
            {{ answer.option }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CasusQuestions from "@/assets/data/casus.json"

export default {
  name: "Quiz",
  inject: ["quizService", "quizResultService", "authService", "usersService"],
  data() {
    return {
      totalQuestions: 0,
      questionIndex: 0,
      currentBlock: 0,
      nextBlock: 0,
      question: null,
      userAnswer: null,
      scoreboardRedirect: null,
      blockFeedback: false,
      casusQuestions: CasusQuestions,
      users: [],
      blockUsers: [],
      player1BlockPoints: 0,
      player2BlockPoints: 0,
      allAnswered: [],
      questions: [],
      answered: [],
      isAdmin: this.authService.isAdmin(),
      roomId: this.$route.query.room,
      nextQuestionWS: new WebSocket(process.env.VUE_APP_SOCKET_URL + "/next-question?userId=" + this.authService.getId()),
      endQuizWS: new WebSocket(process.env.VUE_APP_SOCKET_URL + "/end?userId=" + this.authService.getId()),
      sendAnswerWS: new WebSocket(process.env.VUE_APP_SOCKET_URL + "/send-answer?userId=" + this.authService.getId()),
      allAnswers: {
        userId: this.authService.getId(),
        quizId: this.$route.query.room,
        data: []
      },
    }
  },
  async created() {
    await this.loadQuizData(this.roomId)
    await this.nextQuestionHandler();

    if (!this.isAdmin) await this.endQuizHandler()
    if (this.isAdmin) await this.receiveUserAnswersHandler()

    this.currentBlock = this.question.block
    // Incrementing by 1 to check if the next question block is new
    if (this.questions[this.questionIndex + 1]) {
      if (this.questions[this.questionIndex + 1]) {
        this.nextBlock = this.questions[this.questionIndex + 1].block
      }
    }
  },
  methods: {
    onAnswerClick(item, multipleAnswers, event) {
      if (item.isAnswer && !multipleAnswers) this.addUserAnswersToList(this.question.points)

      if (multipleAnswers) {
        event.target.style.backgroundColor = "grey"
        this.answered.push({item, event})
        if (this.answered.length > 1) {
          let totalCorrect = 0
          const totalAnswered = this.answered.length
          for (let answeredElement of this.answered) {
            const isAnswer = answeredElement.item.isAnswer
            answeredElement.event.target.style.backgroundColor = isAnswer ? "green" : "red"

            if (answeredElement.item.isAnswer) totalCorrect += 1

          }
          this.sendAnswerToAdmin(this.authService.getId(), this.answered)
          this.addUserAnswersToList(this.question.points / totalAnswered * totalCorrect)
        }
      } else {
        this.userAnswer = item;
        if (this.userAnswer.isAnswer) {
          event.target.style.backgroundColor = "green";
        } else {
          event.target.style.backgroundColor = "red";
        }
        this.sendAnswerToAdmin(this.authService.getId(), this.userAnswer)
      }
    },
    adminNextQuestion(event) {
      if (this.limitReached()) return;
      if (event === "next") {
        this.users = []
        this.questionIndex++
        this.nextQuestionWS.send(JSON.stringify(
            {
              "room": this.$route.query.room,
              "event": "next-question",
              "questionIndex": this.questionIndex
            }
        ));
        return;
      }

      this.nextQuestionWS.send(JSON.stringify(
          {
            "room": this.$route.query.room,
            "event": "block-pause",
            "users": this.allAnswered
          }
      ));
    },
    async nextQuestionHandler() {
      this.nextQuestionWS.onopen = () => {
        console.log("[Quiz Next Question] WebSocket connected...")
      }

      // Get all sessions redirect
      this.nextQuestionWS.onmessage = (message) => {
        const data = JSON.parse(message.data);
        const gameState = data.event;

        if (gameState === "next-question") {
          this.userAnswer = null
          this.question = this.questions[data.questionIndex];
          // Sets the current block
          this.currentBlock = this.question.block
          // Incrementing by 1 to check if the next question block is new
          if (this.questions[this.questionIndex + 1]) {
            if (this.questions[data.questionIndex + 1]) {
              this.nextBlock = this.questions[data.questionIndex + 1].block
            }
          }

          this.blockFeedback = false
        }

        // Set variable to true when the event send the event for block-pause
        if (gameState === "block-pause") {
          this.blockFeedback = true
          this.users = this.sort(data.users)
          this.blockUsers = this.users.slice(0, 2)
        }
      }
    },
    sort(array) {
      if (array === undefined) return
      return array.sort((a, b) => {
        if (a.points < b.points) return -1
        if (a.points > b.points) return 1
        return 0
      })
    },
    async endQuiz() {
      this.endQuizWS.send(JSON.stringify(
          {
            "room": this.$route.query.room,
            "event": "finish",
          }
      ));


      for (let allAnsweredElement of this.allAnswered) {
        console.log(allAnsweredElement)
        await this.addToResults(allAnsweredElement.user.id, allAnsweredElement.points)
      }
      // Added interval to prevent admin from seeing no users
      this.scoreboardRedirect = await setTimeout(
          function () {
            this.$router.push({path: '/levels/quiz/scoreboard', query: {quiz: this.roomId}})
          }.bind(this),
          2000
      );
    },
    async assignPointsFromBlock(userId, points) {
      try {
        this.sendAnswerWS.send(JSON.stringify(
            {
              "room": this.$route.query.room,
              "userId": userId,
              "event": "question-answered",
              "answer": {
                "id": Math.floor(Math.random() * -1000),
                "option": "points assigned from block",
                "isAnswer": true
              },
              "points": points
            }
        ));
      } catch (error) {
        console.error(error)
      }
    },
    async receiveUserAnswersHandler() {
      // Receiving messages from the users
      this.sendAnswerWS.onmessage = async (message) => {
        console.log()
        const data = JSON.parse(message.data);
        console.log(data)
        const response = await this.usersService.getUserById(data.userId);
        const user = response.data

        if (this.users.length === 0) this.users.push({user: user, answer: data.answer.option});
        // If the user id (object) was not found in the last, the code will add the incoming object in the array
        if (!this.users.some(x => x.user.id === parseInt(user.id))) this.users.push({
          user: user,
          answer: data.answer.option
        })

        if (data.answer.isAnswer) {
          if (this.allAnswered.length === 0) this.allAnswered.push({
            user: user,
            answer: data.answer,
            points: data.points
          });
          // If the user id (object) was not found in the last, the code will add the incoming object in the array
          if (!this.allAnswered.some(x => x.user.id === parseInt(user.id))) {
            this.allAnswered.push({
              user: user,
              answer: data.answer,
              points: data.points
            })
          } else {
            for (let allAnsweredElement of this.allAnswered) {
              if (allAnsweredElement.user.id === user.id) {
                allAnsweredElement.points += data.points
              }
            }
          }
        }
        console.log(this.allAnswered)
      }
    },
    async endQuizHandler() {
      this.endQuizWS.onmessage = async (message) => {
        const data = JSON.parse(message.data);

        if (data.event === "finish") {
          // let sum = parseInt(0);
          // console.log(this.allAnswers)
          // for (let answer of this.allAnswers.data) {
          //   console.log(answer);
          //   sum += parseInt(answer.points);
          // }
          //
          // await this.addToResults(sum)

          setTimeout(
              function () {
                this.$router.push({path: '/levels/quiz/scoreboard', query: {quiz: this.roomId}});
              }.bind(this),
              2000
          );

        }
      }
    },
    async addToResults(userId, score) {
      try {
        let json = {
          userId: userId,
          quizId: this.allAnswers.quizId,
          score: score
        }
        await this.quizResultService.addScore(json);
      } catch (error) {
        console.error(error)
      }
    },
    async loadQuizData(roomId) {
      try {
        const questions = await this.quizService.getQuestionsByQuizId(roomId);
        questions.data.forEach(item => this.questions.push(item))
        this.question = this.questions[0];
      } catch (error) {
        console.error(error);
      }
    },
    sendAnswerToAdmin(userId, answer) {
      this.sendAnswerWS.send(JSON.stringify(
          {
            "room": this.$route.query.room,
            "userId": userId,
            "event": "question-answered",
            "answer": answer,
            "points": this.question.points
          }
      ));
    },
    addUserAnswersToList(points) {
      this.allAnswers.data.push({
        questionId: this.question.id,
        points: points,
      })
    },
    limitReached() {
      if (this.questionIndex >= this.questions.length - 1) return true;
    }
  },
}
</script>

<style scoped>

</style>