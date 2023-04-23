<template>
  <section class="game-information mt-8 container mx-auto">
    <div class="info-header text-center">
      <h1 class="mb-4 font-semibold text-3xl">Game Information</h1>
      <div class="w-80 h-2 bg-municipality-500 rounded-full mx-auto"></div>
    </div>

    <div
        class="grid grid-cols-1 gap-20 mt-12 place-items-center sm:grid-cols-1 md:grid-cols-1 lg:grid-cols-2 xl:grid-cols-2">
      <div class="">
        <img class="w-5/6 m-auto" :src="EggsDiversity" alt="Eggs Diversity">
      </div>
      <div class="">
        <p class="text-neutral-400 w-9/12 m-auto">We are all different. Attention has quickly been drawn to cultural
          diversity,
          diversity in
          sexual preferences, religious diversity. But physically and in our heads we all differ. Big,
          small, fat, thin, blind, deaf, autistic, allergic, one arm or leg less.... When making a city
          (designing and organizing the city), we do not pay enough attention to these mental and
          physical differences, which leads to inaccessibility and in cases insecurity. The level of
          awareness of mental and physical diversity is too low when designing and organizing the
          city.</p>
      </div>

      <div class="">
        <p class="text-neutral-400 w-9/12 m-auto">In other Western countries, this awareness is a lot higher and
          inventive
          solutions are being
          devised for accessibility. We have a catch-up to do in the Netherlands. To raise this
          awareness in Amsterdam, a serious game has been devised that will take place both online
          and offline.</p>
      </div>
      <div class="">
        <img class="w-5/6 m-auto" :src="Team" alt="Team">
      </div>
    </div>
  </section>

  <div class="mt-10">
    <div class="relative">
      <h1 class="text-xl text-center text-white font-black levels-title md:text-4xl xl:text-4xl">"6 Levels have been
        devised"</h1>
      <img :src="LevelsImage" class="mx-auto" alt="Levels Image">
    </div>
  </div>
</template>

<script>
import EggsDiversity from '@/assets/svg/eggs-diversity.svg'
import Team from '@/assets/svg/team.svg'
import LevelsImage from '@/assets/img/levels-vector.png'

export default {
  name: "GameInformation",
  data() {
    return {
      EggsDiversity: EggsDiversity,
      Team: Team,
      LevelsImage: LevelsImage
    }
  }
}
</script>

<style scoped>
.levels-title {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>