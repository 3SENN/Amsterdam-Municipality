<template>
  <!-- This is an example component -->
  <div class="h-screen w-screen bg-gray-50 flex items-center mt-50">
    <div class="container flex flex-col md:flex-row items-center justify-between px-5 text-gray-700">
      <div class="w-full lg:w-1/2 mx-8">
        <div class="text-7xl text-red-500 font-dark font-extrabold mb-8"> 404</div>
        <p class="text-2xl md:text-3xl font-light leading-normal mb-8">
          Sorry we couldn't find <code class="text-red-400">{{ path }}</code>
        </p>

        <div class="button-group flex flex-wrap gap-3">
          <button @click="$router.go(-1)"
                  class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-blue-600 border text-sm items-center text-white rounded-lg transition duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor"
                 class="w-6 h-6">
              <path stroke-linecap="round" stroke-linejoin="round"
                    d="M11.25 9l-3 3m0 0l3 3m-3-3h7.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            Go back
          </button>
          <router-link class="px-5 inline py-3 text-sm font-medium leading-5 shadow-2xl text-white transition-all
        duration-400 border border-transparent rounded-lg focus:outline-none bg-red-500 active:bg-red-600 hover:bg-red-700"
                       to="/">
            Home
          </router-link>
        </div>
      </div>
      <div class="w-full lg:flex lg:justify-end lg:w-1/2 mx-5 my-12">
        <img :src="ErrorImage" class="" alt="Page not found">
      </div>

    </div>
  </div>
</template>

<style scoped>

</style>

<script>
import ErrorImage from "@/assets/svg/undraw_server_down_s-4-lk.svg";
import {AxiosRequest} from "@/assets/js/utils/axiosRequest";

export default {
  name: "UnknownRoute",
  data() {
    return {
      ErrorImage: ErrorImage,
      path: null,
    }
  },
  async created() {
    this.path = this.$route.path;
    this.axiosRequest = new AxiosRequest();
    const response =  await this.axiosRequest.doRequest(process.env.VUE_APP_API_URL + this.path, "GET");

  }
}
</script>

<style scoped>

</style>