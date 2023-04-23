<template>
  <div class="left-overall mt-10 flex flex-col lg:w-2/6 md:w-5/6 sm:w-5/6">
    <div class="flex flex-wrap w-full flex-row">
      <div class="flex flex-col w-5/6 lg:w-full md:w-full ">
        <h1 class="text text-center text-white font-bold text-3xl drop-shadow-md mb: ml-10"><strong>Overall</strong></h1>
        <div v-if="overallUsers.length>3" class="grid ml-12 divide-y">
          <div v-for="(i,index) in overallUsers.slice(pagenumber,pagenumber+6)" :key="i"  class="user-line flex flex-row items-center gap-x-4 text-center">
            <p class="text-white text-xl drop-shadow-md"><strong>{{index+4}}</strong></p>
            <img :src="i.photo" class="leaderboard-img rounded-full shadow-sm"
                 alt="player-two">
            <div class="flex items-center justify-between gap-x-4 w-11/12 h-12/12">
              <p class="inline-block align-middle text-white">{{i.name}}</p>
              <p class="text-cyan-500 font-extrabold">{{i.points}}</p>
            </div>
          </div>
          <div class="flex flex-row gap-x-4">
            <p v-if="overallUsers.length>6  && overallUsers.length > 9" class="text-white p-10"><strong>{{ pagenumber + 1 }}-{{ pagenumber + 6 }} of {{ overallUsers.length }}</strong>  </p>
            <p v-if="overallUsers.length> 6 && overallUsers.length < 9" class="text-white p-10"><strong>{{ pagenumber + 1 }}-{{ overallUsers.length }} of {{ overallUsers.length }}</strong>  </p>
            <p v-else class="text-white p-10"><strong>{{ pagenumber + 1 }}-{{ overallUsers.length }} of {{ overallUsers.length }}</strong></p>
            <img v-if="pagenumber > 3" v-on:click="onClickPrevious(pagenumber)" class="arrow-prev mt-10 cursor-pointer"
                 src="../../assets/img/arrow.svg" alt="">
            <img v-if="pagenumber < overallUsers.length-5" v-on:click="onClickNext(pagenumber)"
                 class="arrow-next mt-10 cursor-pointer" src="../../assets/img/arrow.svg" alt="">
          </div>
        </div>
        <p v-else class="text-white ml-10">no-data</p>
        <div v-if="loaded === false" class="loader grid place-items-center " role="status">
          <svg aria-hidden="true" class=" w-8 h-8 text-gray-200 animate-spin dark:text-white fill-red-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
          </svg>
          <span class="sr-only">Loading...</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Splide, SplideSlide} from '@splidejs/vue-splide';
import usersData from '@/assets/data/users.json'
import {createElementVNode} from "vue";


export default {
  inject:  ["quizResultService", "countingService"],
  components: {
  },

  data() {
    return {
      pagenumber: 3,
      users:  usersData,
      pageOfItems: [],
      primaryOptions: {
        type: "loop",
        pagination: true,
      },
      countingData: [],
      quizData: [],
      overallUsers: [],
      longList: [],
      shortList: [],
      loaded: false
    }
  },
  created() {
    this.getUserdata();
  },
  methods:{
    async getUserdata(){
      try { const countingResponse = await this.countingService.getHighscore();
        if (countingResponse != null) this.countingData = countingResponse;}
      catch (error){
        console.error(error)
      }

      try {
        const response = await this.quizResultService.findAll()
        this.quizData = response.data.sort(function (a, b) {
          return (b.score - a.score);
        });
      }catch (error) {
        console.error(error)
      }
      //Checks which level has the most individual scores.
      if (this.countingData.length > this.quizData.length){
        this.longList = this.countingData
        this.shortList = this.quizData
      }else{
        this.longList = this.quizData
        this.shortList = this.countingData
      }

      const userHasScore = [];
      //loops through the longest list and adds User and score to overallUsers array.
      for (let i = 0; i < this.longList.length; i++) {
        this.overallUsers[i] = {
          id: this.longList[i].userId.id,
          name: this.longList[i].userId.firstName,
          points: this.longList[i].score,
          photo: this.longList[i].userId.profilePhoto
        };
        //loops through shortlist to check if the users has a score in the other game to add for the overall score.
        //Else it gives index in userHasScore array
        for (let j = 0; j < this.shortList.length; j++) {
          if (this.longList[i].userId.id === this.shortList[j].userId.id){
            this.overallUsers[i].points += this.shortList[j].score
            userHasScore[j]= true
          }
          else if (userHasScore[j]!== true)userHasScore[j]= j;

        }
      }
      let length = this.longList.length;
      //loops through userHasScore array
      //if userHasScore != true, we use the index that's given in the userHasScore array to add the user and score from the shortlist
      // to the overallUser array
      for (let i = 0; i < userHasScore.length; i++) {
        if (userHasScore[i] !== true){
          this.overallUsers[length] = {
            id: this.shortList[i].userId.id,
            name: this.shortList[i].userId.firstName,
            points: this.shortList[i].score,
            photo: this.shortList[i].userId.profilePhoto
          };
          length += 1;
        }
      }
      if (this.overallUsers.length > 0) this.loaded = true;
      //sort overallUser array on score
      this.overallUsers.sort(function (a, b) {
        return (b.points - a.points);
      });
    },

    onClickNext(){
      this.pagenumber += 6;
    },
    onClickPrevious(pagenumber){
      if (pagenumber <= 3){
        return;
      }
      this.pagenumber -= 6;
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