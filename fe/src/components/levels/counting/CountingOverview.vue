<template>
  <header class=" bg-white shadow relative z-20">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Counting Exercise Overview
      </h2>
      <button @click="$router.go(-1)"
              class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-blue-600 border text-white rounded transition
              duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M11.25 9l-3 3m0 0l3 3m-3-3h7.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
        Go back
      </button>
    </div>
  </header>

  <div class="max-w-7xl mx-auto py-12 sm:px-6 lg:px-8">
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <table class="w-full text-sm text-left text-gray-500">
          <thead class="text-gray-700 uppercase">
          <tr>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Start
              position
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Played at
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Score
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">
            </th>
          </tr>
          </thead>
          <tbody v-for="counting in countingLevels" :key="counting.id">
          <tr class="bg-white border-b">
            <th scope="row" class="py-4 px-6">
              {{ counting.startingPoint }}
            </th>
            <td class="py-4 px-6">
              {{ formatDate(new Date(counting.startingTime)) }}
            </td>
            <td class="py-4 px-6">
              {{ counting.score }}
            </td>
            <td class="py-4 px-6">
              <button class="px-12 py-2 border-blue-500 border text-blue-500 rounded transition duration-300
              hover:bg-blue-500 hover:text-white focus:outline-none" v-on:click="viewCounting(counting.id)">
                view
              </button>
            </td>
          </tr>
          </tbody>
        </table>
        <router-link to="/levels/counting" class="text-white bg-red-600 hover:bg-red-800 font-medium rounded-lg text-sm
        w-full sm:w-auto px-6 py-2.5 text-center float-right my-6" v-on:click="clearCountingId()">
          Start new Counting Exercise
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CountingOverview",
  inject: ['countingService', 'authService'],
  data() {
    return {
      countingLevels: []
    }
  },

  async created() {
    this.countingLevels = await this.countingService.getAllCountingLevelsByUserId(this.authService.getId());
  },

  methods: {
    viewCounting(id) {
      this.$store.state.counting.countingId = id;
      this.$router.push('/levels/counting');
    },

    clearCountingId() {
      this.$store.state.counting.countingId = 0;
    },

    formatDate(date) {
      return `${date.toLocaleString('en-us', {weekday: 'long'}).slice(0, 3)}, ${date.getDate()}` +
          ` ${date.toLocaleString('en-us', {month: 'long'}).slice(0, 3)} ${date.getFullYear()}` +
          ` ${date.toLocaleString('en-US', {hour12: false, hour: '2-digit', minute: '2-digit'})}`;
    }
  }
}
</script>

<style scoped>

</style>