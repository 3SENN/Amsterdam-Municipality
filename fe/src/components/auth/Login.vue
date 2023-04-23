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
            <h1 class="text-3xl text-black font-medium mb-4">Welcome Back!</h1>
            <p class="text-gray-400 w-96">Sign in by using your Email and password</p>
          </div>

          <form @submit.prevent="onLogin">
            <div class="flex flex-col w-96">
              <div class="relative mt-3 mb-6">
                <input type="text" id="email"
                       class="block rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="john.doe@gmail.com" v-model="email"/>
                <label for="email"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">Email
                  address</label>
              </div>
              <div class="relative mb-3">
                <input type="password" id="password"
                       class="block rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="********" v-model="password"/>
                <label for="password"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">Password</label>
              </div>

              <span v-if="loginMessage.length  > 0 "
                    class="text-sm font-bold text-municipality-500">{{ loginMessage }}</span>
            </div>

            <div class="flex justify-between w-96 mt-5">
              <div class="flex items-center mb-4">
                <input id="remember-me" type="checkbox" value=""
                       class="w-4 h-4 bg-gray-100 rounded border-gray-300 accent-municipality-300 focus:ring-red-500 focus:ring-2">
                <label for="remember-me" class="ml-2 text-sm text-black">Remember me</label>
              </div>
              <div class="flex items-center mb-4">
                <router-link to="/lost-password" class="flex group">
                  <svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fill="#747474" class="group-hover:fill-slate-400 ease-in-out duration-500"
                          d="M8 0C3.58203 0 0 3.58332 0 8C0 12.4193 3.58203 16 8 16C12.418 16 16 12.4193 16 8C16 3.58332 12.418 0 8 0ZM8 3.54839C8.74826 3.54839 9.35484 4.15497 9.35484 4.90323C9.35484 5.65148 8.74826 6.25806 8 6.25806C7.25174 6.25806 6.64516 5.65148 6.64516 4.90323C6.64516 4.15497 7.25174 3.54839 8 3.54839ZM9.80645 11.7419C9.80645 11.9557 9.63313 12.129 9.41935 12.129H6.58064C6.36687 12.129 6.19355 11.9557 6.19355 11.7419V10.9677C6.19355 10.754 6.36687 10.5806 6.58064 10.5806H6.96774V8.51613H6.58064C6.36687 8.51613 6.19355 8.34281 6.19355 8.12903V7.35484C6.19355 7.14106 6.36687 6.96774 6.58064 6.96774H8.64516C8.85894 6.96774 9.03226 7.14106 9.03226 7.35484V10.5806H9.41935C9.63313 10.5806 9.80645 10.754 9.80645 10.9677V11.7419Z"/>
                  </svg>
                  <label
                      class="ml-2 text-sm text-black cursor-pointer group-hover:text-slate-400 ease-in-out duration-500">Forgot
                    password</label>
                </router-link>
              </div>
            </div>

            <button
                class="bg-municipality-500 hover:bg-municipality-700 text-white font-bold py-2 px-4 rounded w-96 ease-in-out duration-500"
                type="submit">Login
            </button>
          </form>
          <p class="text-xs text-gray-500 mt-5">Dont have an account? Create one
            <router-link to="/sign-up" class="text-municipality-500 hover:text-municipality-700 font-bold">here
            </router-link>
          </p>
        </div>
      </div>
    </div>
    <div class="h-screen w-5/12 bg-red-300 hidden md:block">
      <div class="img-wrapper w-4/5 right-12">
        <img src="@/assets/img/laptop.svg" alt="login-in"/>
      </div>
    </div>
  </div>
</template>

<script>
import {CryptoHelper} from "@/assets/js/utils/cryptoHelper";
import ERROR_MESSAGES from "@/assets/js/utils/errorMessages";

export default {
  name: "Login",
  inject: ["authService", "usersService"],
  data() {
    return {
      email: "",
      password: "",
      loginMessage: "",
    }
  },
  methods: {
    async onLogin() {
      const loginDTO = {
        email: this.email,
        hashedPassword: CryptoHelper.hash(10, this.password),
        password: this.password,
      }
      if (this.email.length === 0) this.loginMessage = ERROR_MESSAGES.EMAIL_REQUIRED;

      else if (this.password.length === 0) this.loginMessage = ERROR_MESSAGES.PASSWORD_REQUIRED;

      try {
        if (this.password === "") return;
        const user = await this.usersService.getUserByEmail(this.email);
        const userData = user.data;
        if (await CryptoHelper.compare(this.password, userData.password)) {
          const response = await this.authService.login(loginDTO);
          const data = response.data;

          this.authService.saveTokenIntoBrowserStorage(data)
          this.$router.push("/");

        } else {
          this.loginMessage = "Wrong credentials, please try again."
        }
      } catch (error) {
        this.loginMessage = "Login failed, try again!"
      }
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