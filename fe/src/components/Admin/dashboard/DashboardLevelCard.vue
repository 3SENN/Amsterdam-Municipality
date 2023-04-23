<template>
  <div class="md:w-1/5 box-content justify-between h-20 p-6 pb-10  bg-white shadow-2xl rounded-xl flex flex-col">
    <div class="flex flex-row justify-between ">
      <p class="text-xl"><strong>{{ title }}</strong></p>
      <div class="flex flex-row mb-2 gap-5">
        <p class="mt-1"><strong>{{ users }}</strong></p>
        <img src="@/assets/svg/profile.svg" class="leaderboard-img rounded-full shadow-sm" alt="">
      </div>
    </div>
    <div class="w-full bg-municipality-200 rounded-full h-2.5 dark:bg-red-200">
      <div
          :style="{width : `${percentage}%`}"
          class="bg-municipality-500 h-2.5 rounded-full"></div>
    </div>
    <div class="flex flex-row justify-between mt-4">
      <p class="font-bold">Average score:</p> <span class="text-municipality-500">{{ averageScore }}/{{ totalScore }}</span>
    </div>
  </div>

</template>

<script>
export default {
  name: "DashboardLevelCard",
  props: ["title", "users", "totalScore", "averageScore", "percentage"],
}
</script>

<style scoped>
img {
  @apply object-cover;
  width: 35px;
  height: 35px;
}
</style>