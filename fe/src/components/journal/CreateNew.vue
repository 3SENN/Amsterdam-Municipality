<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Create new note
      </h2>
      <button @click="$router.go(-1)"
              class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-blue-600 border text-white rounded transition duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
             stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M11.25 9l-3 3m0 0l3 3m-3-3h7.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
        Go back
      </button>
    </div>
  </header>

  <div class="container mx-auto flex justify-center">
    <div class="border-4 border-gray-200 rounded-xl shadow-xl mt-10 h-96 w-9/12">
      <div class="input-field mt-5 ml-3.5 flex flex-col space-y-3">
        <div class="space-y-2">
          <label for="title" class="title">Title</label>
          <br>
          <input v-model="title" id="title" maxlength="15"
                 class="h-12 w-4/5 md:w-2/5 block p-2.5 w-full text-sm rounded-lg border-input border-gray-300"
                 placeholder="Your title...">
          <br>
        </div>

        <div class="space-y-2">
          <label for="text" class="title">Text</label>
          <br>
          <textarea v-model="content" id="text" rows="12" maxlength="250"
                    class="h-24 w-4/5 md:w-3/5 block p-2.5 w-full text-sm rounded-lg border-input border-gray-300"
                    placeholder="Your message..."></textarea>
          <!--          <br>-->
        </div>
        <div class="button-area flex justify-end mr-3 ">
          <button class="create-button" @click="createNew()">Create note</button>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import {cardsService} from "@/services/cardsService";

export default {
  name: "Create New",
  inject: ['cardsService', 'authService'],
  emits: ['create-journal'],
  created() {
    return {
      lastID: 0,
    }
  },
  data() {
    return {
      id: 0,
      title: "",
      cardDate: this.getDate(),
      content: "",
      isDaily: false,
    }
  },
  methods: {
    /**
     * Creating a new journal card.
     *
     * @returns {Promise<void>}
     */
    async createNew() {
      const journalCard = {
        id: this.id,
        title: this.title,
        cardDate: this.cardDate,
        content: this.content,
        daily: this.isDaily,
        userId: this.authService.getId()
      }

      await this.cardsService.addCard(journalCard);

      this.$router.push("/profile/journal");
    },

    /**
     * Making a string of the complete current date (day, date, month, year).
     *
     * @returns {string} - date.
     */
    getDate() {
      const date = new Date();

      const dayName = date.toLocaleString('en-us', {weekday: 'long'});
      const dateNumber = date.getDate();
      const monthName = date.toLocaleString('en-us', {month: 'long'});
      const year = date.getFullYear();

      return String(dayName + ' ' + dateNumber + ' ' + monthName + ' ' + year);
    },
  }
}
</script>

<style scoped>

.title {
  @apply text-black;
  @apply text-lg;
  font-family: Poppins, serif;
}

.border-input {
  border-radius: 8px;
  display: block;
  border-width: thin;
  box-shadow: 2px 2px 2px 1px lightgray;
}

.create-button {
  background-color: #EC0000;
  border: none;
  color: white;
  padding: 10px 15px;
  font-size: smaller;
  cursor: pointer;
  border-radius: 6px;
}

</style>