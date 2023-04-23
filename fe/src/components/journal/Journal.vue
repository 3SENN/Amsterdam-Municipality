<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Journal
      </h2>
      <router-link to="/profile/journal/createNew"
                   class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-green-600 border text-white rounded transition duration-300 hover:bg-green-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
        </svg>

        Create note
      </router-link>
    </div>
  </header>
  <div class="container mx-auto p-4">
    <div class="flex justify-between flex-col md:flex-row ">
      <div class="left-container space-y-3">
        <div class="log">
          <!--This is the top information of the weekly side-->
          <h1>Weekly log</h1>
          <div class="flex justify-between">
            <h4 class="date">Week {{ weekNumber }} of {{ yearNumber }}</h4>
            <div class="weekButtons flex justify-between space-x-2">
              <button @click="pastTime(weekNumber, yearNumber)" class="leftButton">
                <svg class="bg-black" width="11" height="17" viewBox="0 0 11 17" fill="none"
                     xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10.0796 1.97037C11.085 1.02079 9.49306 -0.482714 8.48761 0.545998L1.11425 7.43045C0.695314 7.82611 0.695314 8.53829 1.11425 8.93395L8.48761 15.8975C9.49306 16.8471 11.085 15.3436 10.0796 14.394L3.54411 8.22177L10.0796 1.97037Z"
                      fill="white"/>
                </svg>
              </button>
              <button @click="futureTime(weekNumber, yearNumber)" class="rightButton">
                <svg class="rightArrow bg-black" width="11" height="17" viewBox="0 0 11 17" fill="none"
                     xmlns="http://www.w3.org/2000/svg">
                  <path
                      d="M10.0796 1.97037C11.085 1.02079 9.49306 -0.482714 8.48761 0.545998L1.11425 7.43045C0.695314 7.82611 0.695314 8.53829 1.11425 8.93395L8.48761 15.8975C9.49306 16.8471 11.085 15.3436 10.0796 14.394L3.54411 8.22177L10.0796 1.97037Z"
                      fill="white"/>
                </svg>
              </button>
            </div>
          </div>
          <div class="add-break justify-start">
            <hr class="break">
          </div>
        </div>

        <!--This is the weekly side of the journal cards-->
        <JournalCard
            v-for="card in weeklyCardsPerWeek"
            :key="card.id"
            :id="card.id"
            :card="card"
            @delete="handleDeleteButton(card)"
            @view="getCardByID(card.id)"
        />
      </div>

      <!--This is the top information for the daily side-->
      <div class="right-container space-y-3 mt-7 md:mt-0">
        <div class="log">
          <h1>Daily log</h1>
          <h4 class="date">{{ currentDate }}</h4>
          <div class="add-break justify-start">
            <hr class="break">
          </div>
        </div>

        <!--This is the daily side of the journal cards-->
        <JournalCard
            v-for="card in dailyCards"
            :key="card.id"
            :id="card.id"
            :card="card"
            @delete="handleDeleteButton(card)"
            @view="getCardByID(card.id)"
        />
      </div>
    </div>

    <!--    This is for the confirm message pop up when deleting a card-->
    <div v-if="cardToDelete">
      <router-view :card="cardToDelete" @deleteCard="deleteCard"></router-view>
    </div>
  </div>
</template>

<script>

import JournalCard from "@/components/journal/JournalCard.vue";
import {cardsService} from "@/services/cardsService";
import {Card} from "@/models/Card";

export default {
  name: "Journal",
  inject: ["cardsService", "authService"],
  components: {JournalCard},
  props: {
    journalCard: JournalCard
  },
  computed: {
    /**
     * Filtering the weeklyCards array by the number of the week.
     *
     * @returns {*[]} - filtered array.
     */
    weeklyCardsPerWeek() {
      //Filtering weekly card array on week number
      return this.weeklyCards.filter(card => this.cardWeekNumber(card.cardDate) === (this.weekNumber))
    }

  },
  created() {
    //id of logged-in user
    this.userId = this.authService.getId();

    this.createCards();
    this.getCardByID();
    this.isToday();
    this.currentWeekNumber();
    this.currentYear();
  },
  data() {
    return {
      //Variables
      componentNames: ['JournalCard'],
      userId: null,
      weeklyCards: [],
      dailyCards: [],
      idOfSeeMore: 0,
      currentDate: this.getDate(),
      weekNumber: 0,
      yearNumber: 0,
      cardToDelete: null
    }
  },
  methods: {
    /**
     * This method divides the cards between weekly and daily cards based on the current date.
     *
     * @returns {Promise<void>}
     */
    async createCards() {
      try {
        //Find all cards by user id
        const response = await this.cardsService.findByUserId(this.userId);

        //Making array with the .data of the response
        const cards = response.data;

        //Check if there is any data
        if (cards.length === 0) return;

        //Looping through array of cards
        for (let i = 0; i < cards.length; i++) {
          //If card date is not today the card will be pushed in the weeklyCards array
          if (!await this.isToday(cards[i].cardDate)) {
            this.weeklyCards.push(cards[i])
          }
          //If card date is today the card will be pushed in the dailyCards array
          if (await this.isToday(cards[i].cardDate)) {
            this.dailyCards.push(cards[i])

          }
        }
      } catch (error) {
        console.log(error)
      }
    },

    /**
     * With VueX storing the card's id for another component.
     *
     * @param id - id of card.
     * @returns {Promise<void>}
     */
    async getCardByID(id) {
      //Storing id of the clicked card using VueX => for the ViewNote component
      this.$store.state.idOfSeeMore = id;
    },

    /**
     * This function deletes a card.
     *
     * @param card - card to delete.
     * @returns {Promise<void>}
     */
    async deleteCard(card) {
      //Delete card by id
      const deleteCard = await this.cardsService.deleteCardById(card.id);
      console.log(deleteCard)

      //Reload
      window.location.reload()
    },

    /**
     * Binds the clicked card to the cardToDelete variable for the confirmation pop up.
     *
     * @param card - card to delete.
     */
    handleDeleteButton(card) {
      //When delete button is clicked the confirmation pop-up is shown with the data of the given card
      if (card != null) {
        this.cardToDelete = Card.copyConstructor(card);
        this.$router.push(this.$route.matched[0].path + "/delete/" + card.id);
      } else if (this.cardToDelete != null) {
        this.$router.push(this.$route.matched[0].path);
      }
    },

    /**
     * This function compares the date of the cards with the current date.
     *
     * @param date - date of the card.
     * @returns {Promise<boolean>}
     */
    async isToday(date) {
      const today = new Date();
      const dateOfCard = new Date(date)

      //Checking if dates are the same, if so the card is made today
      return today.toDateString() === dateOfCard.toDateString();
    },

    /**
     * This function is for calculating the number of the current week.
     *
     */
    currentWeekNumber() {
      const currentDate = new Date();

      //Calculation of the current weeknumber
      const startDate = new Date(currentDate.getFullYear(), 0, 1);
      let days = Math.floor((currentDate - startDate) / (24 * 60 * 60 * 1000));
      this.weekNumber = Math.ceil(days / 7);
    },

    /**
     * This function is for calculating the number of the week that the card was created.
     *
     * @param cardDate - date the card was created.
     * @returns {number} - week number of the card.
     */
    cardWeekNumber(cardDate) {
      const date = new Date(cardDate);

      //Calculation of the cards weeknumber
      const startDate = new Date(date.getFullYear(), 0, 1);
      let days = Math.floor((date - startDate) / (24 * 60 * 60 * 1000));
      return Math.ceil(days / 7);
    },

    /**
     * Calculating the current year.
     */
    currentYear() {
      const date = new Date();
      this.yearNumber = date.getFullYear();
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

      //Returning date string of current date
      return String(dayName + ', ' + dateNumber + ' ' + monthName + ' ' + year);
    },

    /**
     * For the different week pages, min 1.
     *
     * @param number - number of week.
     * @param year - number of year.
     */
    pastTime(number, year) {
      if (number > 1) {
        this.weekNumber = number - 1;
      } else if (number === 1) {
        this.weekNumber = 52;
        this.yearNumber = year - 1;
      }
    },

    /**
     * For the different week pages, max 52.
     *
     * @param number - number of week.
     * @param year - number of year.
     */
    futureTime(number, year) {
      if (number < 52) {
        this.weekNumber = number + 1;
      } else if (number === 52) {
        this.weekNumber = 1;
        this.yearNumber = year + 1;
      }
    }
  }
}
</script>

<style scoped>
.log {
  font-size: 28px;
  font-family: Poppins, serif;
}

.date {
  font-size: 18px;
}

.break {
  width: 300px;
  border-color: black;
  margin-top: 8px;
}

.rightArrow {
  /*-webkit-transform: scaleX(-1);*/
  transform: scaleX(-1);
}

.left-container, .right-container {
  @apply block;
}
</style>