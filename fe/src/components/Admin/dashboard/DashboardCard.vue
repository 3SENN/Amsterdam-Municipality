<template>
  <div class="flex flex-col gap-4">
    <router-link v-bind:to="navigation" class="p-2 bg-municipality-500 shadow-2xl rounded-xl text-white w-fit">
      <strong>{{ buttonTitle }}</strong>
    </router-link>
    <div class="box-content h-20 w-52 p-4 bg-white shadow-2xl rounded-xl ">
      <div class="flex flex-row justify-center gap-8 mt-3">
        <img :src="image" class="top-img rounded-full shadow-sm" alt="">
        <div class="flex flex-col">
          <p>{{ boxTitle }}</p>
          <p class="text-2xl font-bold">{{ data }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "DashboardBox",
  props: ["buttonTitle", "boxTitle", "data", "navigation", "image"]
}
</script>

<style scoped>
img {
  @apply object-cover;
  width: 35px;
  height: 35px;
}

.top-img {
  @apply object-cover;
  width: 50px;
  height: 50px;
}
</style>