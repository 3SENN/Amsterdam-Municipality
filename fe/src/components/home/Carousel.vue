<template>
  <section class="container mx-auto w-4/5">
    <div v-if="!hasLevelData" class="flex justify-center">
      <danger-alert category="Carousel info" message="Couldn't load the carousel data"></danger-alert>
    </div>
    <splide :options="primaryOptions" rel="primary" class="w-10/12 mx-auto" v-else>
      <splide-slide v-for="level in levels" :key="level.id">
        <div class="max-w-5xl mx-auto py-12 sm:px-6 lg:px-8 content-box">
          <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
            <div class="p-10 overflow-x-auto relative">
              <div class="flex flex-col items-center">
                <div class="h-16 w-16 rounded-full mx-auto flex justify-center items-center bg-red-300">
                  <h1 class="text-3xl font-bold">{{ level.level }}</h1>
                </div>
                <h5 class="mb-4 mt-2 text-xl font-bold text-gray-900">{{ level.name }}</h5>
                <p class="text-sm text-gray-500 text-center">{{ level.info }}</p>
              </div>
            </div>
          </div>
        </div>
      </splide-slide>
    </splide>
  </section>

</template>

<script>
import {Splide, SplideSlide} from '@splidejs/vue-splide'
import dangerAlert from '@/components/includes/alerts/dangerAlert'

export default {
  inject: ["levelsService"],
  components: {
    Splide,
    SplideSlide,
    dangerAlert
  },
  data() {
    return {
      levels: [],
      hasLevelData: false,
      primaryOptions: {
        type: "loop",
        pagination: true,
      }
    }
  },
  async created() {
    await this.loadLevels();
  },
  methods: {
    async loadLevels() {
      this.levels = await this.levelsService.findAll();
      this.hasLevelData = this.levels !== undefined;
    }
  },
}
</script>

<style scoped>
@import '@splidejs/splide/dist/css/themes/splide-default.min.css';

</style>