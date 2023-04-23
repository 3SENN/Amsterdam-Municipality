<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap justify-between items-center sm:px-6 lg:px-8">
      <h2 class="text-xl text-gray-800 leading-tight">
        Journal overview
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

    <div class="content-container h-auto p-6 w-10/12 drop-shadow-xl border-4 border-gray-200 rounded-xl mt-20">

      <div class="flex justify-between">
        <p class="title ml-3 font-bold">{{ card.title }}</p>

        <p class="date mr-3 mt-3 font-bold"><a class="text-municipality-500 font-bold ml-3">{{ card.weeklyOrDaily }}</a>
          {{ card.date }}</p>
      </div>

      <div class="flex justify-between text-black text-left  mt-8 ml-3 mr-1.5">
        <p>
          {{ card.content }}
        </p>
        <router-link to="/profile/journal/editNote" v-slot="{ navigate }">
          <button @click="navigate"
                  class="edit-button bg-blue-600 hover:bg-blue-500 hover:text-white focus:outline-none " role="button">
            Edit
          </button>
        </router-link>
      </div>


    </div>
  </div>
</template>

<script>
export default {
  name: "View Note",
  inject: ['cardsService'],
  created() {
    this.getCardByID()
  },
  data() {
    return {
      card: {
        title: "",
        date: "",
        content: "",
        isDaily: false
      }
    }
  },

  methods: {
    /**
     * This method gets the card by the id of the card that is stored with VueX.
     *
     * @returns {Promise<void>}
     */
    async getCardByID() {
      //VueX stored id of the journal card
      const id = this.$store.state.idOfSeeMore;
      const foundJournals = await this.cardsService.findById(id);

      this.card = foundJournals.data;

      console.log(foundJournals);
    },

  }
}

</script>

<style scoped>

.title {
  @apply text-black;

  font-family: Poppins, serif;
  font-size: 30px;
}

.date {
  font-family: Poppins, serif;
  font-size: smaller;
  float: right;
  color: #A3AED0;
}

.edit-button {
  align-items: center;
  appearance: none;
  border-radius: 0.25rem;
  border-style: none;
  box-sizing: border-box;
  color: #fff;
  cursor: pointer;
  display: inline-flex;
  fill: currentcolor;
  font-family: "Google Sans", Roboto, Arial, sans-serif;
  font-size: 14px;
  font-weight: 500;
  height: 48px;
  justify-content: center;
  letter-spacing: .25px;
  line-height: normal;
  max-width: 100%;
  overflow: visible;
  padding: 2px 24px;
  position: relative;
  text-align: center;
  text-transform: none;
  user-select: none;
  width: auto;
  will-change: transform, opacity;
  z-index: 0;
}
</style>