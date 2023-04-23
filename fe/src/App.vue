<template>
  <NavBar v-if="!isAuthPage"/>
  <main>
    <router-view></router-view>
  </main>
  <Footer v-if="!isAuthPage"/>
</template>

<script>
import NavBar from "@/components/includes/NavBar";
import Footer from '@/components/includes/Footer'

import {LevelsService} from "@/services/levelsService";
import CONFIG from "./app-config.js";
import {UsersService} from "@/services/user/usersService";
import {AuthService} from "@/services/auth/authService";
import {QuizService} from "@/services/quiz/quizService";
import {QuizResultService} from "@/services/quiz/result/quizResultService";
import {CountingService} from "@/services/countingService";
import {EventService} from "@/services/events/eventService";
import {LevelsLockedService} from "@/services/levelsLockedService";
import {cardsService} from "@/services/cardsService";

export default {
  name: 'App',
  components: {
    NavBar,
    Footer
  },
  provide() {
    return {
      levelsService: new LevelsService(CONFIG.BACKEND_URL + "/levels"),
      usersService: new UsersService(CONFIG.BACKEND_URL + "/users"),
      authService: new AuthService(CONFIG.BACKEND_URL+ "/auth"),
      quizService: new QuizService(CONFIG.BACKEND_URL+ "/quiz"),
      quizResultService: new QuizResultService(CONFIG.BACKEND_URL + "/quiz/result"),
      countingService: new CountingService(CONFIG.BACKEND_URL + "/counting"),
      eventService: new EventService(CONFIG.BACKEND_URL + "/events"),
      levelsLockedService: new LevelsLockedService(CONFIG.BACKEND_URL + "/levelsLocked"),
      cardsService: new cardsService(CONFIG.BACKEND_URL + "/journal")
    }
  },
  computed: {
    isAuthPage() {
      return this.$route.path === "/sign-in" || this.$route.path === "/sign-up" || this.$route.path === "/lost-password";
    },
  },
}
</script>

<style>
</style>
