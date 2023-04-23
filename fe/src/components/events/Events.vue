<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap justify-between items-center sm:px-6 lg:px-8">
      <h2 class="text-3xl text-gray-800 leading-tight">
        Events - Level
      </h2>
    </div>
  </header>

  <div class="container mx-auto p-4">
    <div class="registered">
      <p class="text-3xl mt-3 mb-3">Registered Events</p>
      <hr/>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-x-32">
        <AgendaCard v-for="event in userEvents" :key=event.id :id=event.id :level=event.quiz.level lobby=1
                    :starts-at=event.quiz.startsAt :title=event.quiz.title :is-registered="isRegistered"
                    @leaveEvent="handleLeave"/>
      </div>
    </div>

    <div class="dates mt-20">
      <p class="text-3xl mt-3 mb-3">Upcoming Events</p>
      <hr/>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-x-32">
        <AgendaCard v-for="event in events" :key="event.id" :id=event.id :level=event.level lobby=1
                    :starts-at=event.startsAt :title=event.title :is-registered="!isRegistered"
                    @registerEvent="handleRegister"/>
      </div>
    </div>
  </div>
</template>

<script>
import AgendaCard from "@/components/events/EventsCard";

export default {
  inject: ["quizService", "eventService"],
  name: "Agenda",
  components: {AgendaCard},
  data() {
    return {
      isRegistered: true,
      events: [],
      userEvents: [],
      userId: 1
    }
  },
  async created() {
    await this.loadData()
    // {
    //   "id":1001,
    //     "firstName":"Harry",
    //     "lastName":"Kha",
    //     "email":"harry@gmail.com",
    //     "role":0,
    //     "password":"12345678",
    //     "profilePhoto":"https://ui-avatars.com/api/?background=random&name=Harry+Kha",
    //     "createdAt":"2022-10-30T22:28:50",
    //     "updatedAt":"2022-10-30T22:28:50"
    // }
    // const ws = new WebSocket('ws://localhost:8083/api/name');
    // const ws = new WebSocket('ws://localhost:8083/api/quiz');
    //
    // ws.onopen = () => {
    //   console.log("Connection opened...")
    //   ws.send(
    //       {
    //         "id": 1001,
    //         "roomId": 2
    //       }
    //   )
    // }
    //
    // ws.onmessage = (data) => {
    //   const response = JSON.stringify(data)
    //   console.log(response)
    // }
    //
    // ws.onclose = () => {
    //   console.log("Connection closed...")
    // }
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
    async handleLeave(id) {
      try {
        const response = await this.eventService.deleteEvent(id)
        await this.clearEvents()
        await this.loadData()
      } catch (error) {
        console.error(error)
      }
    },
    async loadData() {
      await this.getEventByUserId()
      await this.getAllQuizzes()
    },
    async getEventByUserId() {
      try {
        const response = await this.eventService.getAllEventsByUserId(this.userId)
        const events = response.data
        events.forEach((event) => this.userEvents.push(event))
      } catch (error) {
        console.error(error)
      }
    },
    async getAllQuizzes() {
      try {
        const response = await this.quizService.getQuizzesWhereUserIsNotSignedUpFor(this.userId);
        const quizzes = response.data
        quizzes.forEach((quiz) => this.events.push(quiz))
      } catch (error) {
        console.error(error)
      }
    },
    async clearEvents() {
      this.events = []
      this.userEvents = []
    }
  }
}
</script>

<style scoped>

</style>