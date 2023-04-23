<template>
  <div class="right-leaderboard lg:w-3/6 md:w-6/6 sm:w-6/6 mt-10">
    <div class="flex flex-wrap w-full flex-row">
      <div class="flex flex-col w-4/5 ">
        <h1 class="text text-center text-white font-bold text-3xl drop-shadow-md ml-10 "><strong>Leaderboard
          {{ level }}</strong></h1>
        <div class="grid flex  flex-wrap flex-row lg:md:border-l-4">
          <div v-if="users != null" class="grid ml-12 divide-y ">
            <div v-for="(i,index) in users.slice(pagenumber,pagenumber+6)" :key="i"
                 class="user-line flex flex-row items-center gap-x-4 text-center">
              <p v-if="i['userId'] && pagenumber<=5" class="text-white text-xl drop-shadow-md"><strong>{{ index + 1 }}</strong></p>
              <p v-else class="text-white text-xl drop-shadow-md"><strong>{{ index + pagenumber + 1 }}</strong></p>
              <img :src="i.userId.profilePhoto" class="leaderboard-img rounded-full shadow-sm"
                   alt="player-two">
              <div class="flex items-center justify-between gap-x-4 w-11/12 h-12/12">
                <p v-if="i['userId']" class="inline-block align-middle text-white">{{ i.userId.firstName }}</p>
                <p v-else class="inline-block align-middle text-white">Geen</p>
              </div>
              <p class="text-cyan-500 font-extrabold">{{ i.score }}</p>
            </div>
          </div>
          <p v-else class="text-white ml-10">no-data</p>
          <div v-if="loaded === false" class="grid place-items-center " role="status">
            <svg aria-hidden="true" class=" w-8 h-8 text-gray-200 animate-spin dark:text-white fill-red-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
              <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
              <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
            </svg>
            <span class="sr-only">Loading...</span>
          </div>
        </div>
        <div class="grid ml-12 ">
          <div class="flex flex-row gap-x-4 w-full">
            <p v-if="users.length>6  && users.length > 9" class="text-white p-10"><strong>{{ pagenumber + 1 }}-{{ pagenumber + 6 }} of {{ users.length }}</strong>  </p>
            <p v-if="users.length> 6 && users.length < 9" class="text-white p-10"><strong>{{ pagenumber + 1 }}-{{ users.length }} of {{ users.length }}</strong>  </p>
            <p v-else class="text-white p-10"><strong>{{ pagenumber + 1 }}-{{ users.length }} of {{ users.length }}</strong></p>
            <img v-if="pagenumber > 3" v-on:click="onClickPrevious(pagenumber)" class="arrow-prev mt-10 cursor-pointer"
                 src="../../assets/img/arrow.svg" alt="">
            <img v-if="pagenumber < users.length-5" v-on:click="onClickNext(pagenumber)"
                 class="arrow-next mt-10 cursor-pointer" src="../../assets/img/arrow.svg" alt="">
          </div>
        </div>
      </div>
      <div
          class="buttons w-5/6 lg:w-1/6 md:w-5/6 sm:w-5/6 flex flex-wrap gap-3 h-fit sm:flex lg:flex-col md:flex-row sm:flex-row md:m-0 sm:m-10 ">
        <h1 class="text text-center text-white font-bold text-2xl drop-shadow-md ml-10 "><strong>Levels</strong></h1>
        <button v-on:click="onClickLevel(1)"
                class="bg-blue-600 hover:bg-blue-400 ml-10 w-10 h-10 text-white font-bold py-2 px-4 border-b-4 border-blue-700 hover:border-blue-500 rounded">
          1
        </button>
        <button v-on:click="onClickLevel(2)"
                class="bg-blue-700 hover:bg-blue-400 ml-10 w-10 h-10 text-white font-bold py-2 px-4 border-b-4 border-blue-900 hover:border-blue-500 rounded">
          2
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import {Splide, SplideSlide} from '@splidejs/vue-splide';
import usersData from '@/assets/data/users.json'
import {createElementVNode} from "vue";


export default {
  inject: ["quizResultService", "countingService"],
  components: {},

  data() {
    return {
      level: "per level (1)",
      pagenumber: 0,
      users: [],
      levelsData: {
        level1Data: [],
        level2Data: [],
      },
      pageOfItems: [],
      primaryOptions: {
        type: "loop",
        pagination: true,
      },
      loaded: false
    }
  },
  created() {
    this.sortUsersOnLevel1();
  },
  methods: {
    async sortUsersOnLevel1() {
      this.users = ""
      console.log("sortUsersLevel 1")
      //get counting score
      this.users = await this.countingService.getHighscore();
      this.pagenumber = 0;
      //stop loading animation
      if (this.users.length > 0) this.loaded = true;
      //sort user score
      this.users.sort(function (a, b) {
        return (b.score - a.score);
      });
    },
    async sortUsersOnLevel2() {
      this.users = ""
      console.log("sortUsersLevel 2")
      //get quiz results
      const response = await this.quizResultService.findAll()
      //stop loading animation
      if (response.data.length > 0) this.loaded = true;
      this.pagenumber = 0;
      //sort user score
      this.users = response.data.sort(function (a, b) {
        return (b.score - a.score);
      });

    },
    async getUserdata() {
      console.log(this.users)
      const response = await this.usersService.findAll();
      this.users = response.data;
      console.log(this.users)
    },
    onClickNext() {
      this.pagenumber += 6;
    },
    onClickPrevious(pagenumber) {
      if (pagenumber <= 3) {
        return;
      }
      this.pagenumber -= 6;
    },
    onClickLevel(level) {
      this.level = "Level " + level;
      switch (level) {

        case 1:
          console.log("level 1");
          this.loaded =false;
          this.sortUsersOnLevel1();
          /* implement the statement(s) to be executed when
          expression = value1 */
          break;
        case 2:
          console.log("level 2 / quiz");
          this.loaded =false;
          this.sortUsersOnLevel2();
          /* implement the statement(s) to be executed when
          expression = value2 */
          break;
        case 3:
          console.log("level 3")
          /* implement the statement(s) to be executed when
          expression = value3 */
          break;
        default:
          console.log("default")
          /* implement the statement(s) to be executed if expression
          doesn't match any of the above cases */
      }
    }
  }
}
</script>

<style scoped>
@import '@splidejs/splide/dist/css/themes/splide-default.min.css';

.arrow-next {
  width: auto;
  height: 20px;
  transform: scaleX(-1);
}

.arrow-prev {
  width: auto;
  height: 20px;
}

.user-line {
  height: 80px;
}

img {
  @apply object-cover;
  width: 60px;
  height: 60px;

}

.leaderboard-img {
  @apply object-cover;
  width: 50px;
  height: 50px;
}
</style>