<template>
  <div class="pb-48 bg-slate-50 flex flex-col gap-32">
    <div class="flex flex-row flex-wrap justify-center px-20 pt-10 gap-32" id="box">
      <DashboardCard button-title="Manage Users" box-title="Total users:" :data="totalAmountOfUsers"
                     navigation="/admin/users"
                     :image="profileSvg"/>
      <DashboardCard button-title="Manage Events" box-title="Events:" :data="totalAmountOfEvents"
                     navigation="/admin/levels"
                     :image="agendaSvg"/>
      <DashboardCard button-title="Manage Questions" box-title="Questions:" :data="totalAmountOfQuestions"
                     navigation="/admin/questions"
                     :image="agendaSvg"/>
      <DashboardCard button-title="Manage played levels" box-title="User data:" :data="totalAmountOfPlayedLevelsByUsers"
                     navigation="/admin/played-levels"
                     :image="checklistSvg"/>
    </div>
    <div class="flex flex-row justify-center pl-20 pr-20 pt-10 bg-slate-50 gap-32 flex-wrap">
      <DashboardLevelCard title="Level 1"
                          :users="countingScores.users"
                          :total-score="countingScores.totalScore"
                          :average-score="countingScores.averageScore"
                          :percentage="countingScores.percentage">
      </DashboardLevelCard>
      <DashboardLevelCard title="Level 2"
                          :users="quizScores.users"
                          :total-score="quizScores.totalScore"
                          :average-score="quizScores.averageScore"
                          :percentage="quizScores.percentage">>
      </DashboardLevelCard>
    </div>
  </div>
</template>

<script>

import DashboardCard from "@/components/Admin/dashboard/DashboardCard";
import DashboardLevelCard from "@/components/Admin/dashboard/DashboardLevelCard";
import profileSvg from "@/assets/svg/profile.svg"
import agendaSvg from "@/assets/svg/agenda.svg"
import checklistSvg from "@/assets/svg/checklist.svg"

export default {
  inject: ["usersService", "quizResultService", "countingService", "quizService", "eventService"],
  name: "Admin Dashboard",
  components: {DashboardLevelCard, DashboardCard},
  data() {
    return {
      profileSvg: profileSvg,
      agendaSvg: agendaSvg,
      checklistSvg: checklistSvg,
      totalAmountOfUsers: 0,
      totalAmountOfQuestions: 0,
      totalAmountOfPlayedLevelsByUsers: 0,
      totalAmountOfEvents: 0,
      countingScores: [],
      quizScores: []
    }
  },
  async created() {
    await this.setTotalAmountOfUsers()
    await this.setTotalAmountOfQuestions()
    await this.setTotalAmountOfPlayedLevelsByUsers()
    await this.setTotalAmountOfEvents()
    await this.setCountingScores()
    await this.setQuizScores()
  },
  methods: {
    async setTotalAmountOfUsers() {
      const response = await this.usersService.getTotalAmountOfUsers()
      this.totalAmountOfUsers = response.data
    },
    async setTotalAmountOfQuestions() {
      const response = await this.quizService.getTotalAmountOfQuestions();
      this.totalAmountOfQuestions = response.data
    },
    async setTotalAmountOfPlayedLevelsByUsers() {
      const quizResponse = await this.quizResultService.getTotalAmountOfQuizResults()
      const countingResponse = await this.countingService.getTotalAmountOfCountingResults()
      this.totalAmountOfPlayedLevelsByUsers = quizResponse.data + countingResponse.data
    },
    async setTotalAmountOfEvents() {
      const response = await this.quizService.getTotalAmountOfQuizzes()
      this.totalAmountOfEvents = response.data + 1
    },
    async setCountingScores() {
      const response = await this.countingService.getScores()
      this.countingScores = response.data
    },
    async setQuizScores() {
      const response = await this.quizResultService.getScores()
      this.quizScores = response.data
    }
  }

}
</script>

<style scoped>
img {
  @apply object-cover;
  width: 35px;
  height: 35px;
}
</style>