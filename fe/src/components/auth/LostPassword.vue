<template>
  <div class="relative flex h-full w-full">
    <div class="absolute left-0 top-0 m-2">
      <router-link to="/"
                   class="text-white font-bold inline-flex items-center justify-center px-3 py-2 text-base font-medium
                   text-center rounded-lg bg-municipality-500 hover:bg-municipality-700 ease-in-out duration-500
                   focus:ring-primary-300">
        Home
      </router-link>
    </div>
    <div class="h-screen w-full md:w-7/12">
      <div class="mx-auto flex h-full w-2/3 flex-col justify-center text-white">
        <div class="flex items-center flex-col">
          <img src="@/assets/img/logo.png" alt="gemeente_amsterdam_logo" class="h-24 mb-6"/>

          <div class="flex flex-col text-center justify-center">
            <h1 class="text-3xl text-black font-medium mb-4">Lost your password?</h1>
            <p class="text-gray-400 w-96">It happens to the best of us.
              Please enter your email so we can send you the link to change your password</p>
          </div>

          <form @submit="onLostPassword">
            <div class="flex flex-col">
              <div class="relative mt-3 mb-3">
                <input type="text" id="email"
                       class="block w-96 rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="john.doe@gmail.com"/>
                <label for="email"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">Email
                  address</label>
              </div>
              <button
                  class="bg-municipality-500 hover:bg-municipality-700 ease-in-out duration-500 text-white font-bold py-2 px-4 rounded"
                  type="submit">Continue
              </button>
            </div>
          </form>

          <p class="text-xs text-gray-500 mt-5">Dont have an account? Create one
            <router-link to="/sign-up"
                         class="text-municipality-500 hover:text-municipality-700 ease-in-out duration-500 font-bold">
              here
            </router-link>
          </p>
          <p class="text-xs text-gray-500 mt-3">Remember your password? Please
            <router-link to="/sign-in"
                         class="text-municipality-500 hover:text-municipality-700 ease-in-out duration-500 font-bold">
              login
            </router-link>
          </p>
        </div>
      </div>
    </div>
    <div class="h-screen w-5/12 bg-red-300 hidden md:block">
      <div class="img-wrapper w-4/5 right-12">
        <img src="@/assets/img/laptop.svg" alt="forgot-password"/>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "LostPassword",
  methods: {
    onLostPassword() {
      this.$router.push("/sign-in")
    }
  }
}
</script>

<style scoped>
.img-wrapper {
  position: relative;
  top: 50%;
  transform: translateY(-50%);
}
</style>