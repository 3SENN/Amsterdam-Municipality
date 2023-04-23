<template>
  <div class="w-full bg-red-400">
    <div class="flex justify-center bg-red-300">
      <p class="text-white text-3xl p-6">Hello player! We'll be waiting until the quiz starts!</p>
    </div>
    <div class="flex justify-between p-6 text-white">
      <div class="left-section">
        <p class="text-3xl">{{ users.length }}/12</p>
        <p>Players</p>
      </div>
      <div class="mid-section">
        <p class="text-3xl">Quiz</p>
      </div>
      <div class="end-section">
        <button
            @click="adminStartQuiz"
            class="button bg-blue-700 hover:bg-blue-900 active:bg-blue-700 ease-in-out duration-500 text-white py-2 px-4 rounded-lg"
            v-if="role">
          Start
        </button>
      </div>
    </div>
    <!--    Mobile grid either grid-cols-1 or grid-cols-2-->
    <div class="grid grid-cols-2 md:grid-cols-5 gap-10 mt-10 p-6">
      <div v-for="user in users" :key="user"
           :class="user.id === parseInt(userId) ? 'bg-blue-300' : 'bg-red-300'"
           class="card flex justify-evenly items-center  p-2 rounded-lg">
        <img :src="user.profilePhoto" class="rounded-full" alt="profile_photo">
        <p class="name text-white font-bold text-xl">{{ user.firstName }}</p>
      </div>
    </div>
    <div class="flex text-center bg-red-300">
      <div class="waiting-section flex items-center p-3">
        <svg class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none"
             viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
          <path class="opacity-75" fill="currentColor"
                d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
        </svg>
        <p class="ml-3 text-2xl text-white">Waiting for players..</p>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  inject: ["usersService", "authService"],
  name: "Lobby",
  data() {
    return {
      users: [],
      userId: this.authService.getId().toString(),
      role: this.authService.isAdmin(),
      // Direct use of LocalStorageHelper instead of using variable because userId is undefined
      lobbyWS: new WebSocket(process.env.VUE_APP_SOCKET_URL + "/lobby?userId=" + this.authService.getId().toString()),
      quizStartWS: new WebSocket(process.env.VUE_APP_SOCKET_URL + "/start?userId=" + this.authService.getId().toString())
    }
  },
  beforeRouteLeave(to, from, next) {
    this.lobbyWS.close()
    next()
  },
  async created() {
    // this.quizStartOnSocketOpen()
    await this.lobbyHandler()
    await this.startQuizHandler()
  },
  watch: {
    // Watching the query id for changes
    async '$route.query.id'(value) {
      if (value !== undefined) {
        await this.lobbyHandler();
      } else {
        this.$router.push("/levels/quiz")
      }
    }
  },
  methods: {
    /**
     * Function shows message when the socket for quiz start is successfully opened
     */
    adminStartQuiz() {
      this.quizStartWS.send(JSON.stringify({"room": this.$route.query.id, "event": "start"}));
    },
    /**
     * Function sends a message to the sockets with the room id and event for the next question.
     * Only executable by the admin!
     * @returns {Promise<void>}
     */
    async startQuizHandler() {
      this.quizStartWS.onopen = () => {
        console.log("[Quiz Start] WebSocket connected...")
      }

      // Get all sessions redirect
      this.quizStartWS.onmessage = (message) => {
        const data = JSON.parse(message.data);
        const gameState = data.event;
        const roomId = this.$route.query.id;
        // Starting question at 1
        if (gameState === "start") this.$router.push({path: '/levels/quiz/room', query: {room: roomId}});
      }
    },
    /**
     * Function handles the lobby socket.
     * When the incoming message is received the user id will be used to make a call
     * to the back-end and retrieves the user object to display the data of the user
     * @returns {Promise<void>}
     */
    async lobbyHandler() {
      // The open event is fired when the connection with the lobby WebSocket is opened.
      this.lobbyWS.onopen = () => {
        console.log("WebSocket [Lobby] connected...")
        // Sending a message with the user id and the room name to all connected users in the same room
        this.lobbyWS.send(JSON.stringify({"id": this.userId, "room": this.$route.query.id}));
      }

      // The message event is fired when data is received through the lobby WebSocket.
      this.lobbyWS.onmessage = async (message) => {
        const data = JSON.parse(message.data)
        // Clearing the user array to avoid duplicate data
        this.users = []
        console.log(data)
        for (let i = 0; i < data.length; i++) {
          let user = await this.usersService.getUserById(data[i].userId);
          if (this.users.length === 0) this.users.push(user.data);
          // If the user id (object) was not found in the last, the code will add the incoming object in the array
          if (!this.users.some(x => x.id === user.data.id)) this.users.push(user.data);
        }
      }
    },
  }
}
</script>

<style scoped>
.player-name {
  @apply m-36;
  @apply text-2xl;
  @apply font-bold;
  @apply text-black;
  @apply bg-gray-100;
  @apply bg-opacity-80;
  @apply p-1;
  @apply rounded;
  @apply absolute;
  @apply align-bottom;
}

.player {
  @apply flex;
  @apply items-center;
  @apply justify-center;
  @apply border-white;
  @apply border-4;
}

.player img {
  @apply object-cover;
  height: auto;
}
</style>
