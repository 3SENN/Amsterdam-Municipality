<template>
  <section class="hero mb-8">
    <div class="container grid z-10 max-w-screen-xl px-4 py-8 mx-auto lg:gap-8 xl:gap-0 lg:py-16 lg:grid-cols-12">
      <div class="mr-auto place-self-center lg:col-span-7">
        <h1 class="font-bold tracking-tight leading-none text-municipality-500 mb-2 ml-2">SERIOUS GAME</h1>
        <h1 class="max-w-2xl mb-4 text-4xl font-black tracking-tight leading-none md:text-5xl xl:text-6xl">
          Amsterdam</h1>
        <h1 class="max-w-2xl mb-4 text-4xl font-black tracking-tight leading-none md:text-5xl xl:text-6xl">
          Municipality</h1>
        <p class="text-sm font-light text-neutral-400">A new serious game to raise awareness for</p>
        <p class="text-sm font-light text-neutral-400">mental and physical diversity and inclusive city</p>
        <p class="text-sm mb-6 font-light text-neutral-400">making</p>
        <div class="button-group flex flex-wrap gap-3">
          <router-link v-if="!getToken" to="/sign-up" class="w-5/12 text-white font-bold inline-flex items-center justify-center px-5 py-3
          text-base font-medium text-center rounded-lg bg-municipality-500 hover:bg-municipality-700
          ease-in-out duration-500 focus:ring-4 focus:ring-primary-300">
            Sign up
          </router-link>
          <button @click="scrollTo(info)"
             class="w-5/12 inline-flex items-center justify-center px-5 py-3 text-base font-medium text-center
             text-municipality-500 border border-municipality-500 rounded-lg hover:bg-gray-200
             ease-in-out duration-500 focus:ring-4 focus:ring-gray-100">
            More info
          </button>
        </div>
      </div>
      <div class="hidden lg:mt-0 lg:col-span-5 lg:flex">
        <img class="w-9/12 z-10" :src="HeroImage" alt="Hero Image"/>

        <div class="ellipse-box absolute -top-32 -right-40 z-0">
          <img :src="Ellipse" alt="Ellipse"/>
        </div>
      </div>
    </div>
  </section>

  <game-information ref="info"></game-information>

  <carousel class="mt-14"></carousel>
</template>

<script>
import GameInformation from "@/components/home/GameInformation"
import Carousel from './Carousel'
import HeroImage from '@/assets/svg/hero-amsterdam.svg'
import Ellipse from '@/assets/svg/ellipse.svg'

export default {
  name: "Homepage",
  inject: ["authService"],
  components: {
    GameInformation,
    Carousel
  },
  data() {
    return {
      HeroImage: HeroImage,
      Ellipse: Ellipse,
    };
  },
  methods: {
    scrollTo(refName) {
      console.log(this.$refs.info)
    },
    getToken() {
      return this.authService.getTokenFromBrowserStorage()
    }
  }
}
</script>

<style>
body {
  overflow-x: hidden;
}
</style>