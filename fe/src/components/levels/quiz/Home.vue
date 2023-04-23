<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap justify-between items-center sm:px-6 lg:px-8">
      <h2 class="text-xl text-gray-800 leading-tight">Quiz</h2>
    </div>
  </header>

  <div class="container mx-auto p-4">
    <!--    <div v-if="messages.length > 0" class="messages-wrapper">-->
    <!--      <danger-alert v-for="(message, index) in messages" category="message" :message="message" :key="index"-->
    <!--                    @onClose="closeAlert(index)"></danger-alert>-->
    <!--    </div>-->

    <div class="flex justify-between flex-col md:flex-row">
      <div class="left">
        <!--        <h1 class="text-3xl mt-3 mb-3">Join by quiz code</h1>-->
        <!--        <div class="flex justify-between bg-gray-50 rounded mb-12">-->
        <!--          <label for="join-quiz" class="sr-only">Label</label>-->
        <!--          <div class="flex justify-between p-3 gap-2">-->
        <!--            <input type="text" id="join-quiz" name="join-quiz"-->
        <!--                   class="py-3 px-4 bg-blue-50 font-bold rounded-2xl text-lg focus:outline-0"-->
        <!--                   placeholder="Join by code" v-model="roomId">-->
        <!--            <button @click="handleJoinButton"-->
        <!--                    class="flex items-center justify-center h-13 w-16 rounded-lg border border-transparent bg-municipality-400 text-white hover:bg-municipality-700 text-lg">-->
        <!--              Join-->
        <!--            </button>-->
        <!--          </div>-->
        <!--        </div>-->

        <div class="registered" v-if="registeredEvents.length > 0">
          <p class="title">Registered</p>
          <div class="grid grid-cols-1">
            <div v-for="event in registeredEvents" :key="event.id">
              <events-card
                  :id=event.quiz.data.id
                  :level=event.quiz.data.level
                  :ended="isEnded(event.quiz.data.id)"
                  :starts-at=event.quiz.data.startsAt
                  :title=event.quiz.data.title
                  :is-registered="isRegistered"
                  :registered="event.registered"
                  :max="event.max"
                  lobby=1
                  @leaveEvent="handleLeave(event.id)"
                  @joinLobby="handleJoinLobby(event.quiz.data.id)"/>
            </div>

          </div>
        </div>

        <div class="registered" v-else>
          <p class="title">Registered</p>
          <div class="grid grid-cols-1">
            <p>No quizzes registered</p>
          </div>
        </div>
      </div>

      <div class="right md:w-7/12" v-if="events.length > 0">
        <div class="upcoming-events">
          <p class="title">Upcoming</p>
          <hr/>
          <div class="grid grid-cols-1 md:grid-cols-2 md:gap-4">
            <div v-for="event in events" :key="event.id">
              <events-card
                  :id=event.id
                  :level=event.level
                  lobby=1
                  :ended="isEnded(event.id)"
                  :starts-at=event.startsAt
                  :title=event.title
                  :is-registered="!isRegistered"
                  :registered="event.registered"
                  :max="event.amountToRegister"
                  @registerEvent="handleRegister(event.id)"/>
            </div>
          </div>
        </div>
      </div>
      <div v-else class="right md:w-7/12">
        <div class="upcoming-events">
          <p class="title">Upcoming</p>
          <div class="grid grid-cols-1">
            <p>No quizzes upcoming</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import EventsCard from "@/components/events/EventsCard";

export default {
  inject: ["quizService", "eventService", "authService", "quizResultService"],
  name: "Quiz Lobby",
  components: {EventsCard},
  data() {
    return {
      roomId: null,
      isRegistered: true,
      events: [],
      userEvents: [],
      registeredEvents: [],
      messages: [],
      quizResults: [],
      availableRooms: [],
      userId: this.authService.getId(),
    }
  },
  async created() {
    await this.loadData();
  },
  methods: {
    async handleRegister(id) {
      try {
        const response = await this.eventService.addToEvent({
          quiz: id,
          user: this.userId
        })
        await this.clearEvents()
        await this.loadData()
      } catch (error) {
        console.error(error)
      }
    },
    async handleJoinLobby(id) {
      this.$router.push(`/levels/quiz/lobby?id=${id}`);
    },
    async retrieveQuizRooms() {
      try {
        const allEvents = await this.eventService.getAllEvents();
        const eventData = allEvents.data;
        for (let i = 0; i < eventData.length; i++) {
          const room = eventData[i].quiz.id;
          if (!this.availableRooms.includes(room)) this.availableRooms.push(room);
        }
      } catch (error) {
        console.error(error);
      }
    },
    async handleJoinButton() {
      const response = await this.eventService.getAllEventsByUserId(this.userId);

      const userEvent = response.data;
      for (let i = 0; i < userEvent.length; i++) {
        const room = userEvent[i].quiz.id;

        if (userEvent[i].user.id === parseInt(this.userId) && room === parseInt(this.roomId)) {
          this.$router.push(`/levels/quiz/lobby?id=${room}`);
        } else {
          this.messages.push("You can't join this room, you can only join rooms when you are registered to that specific event or quiz.")
        }
      }
    },
    async handleLeave(id) {
      try {
        await this.eventService.deleteEvent(id)
        await this.clearEvents()
        await this.loadData()
      } catch (error) {
        console.error(error)
      }
    },
    async loadData() {
      await this.getEventByUserId();
      await this.getAllQuizzes();
      await this.retrieveQuizRooms();
      await this.getCountParticipantsForAllQuizzes()
      await this.loadAllQuizResults()
    },
    async getEventByUserId() {
      try {
        const response = await this.eventService.getAllEventsByUserId(this.userId);
        const events = response.data;

        events.forEach((event) => this.userEvents.push(event))
      } catch (error) {
        console.error(error);
      }
    },
    async getAllQuizzes() {
      try {
        const response = await this.quizService.getQuizzesWhereUserIsNotSignedUpFor(this.userId);
        const quizzes = response.data
        quizzes.forEach((quiz) => this.events.push(quiz));
      } catch (error) {
        console.error(error)
      }
    },
    async getCountParticipantsForAllQuizzes() {
      try {
        const response = await this.eventService.getCountParticipantsForAllQuizzes()
        const data = response.data

        this.registeredEvents = this.userEvents.map(t1 => ({...t1, ...data.find(t2 => t2.quizId === t1.quiz.data.id)}))
        this.events = this.events.map(t1 => ({...t1, ...data.find(t2 => t2.quizId === t1.id)}))
      } catch (error) {
        console.error(error)
      }
    },
    async loadAllQuizResults() {
      try {
        const response = await this.quizResultService.findAllResults()
        const data = response.data

        data.forEach(item => this.quizResults.push(item.quizId))
      } catch (error) {
        console.error(error)
      }
    },
    isEnded(id) {
      let exists = false
      for (let quizResult of this.quizResults) {
        if (quizResult.id === id) exists = true
      }
      return exists
    },
    async clearEvents() {
      this.events = [];
      this.userEvents = [];
      this.registeredEvents = [];
    },
    closeAlert(index) {
      this.messages = this.messages.filter((element, i) => {
        return index !== i
      })
    }
  }
}
</script>

<style scoped>
.title {
  @apply mt-3 mb-3 text-3xl;
}
</style>