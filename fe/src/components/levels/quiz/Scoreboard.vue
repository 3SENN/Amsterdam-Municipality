<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap justify-between items-center sm:px-6 lg:px-8">
      <h2 class="text-xl text-gray-800 leading-tight">Scoreboard </h2>
    </div>
  </header>

  <div class="scoreboard-container bg-red-400 pb-5 pt-5" v-if="players.length > 0 || topThreePlayers.length > 0">
    <h1 class="text-center text-white font-extrabold text-5xl drop-shadow-md pt-20">Scoreboard</h1>


    <!-- Top 3 -->
    <div v-if="topThreePlayers.length > 0" class="top-three-wrapper flex flex-row gap-4 m-auto mt-10 md:w-2/5">

      <!-- Second place -->
      <div v-if="topThreePlayers[1]" class="second-place-wrapper w-1/3 mt-auto">
        <div class="name-wrapper">
          <h2 class="text-white font-bold text-center">
            {{ topThreePlayers[1].userId.firstName }} {{ topThreePlayers[1].userId.lastName }}
          </h2>
        </div>
        <div class="box-wrapper h-[250px] p-2 bg-[#a90000a8] rounded-t-2xl">
          <div class="image-wrapper">
            <img :src="topThreePlayers[1].userId.profilePhoto"
                 class="m-auto rounded-full border-[#C0C0C0] border-4" alt="Profile image">
          </div>
          <div class="score-wrapper text-center mt-8">
            <div class="flex justify-center mb-4">
              <div class="flex justify-center items-center w-6 h-6 p-5 rounded-full bg-[#C0C0C0]">
                <span class="font-bold">2<sup>nd</sup></span>
              </div>
            </div>

            <h2 class="text-white font-bold text-2xl">+{{ topThreePlayers[1].score }}</h2>
          </div>
        </div>
      </div>

      <!-- First place -->
      <div v-if="topThreePlayers[0]" class="first-place-wrapper w-1/3">
        <div class="name-wrapper">
          <h2 class="text-white font-bold text-center">
            {{ topThreePlayers[0].userId.firstName }} {{ topThreePlayers[0].userId.lastName }}
          </h2>
        </div>
        <div class="box-wrapper h-[300px] p-2 bg-[#a90000a8] rounded-t-2xl">
          <div class="image-wrapper">
            <img :src="topThreePlayers[0].userId.profilePhoto"
                 class="m-auto rounded-full border-[#FFD700] border-4" alt="Profile image">
          </div>
          <div class="score-wrapper text-center mt-8">
            <div class="flex justify-center mb-4">
              <div class="flex justify-center items-center w-6 h-6 p-5 rounded-full bg-[#FFD700]">
                <span class="font-bold">1<sup>st</sup></span>
              </div>
            </div>

            <h2 class="text-white font-bold text-2xl">+{{ topThreePlayers[0].score }}</h2>
          </div>
        </div>
      </div>

      <!-- Third place -->
      <div v-if="topThreePlayers[2]" class="third-place-wrapper w-1/3 mt-auto ">
        <div class="name-wrapper">
          <h2 class="text-white font-bold text-center">
            {{ topThreePlayers[2].userId.firstName }} {{ topThreePlayers[2].userId.lastName }}
          </h2>
        </div>
        <div class="box-wrapper h-[200px] p-2 bg-[#a90000a8] rounded-t-xl">
          <div class="image-wrapper">
            <img :src="topThreePlayers[2].userId.profilePhoto"
                 class="m-auto rounded-full border-[#CD7F32] border-4" alt="Profile image">
          </div>
          <div class="score-wrapper text-center mt-8">
            <div class="flex justify-center mb-4">
              <div class="flex justify-center items-center w-6 h-6 p-5 rounded-full bg-[#CD7F32]">
                <span class="font-bold">3<sup>rd</sup></span>
              </div>
            </div>

            <h2 class="text-white font-bold text-2xl">+{{ topThreePlayers[2].score }}</h2>
          </div>
        </div>
      </div>
    </div>
    <div class="flex flex-col m-2 md:m-40 md:mt-6">
      <div v-for="player in players" :key="player.id"
           class="player bg-white mb-2.5" :class="parseInt(userId) === player.userId.id ? 'highlight' : '' ">
        <p class="text-3xl text-center">{{ player.userId.firstName }} {{ player.userId.lastName }}</p>
        <p class="text-3xl">{{ player.score }}</p>
      </div>
    </div>
  </div>
  <div class="container mx-auto" v-else>
    <div class="grid h-screen place-items-center">
      <h1 class="text-3xl mt-2">No results for this quiz</h1>
    </div>
  </div>


</template>

<script>
export default {
  name: "Scoreboard",
  inject: ["quizResultService", "authService"],
  data() {
    return {
      players: [],
      topThreePlayers: [],
      quizId: this.$route.query.quiz,
      userId: this.authService.getId()
    }
  },
  async created() {
    try {
      const response = await this.quizResultService.getQuizResultByQuizId(this.quizId);
      this.players = response.data;
      this.topThreePlayers = this.players.splice(0, 3)
    } catch (error) {
      console.error(error);
    }
  }
}
</script>

<style scoped>
.highlight {
  @apply bg-blue-300 shadow-2xl border-b-2 border-gray-400;
}

.player {
  @apply flex font-bold justify-between p-4;
}

.scoreboard-container {
  height: auto;
  background-image: url("@/assets/img/dashboardBackground.svg");
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;

}
</style>