<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        View user: <span v-if="user">{{ user.firstName }} {{ user.lastName }}(<code
          class="text-municipality-500">ID: {{ user.id }}</code>)</span>
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
  <div v-if="user" class="max-w-lg mx-auto py-12 sm:px-6 lg:px-8 relative">
    <div>
      <span class="absolute -right-0 top-[90px] z-[-1]">
          <svg width="34" height="134" viewBox="0 0 34 134" fill="none"
               xmlns="http://www.w3.org/2000/svg">
              <circle cx="31.9993" cy="132" r="1.66667" transform="rotate(180 31.9993 132)"
                      class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="117.333" r="1.66667"
                      transform="rotate(180 31.9993 117.333)" class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="102.667" r="1.66667"
                      transform="rotate(180 31.9993 102.667)" class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="88" r="1.66667" transform="rotate(180 31.9993 88)"
                      class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="73.3333" r="1.66667"
                      transform="rotate(180 31.9993 73.3333)" class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="45" r="1.66667" transform="rotate(180 31.9993 45)"
                      class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="16" r="1.66667" transform="rotate(180 31.9993 16)"
                      class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="59" r="1.66667" transform="rotate(180 31.9993 59)"
                      class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="30.6666" r="1.66667"
                      transform="rotate(180 31.9993 30.6666)" class="fill-municipality-500"></circle>
              <circle cx="31.9993" cy="1.66665" r="1.66667"
                      transform="rotate(180 31.9993 1.66665)" class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="132" r="1.66667" transform="rotate(180 17.3333 132)"
                      class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="117.333" r="1.66667"
                      transform="rotate(180 17.3333 117.333)" class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="102.667" r="1.66667"
                      transform="rotate(180 17.3333 102.667)" class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="88" r="1.66667" transform="rotate(180 17.3333 88)"
                      class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="73.3333" r="1.66667"
                      transform="rotate(180 17.3333 73.3333)" class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="45" r="1.66667" transform="rotate(180 17.3333 45)"
                      class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="16" r="1.66667" transform="rotate(180 17.3333 16)"
                      class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="59" r="1.66667" transform="rotate(180 17.3333 59)"
                      class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="30.6666" r="1.66667"
                      transform="rotate(180 17.3333 30.6666)" class="fill-municipality-500"></circle>
              <circle cx="17.3333" cy="1.66665" r="1.66667"
                      transform="rotate(180 17.3333 1.66665)" class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="132" r="1.66667" transform="rotate(180 2.66536 132)"
                      class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="117.333" r="1.66667"
                      transform="rotate(180 2.66536 117.333)" class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="102.667" r="1.66667"
                      transform="rotate(180 2.66536 102.667)" class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="88" r="1.66667" transform="rotate(180 2.66536 88)"
                      class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="73.3333" r="1.66667"
                      transform="rotate(180 2.66536 73.3333)" class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="45" r="1.66667" transform="rotate(180 2.66536 45)"
                      class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="16" r="1.66667" transform="rotate(180 2.66536 16)"
                      class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="59" r="1.66667" transform="rotate(180 2.66536 59)"
                      class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="30.6666" r="1.66667"
                      transform="rotate(180 2.66536 30.6666)" class="fill-municipality-500"></circle>
              <circle cx="2.66536" cy="1.66665" r="1.66667"
                      transform="rotate(180 2.66536 1.66665)" class="fill-municipality-500"></circle>
          </svg>
      </span>
      <span class="absolute -left-2 -bottom-2 z-[-1]">
            <svg width="107" height="134" viewBox="0 0 107 134" fill="fill-municipality-500"
                 xmlns="http://www.w3.org/2000/svg">
                <circle cx="104.999" cy="132" r="1.66667" transform="rotate(180 104.999 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="117.333" r="1.66667"
                        transform="rotate(180 104.999 117.333)" class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="102.667" r="1.66667"
                        transform="rotate(180 104.999 102.667)" class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="88" r="1.66667" transform="rotate(180 104.999 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="73.3333" r="1.66667"
                        transform="rotate(180 104.999 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="45" r="1.66667" transform="rotate(180 104.999 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="16" r="1.66667" transform="rotate(180 104.999 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="59" r="1.66667" transform="rotate(180 104.999 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="30.6666" r="1.66667"
                        transform="rotate(180 104.999 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="104.999" cy="1.66665" r="1.66667"
                        transform="rotate(180 104.999 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="132" r="1.66667" transform="rotate(180 90.3333 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="117.333" r="1.66667"
                        transform="rotate(180 90.3333 117.333)" class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="102.667" r="1.66667"
                        transform="rotate(180 90.3333 102.667)" class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="88" r="1.66667" transform="rotate(180 90.3333 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="73.3333" r="1.66667"
                        transform="rotate(180 90.3333 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="45" r="1.66667" transform="rotate(180 90.3333 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="16" r="1.66667" transform="rotate(180 90.3333 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="59" r="1.66667" transform="rotate(180 90.3333 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="30.6666" r="1.66667"
                        transform="rotate(180 90.3333 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="90.3333" cy="1.66665" r="1.66667"
                        transform="rotate(180 90.3333 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="132" r="1.66667" transform="rotate(180 75.6654 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="132" r="1.66667" transform="rotate(180 31.9993 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="117.333" r="1.66667"
                        transform="rotate(180 75.6654 117.333)" class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="117.333" r="1.66667"
                        transform="rotate(180 31.9993 117.333)" class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="102.667" r="1.66667"
                        transform="rotate(180 75.6654 102.667)" class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="102.667" r="1.66667"
                        transform="rotate(180 31.9993 102.667)" class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="88" r="1.66667" transform="rotate(180 75.6654 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="88" r="1.66667" transform="rotate(180 31.9993 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="73.3333" r="1.66667"
                        transform="rotate(180 75.6654 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="73.3333" r="1.66667"
                        transform="rotate(180 31.9993 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="45" r="1.66667" transform="rotate(180 75.6654 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="45" r="1.66667" transform="rotate(180 31.9993 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="16" r="1.66667" transform="rotate(180 75.6654 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="16" r="1.66667" transform="rotate(180 31.9993 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="59" r="1.66667" transform="rotate(180 75.6654 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="59" r="1.66667" transform="rotate(180 31.9993 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="30.6666" r="1.66667"
                        transform="rotate(180 75.6654 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="30.6666" r="1.66667"
                        transform="rotate(180 31.9993 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="75.6654" cy="1.66665" r="1.66667"
                        transform="rotate(180 75.6654 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="31.9993" cy="1.66665" r="1.66667"
                        transform="rotate(180 31.9993 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="132" r="1.66667" transform="rotate(180 60.9993 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="132" r="1.66667" transform="rotate(180 17.3333 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="117.333" r="1.66667"
                        transform="rotate(180 60.9993 117.333)" class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="117.333" r="1.66667"
                        transform="rotate(180 17.3333 117.333)" class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="102.667" r="1.66667"
                        transform="rotate(180 60.9993 102.667)" class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="102.667" r="1.66667"
                        transform="rotate(180 17.3333 102.667)" class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="88" r="1.66667" transform="rotate(180 60.9993 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="88" r="1.66667" transform="rotate(180 17.3333 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="73.3333" r="1.66667"
                        transform="rotate(180 60.9993 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="73.3333" r="1.66667"
                        transform="rotate(180 17.3333 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="45" r="1.66667" transform="rotate(180 60.9993 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="45" r="1.66667" transform="rotate(180 17.3333 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="16" r="1.66667" transform="rotate(180 60.9993 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="16" r="1.66667" transform="rotate(180 17.3333 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="59" r="1.66667" transform="rotate(180 60.9993 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="59" r="1.66667" transform="rotate(180 17.3333 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="30.6666" r="1.66667"
                        transform="rotate(180 60.9993 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="30.6666" r="1.66667"
                        transform="rotate(180 17.3333 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="60.9993" cy="1.66665" r="1.66667"
                        transform="rotate(180 60.9993 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="17.3333" cy="1.66665" r="1.66667"
                        transform="rotate(180 17.3333 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="132" r="1.66667" transform="rotate(180 46.3333 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="132" r="1.66667" transform="rotate(180 2.66536 132)"
                        class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="117.333" r="1.66667"
                        transform="rotate(180 46.3333 117.333)" class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="117.333" r="1.66667"
                        transform="rotate(180 2.66536 117.333)" class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="102.667" r="1.66667"
                        transform="rotate(180 46.3333 102.667)" class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="102.667" r="1.66667"
                        transform="rotate(180 2.66536 102.667)" class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="88" r="1.66667" transform="rotate(180 46.3333 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="88" r="1.66667" transform="rotate(180 2.66536 88)"
                        class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="73.3333" r="1.66667"
                        transform="rotate(180 46.3333 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="73.3333" r="1.66667"
                        transform="rotate(180 2.66536 73.3333)" class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="45" r="1.66667" transform="rotate(180 46.3333 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="45" r="1.66667" transform="rotate(180 2.66536 45)"
                        class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="16" r="1.66667" transform="rotate(180 46.3333 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="16" r="1.66667" transform="rotate(180 2.66536 16)"
                        class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="59" r="1.66667" transform="rotate(180 46.3333 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="59" r="1.66667" transform="rotate(180 2.66536 59)"
                        class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="30.6666" r="1.66667"
                        transform="rotate(180 46.3333 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="30.6666" r="1.66667"
                        transform="rotate(180 2.66536 30.6666)" class="fill-municipality-500"></circle>
                <circle cx="46.3333" cy="1.66665" r="1.66667"
                        transform="rotate(180 46.3333 1.66665)" class="fill-municipality-500"></circle>
                <circle cx="2.66536" cy="1.66665" r="1.66667"
                        transform="rotate(180 2.66536 1.66665)" class="fill-municipality-500"></circle>
            </svg>
        </span>
    </div>
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <div class="flex flex-col items-center">
          <!-- User profile photo -->
          <img class="mb-3 w-24 h-24 rounded-full" :src="user.profilePhoto" alt="Profile Photo">
          <!-- User full name -->
          <h5 class="mb-1 text-xl font-medium text-gray-900">{{ user.firstName }} {{ user.lastName }}</h5>
          <!-- User info -->
          <div class="info">
            <div class="text-sm text-gray-500 flex justify-between gap-2">
              <!-- User email -->
              <span class="font-bold">Email: </span>{{ user.email }}
            </div>
            <!-- User role -->
            <div class="text-sm text-gray-500 flex justify-between"><span class="font-bold">Role: </span>
              {{ user.role === 1 ? "Admin" : "User" }}
            </div>
            <!-- User created at -->
            <div class="text-sm text-gray-500 flex justify-between"><span
                class="font-bold">Joined at: </span>{{ user.createdAt.toLocaleString() }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

export default {
  inject: ["usersService"],
  name: "show",
  data() {
    return {
      user: null
    }
  },
  watch: {
    // Watching the query id for changes
    async '$route.query.id'() {
      await this.loadUser();
    }
  },
  async created() {
    await this.loadUser();
  },
  methods: {
    /**
     * Function loads the user by id
     * @returns {Promise<void>}
     */
    async loadUser() {
      try {
        const response = await this.usersService.getUserById(this.getQueryId())
        this.user = response.data
      } catch (error) {
        console.error(error)
      }
    },
    /**
     * @returns {number} The id of the user
     */
    getQueryId() {
      return this.$route.query.id
    }
  }
}
</script>

<style scoped>

</style>