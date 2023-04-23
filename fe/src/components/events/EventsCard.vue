<template>
  <div class="card flex flex-col bg-white rounded-lg drop-shadow-xl p-3 mt-3 mb-3">
    <div class="header-card flex flex-row p-3 justify-between items-center">
      <div class="title-wrapper">
        <p class="text-xl font-bold info">Level {{ level }}</p>
        <p class="info">{{ title }}</p>
      </div>

      <div class="rounded-full bg-municipality-25 p-4 text-white font-bold text-[1.2em]">{{ registered }}/{{ max }}
      </div>
    </div>
    <div class="footer-card flex flex-row p-2 justify-between items-center">
      <p class="mt-2 info">{{ new Date(startsAt).toLocaleString() }}</p>
      <div v-if="checkIfEventDateIsLargerThenToday(startsAt)">
        <div v-if="ended">
          <div class="bg-red-500 mt-2 px-4 py-1 rounded text-white font-bold  hover:bg-orange-40">
            <p>Ended</p>
          </div>
        </div>
        <div v-else-if="isRegistered" class="flex justify-end grow ml-3">
          <button @click="handleJoin(id)" class="bg-green-700 hover:bg-green-600">Join</button>
          <button @click="handleLeave(id)"
                  class="bg-municipality-500 hover:bg-municipality-700 hover:text-white ease-in-out duration-500">Leave
          </button>
        </div>

        <div v-else>
          <button v-if="!isRegistered && registered !== max" @click="handleRegister(id)"
                  class="bg-blue-500 hover:bg-blue-400">Register
          </button>
          <div v-else class="bg-orange-500 px-4 py-1 rounded text-white font-bold  hover:bg-orange-40"><p>Full</p></div>
        </div>
      </div>
      <div v-else class="bg-orange-500 mt-2 px-4 py-1 rounded text-white font-bold  hover:bg-orange-40"><p>Expired</p></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Events Card",
  props: ["level", "startsAt", "title", "isRegistered", "lobby", "id", "registered", "max", "ended"],
  emits: ['join-lobby', 'leave-event', 'register-event'],
  async created() {
  },
  methods: {
    handleJoin(id) {
      this.$emit('join-lobby', id)
    },
    handleLeave(id) {
      this.$emit('leave-event', id)
    },
    handleRegister(id) {
      this.$emit('register-event', id)
    },
    checkIfEventDateIsLargerThenToday(date) {
      return new Date(date) >= new Date()
    },


  }
}
</script>

<style scoped>
.info {
  color: #A3AED0;
}

button {
  @apply border;
  @apply rounded-lg;
  @apply transition;
  @apply duration-300;
  @apply text-white;
  @apply font-bold;
  @apply p-2;
  @apply w-full;
}
</style>