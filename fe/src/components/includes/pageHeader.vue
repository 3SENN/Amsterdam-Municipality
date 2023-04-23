<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        {{ title }}
      </h2>
      <router-link to="/admin/users/create"
                   class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-green-600 border text-white rounded transition duration-300 hover:bg-green-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
        </svg>

        Create user
      </router-link>
    </div>
  </header>
</template>

<script>
export default {
  name: "pageHeader",
  props: ["title"]
}
</script>

<style scoped>

</style>