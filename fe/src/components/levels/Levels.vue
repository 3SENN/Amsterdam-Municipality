<template>
  <div class="games-body mx-auto bg-dashboardBackground bg-no-repeat bg-cover bg-center">
    <section class="max-w-6xl mx-auto px-4 sm:px-6 lg:px-4 py-12">
      <h1 class="title text-center text-white font-bold ">Levels</h1>
      <div class="level-grid grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-3 gap-12 place-items-center">

        <router-link :to=" countingLocked ? '' : '/levels/counting/overview'" class="router-link">
          <div class="level w-60 h-60 rounded-lg p-12 flex flex-col border-4 border-white
         bg-counting bg-no-repeat bg-cover bg-center">
            <h1 class="level-title text-2xl text-white font-bold text-center">Counting</h1>
            <div class="lock" v-if="countingLocked"><img src="../../assets/img/levels/lock.png" alt="lock"></div>
            <div v-else></div>
          </div>
        </router-link>

        <router-link :to=" quizLocked ? '' : '/levels/quiz'" class="router-link">
          <div class="level w-60 h-60 rounded-lg p-12 flex flex-col border-4 border-white
         bg-quiz bg-no-repeat bg-cover bg-center">
            <h1 class="level-title text-2xl text-white font-bold text-center">Quiz</h1>
            <div class="lock" v-if="quizLocked"><img src="../../assets/img/levels/lock.png" alt="lock"></div>
            <div v-else></div>
          </div>
        </router-link>

        <router-link :to="safariLocked ? '' : '/'" class="router-link">
          <div class="level w-60 h-60 rounded-lg p-12 flex flex-col border-4 border-white
         bg-safari bg-no-repeat bg-cover bg-center">
            <h1 class="level-title text-2xl text-white font-bold text-center">Safari</h1>
            <div class="lock" v-if="safariLocked"><img src="../../assets/img/levels/lock.png" alt="lock"></div>
            <div v-else></div>
          </div>
        </router-link>

        <router-link :to="helpTravelLocked ? '' : '/'" class="router-link">
          <div class="level w-60 h-60 rounded-lg p-12 flex flex-col border-4 border-white
         bg-helpTravel bg-no-repeat bg-cover bg-center">
            <h1 class="level-title text-2xl text-white font-bold text-center">Help Travel</h1>
            <div class="lock" v-if="helpTravelLocked"><img src="../../assets/img/levels/lock.png" alt="lock">
            </div>
            <div v-else></div>
          </div>
        </router-link>

        <router-link :to="feedbackLocked ? '' : '/'" class="router-link">
          <div class="level w-60 h-60 rounded-lg p-12 flex flex-col border-4 border-white
         bg-feedback bg-no-repeat bg-cover bg-center">
            <h1 class="level-title text-2xl text-white font-bold text-center">Feedback</h1>
            <div class="lock" v-if="feedbackLocked"><img src="../../assets/img/levels/lock.png" alt="lock">
            </div>
            <div v-else></div>
          </div>
        </router-link>

        <router-link :to="finalBossLocked ? '' : '/'" class="router-link">
          <div class="level w-60 h-60 rounded-lg p-12 flex flex-col border-4 border-white
         bg-finalBoss bg-no-repeat bg-cover bg-center">
            <h1 class="level-title text-2xl text-white font-bold text-center">Final Boss</h1>
            <div class="lock" v-if="finalBossLocked"><img src="../../assets/img/levels/lock.png" alt="lock">
            </div>
            <div v-else></div>
          </div>
        </router-link>
      </div>
    </section>
  </div>
</template>

<script>
import {LevelsLocked} from "@/models/LevelsLocked";

export default {
  name: "Levels",
  inject: ['levelsLockedService', 'authService'],

  data() {
    return {
      countingLocked: true,
      quizLocked: true,
      safariLocked: true,
      helpTravelLocked: true,
      feedbackLocked: true,
      finalBossLocked: true
    }
  },

  created() {
    this.lockLevels();
  },

  methods: {
    async lockLevels() {
      const request = (await this.levelsLockedService.getLevelsLockedByUserId(this.authService.getId()));

      if (request == null) {
        await this.postFirstLevelsLocked();
        await this.lockLevels();
      } else {
        this.countingLocked = request.levels.levelOne;
        this.quizLocked = request.levels.levelTwo;
        this.safariLocked = request.levels.levelThree;
        this.helpTravelLocked = request.levels.levelFour;
        this.feedbackLocked = request.levels.levelFive;
        this.finalBossLocked = request.levels.levelFive;
      }
    },

    async postFirstLevelsLocked() {
      const levelsLockedData = new LevelsLocked({
        levelOne: false,
        levelTwo: false,
        levelThree: true,
        levelFour: true,
        levelFive: true,
        levelSix: true
      });

      await this.levelsLockedService.addLevelsLocked(levelsLockedData, this.authService.getId());
    }
  }

}
</script>

<style scoped>
.title {
  font-size: 3em;
  margin-bottom: 1.1em;
}

.level-title {
  margin-top: -25px;
}

.lock {
  width: 100px;
  margin-left: 20px;
  margin-top: 50px;
}

.level:hover {
  cursor: pointer;
}

.router-link {
  background: none;
}

</style>