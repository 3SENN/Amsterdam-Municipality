<template>
  <div class="leaderboard-body justify-center h-screen">
    <h1 class="text-center text-white font-bold text-5xl drop-shadow-md pt-20"><strong>Leaderboards</strong></h1>
    <h2 class="text-center text-white font-bold text-3xl drop-shadow-md"><strong>Overall</strong></h2>
    <div class="stage pt-20 h-10 flex flex-row justify-center h-screen">
      <div class="second overflow-hidden grid place-items-center bottom-0 h-16">
        <div class="flex flex-col place-items-center">
          <p v-if="overallUsers[1]" class="text-white font-extrabold absolute ">{{overallUsers[1].points}}</p>
          <img v-if="overallUsers[1]" :src="overallUsers[1].photo" class="flex rounded-full border-4 border-sky-500 shadow-sm"
               alt="player-two">
          <img v-else src="../../assets/img/stockphotoPerson.jpeg" class="flex rounded-full border-4 border-sky-500 shadow-sm"
               alt="player-two">
          <div class="square-1 static bg-sky-500  rounded-1xl p-2 rotate-45"></div>
          <p class=" mt-14 text-white absolute">2</p>
        </div>
        <p v-if="overallUsers[1]" class=" mt-28 text-white number-2-text absolute">{{overallUsers[1].name}}</p>
        <p v-else class=" mt-28 text-white number-2-text absolute">no-data</p>
      </div>
      <div class="first overflow-hidden grid place-items-center col">
        <div class="flex flex-col place-items-center">
          <p v-if="overallUsers[0]" class="h-5 text-white font-extrabold">{{overallUsers[0].points}}</p>
          <p v-else class="h-5 text-white font-extrabold">no-data</p>
          <img src="../../assets/img/crown.svg" class="crown-img" alt="">
          <img v-if="overallUsers[0]" :src="overallUsers[0].photo" class="flex rounded-full border-4 border-orange-300 shadow-sm"
               alt="player-two">
          <img v-else src="../../assets/img/stockphotoPerson.jpeg" class="flex rounded-full border-4 border-orange-300 shadow-sm"
               alt="player-two">
          <div class="square-1 static bg-orange-300  rounded-1xl p-2 rotate-45"></div>
          <p class="number-one text-white absolute">1</p>
        </div>
        <p v-if="overallUsers[0]" class="mt-36 text-white absolute number-text">{{overallUsers[0].name}}</p>
        <p v-else class=" mt-36 text-white number-2-text absolute">no-data</p>
      </div>
      <div class="third overflow-hidden grid place-items-center bottom-0 h-16">
        <div class="flex flex-col place-items-center">
          <p v-if="overallUsers[2]" class="text-white font-extrabold absolute ">{{overallUsers[2].points}}</p>
          <img v-if="overallUsers[2]" :src="overallUsers[2].photo" class="flex rounded-full border-4 border-lime-600 shadow-sm"
               alt="player-two">
          <img v-else src="../../assets/img/stockphotoPerson.jpeg" class="flex rounded-full border-4 border-lime-600 shadow-sm"
               alt="player-two">
          <div class="square-1 static bg-lime-600  rounded-1xl p-2 rotate-45"></div>
          <p class=" mt-14 text-white absolute">3</p>
        </div>
        <p v-if="overallUsers[2]" class=" mt-28 text-white number-2-text absolute">{{overallUsers[2].name}}</p>
        <p v-else class=" mt-28 text-white number-2-text absolute">no-data</p>
      </div>
    </div>
    <div v-if="loaded === false" class="grid place-items-center " role="status">
      <svg aria-hidden="true" class=" w-8 h-8 text-gray-200 animate-spin dark:text-white fill-red-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
        <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
      </svg>
      <span class="sr-only">Loading...</span>
    </div>
    <div class="leaderboard-table flex flex-col flex-wrap sm:flex-row mt-20 gap-20">
      <carousel class="mt-14"></carousel>
      <carousel-per-level class="mt-14"></carousel-per-level>
    </div>
  </div>
</template>

<script>
import Carousel from "@/components/leaderboard/Carousel";
import CarouselPerLevel from "@/components/leaderboard/CarouselPerLevel";
import usersData from "@/assets/data/users.json";

export default {
  inject:  ["quizResultService", "countingService"],
  name: "Leaderboards",
  components: {
    Carousel,
    CarouselPerLevel
  },
  created() {
    this.getUserdata();
  },
  data() {
    return {
      users:  usersData,
      countingData: [],
      quizData: [],
      overallUsers: [],
      longList: [],
      shortList: [],
      loaded: false
    };
  },
  methods:{
    async getUserdata(){
      try { const countingResponse = await this.countingService.getHighscore();
        if (countingResponse != null) this.countingData = countingResponse;
      }
      catch (error){
        console.error(error + " countingData")
      }
      try {
        const response = await this.quizResultService.findAll()
        this.quizData = response.data.sort(function (a, b) {
          return (b.score - a.score);
        });
      }catch (error){
        console.error(error + " quizResults")
      }

      //Checks which level has the most individual scores.
      if (this.countingData.length > this.quizData.length){
        this.longList = this.countingData
        this.shortList = this.quizData
      }else{
        this.longList = this.quizData
        this.shortList = this.countingData
      }

      //loops through the longest list and adds User and score to overallUsers array.
      for (let i = 0; i < this.longList.length; i++) {
        this.overallUsers[i] = {
          id: this.longList[i].userId.id,
          name: this.longList[i].userId.firstName,
          points: this.longList[i].score,
          photo: this.longList[i].userId.profilePhoto
        };
        //loops through shortlist to check if the users has a score in the other game to add for the overall score.
        for (let j = 0; j < this.shortList.length; j++) {
          if (this.longList[i].userId.id === this.shortList[j].userId.id) this.overallUsers[i].points += this.shortList[j].score;
        }
      }
      if (this.overallUsers.length > 0) this.loaded = true;
      //sort overallUser array on score
      this.overallUsers.sort(function (a, b) {
        return (b.points - a.points);
      });
    },
  },
}
</script>

<style scoped>
.leaderboard-table {
  border-top-left-radius: 45px;
  border-top-right-radius: 45px;
  height: auto;
  background-color: rgba(169, 0, 0, 0.66);
}

.square-1{

}

@supports (-webkit-hyphens:none)
{
  .number-text{
  margin-top: 142px;
  text-align: center;
    width: 100px;
  }

  .number-2-text{
    margin-top: 115px;
    text-align: center;
    width: 100px;
  }
}

.stage {
  height: auto;
  width: auto;
}

.second {
  margin-top: 40px;
  height: 140px;
  width: 100px;
  background-color: rgba(169, 0, 0, 0.66);
  border-top-left-radius: 25px;
  border-bottom-left-radius: 25px;
}

.first {
  height: 180px;
  width: 100px;
  background-color: #A90000;
  border-top-left-radius: 25px;
  border-top-right-radius: 25px;
}

.number-one{
  margin-top: 98px;
}

.third {
  margin-top: 40px;
  height: 140px;
  width: 100px;
  background-color: rgba(169, 0, 0, 0.66);
  border-top-right-radius: 25px;
  border-bottom-right-radius: 25px;
}

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

.crown-img {
  width: 20px;
  height: 20px;
}

.leaderboard-body {
  height: auto;
  background-image: url(../../assets/img/dashboardBackground.svg);
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}
</style>