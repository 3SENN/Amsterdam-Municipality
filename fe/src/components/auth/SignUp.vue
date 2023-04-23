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
            <h1 class="text-3xl text-black font-medium mb-4">Sign up</h1>
          </div>

          <form @submit.prevent="onSignUp">
            <div class="flex flex-col">
              <div class="relative mt-3 mb-3">
                <input type="text" v-model="firstName" id="first-name"
                       class="block w-96 rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="John"/>
                <label for="first-name"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">First
                  name</label>
                <span v-if="errors.firstName"
                      class="text-sm font-bold text-municipality-500">{{ errors.firstName.message }}</span>
              </div>
              <div class="relative mb-3">
                <input type="text" v-model="lastName" id="last-name"
                       class="block w-96 rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="Doe"/>
                <label for="last-name"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">Last
                  name</label>
                <span v-if="errors.lastName"
                      class="text-sm font-bold text-municipality-500">{{ errors.lastName.message }}</span>
              </div>

              <div class="relative mb-3">
                <label for="email"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">Email
                  Address</label>
                <input type="text" v-model="email" id="email"
                       class="block w-96 rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="john.doe@gmail.com"/>
                <span v-if="errors.email"
                      class="text-sm font-bold text-municipality-500">{{ errors.email.message }}</span>
              </div>

              <div class="relative mb-3">
                <label for="password"
                       class="absolute text-sm text-gray-500 duration-300 transform -translate-y-4 scale-75 top-4 z-10 origin-[0] left-2.5">Password</label>
                <input type="password" v-model="password" id="password"
                       class="block w-96 rounded-t-lg px-2.5 pb-2.5 pt-5 w-full text-sm text-gray-900 bg-gray-50 border-0 border-b-2 border-gray-300 appearance-none focus:outline-none focus:ring-0 focus:border-red-600"
                       placeholder="********" minlength="8"/>
                <span v-if="errors.password"
                      class="text-sm font-bold text-municipality-500">{{ errors.password.message }}</span>
                <div class="password-icon">
                  <button @click.prevent="showPassword()">
                    <img :src="show ? EyeClose : EyeOpen" alt="Password icon">
                  </button>
                </div>
              </div>
              <button
                  class="bg-municipality-500 hover:bg-municipality-700 ease-in-out duration-500 text-white font-bold py-2 px-4 rounded"
                  type="submit">Sign up
              </button>
            </div>
          </form>

          <p class="text-xs text-gray-500 mt-3">Already have an account? Please
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
        <img src="@/assets/img/laptop.svg" alt="Sign-up photo"/>
      </div>
    </div>
  </div>
</template>

<script>
import EyeOpen from '@/assets/svg/eye-open.svg'
import EyeClose from '@/assets/svg/eye-close.svg'
import timeHelper from "@/assets/js/utils/timeHelper.js"
import ERROR_MESSAGES from "@/assets/js/utils/errorMessages"
import {CryptoHelper} from "@/assets/js/utils/cryptoHelper";

export default {

  name: "Sign Up",
  inject: ["authService", "usersService"],
  data() {
    return {
      show: false,
      EyeOpen: EyeOpen,
      EyeClose: EyeClose,
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      errors: {},
    }
  },
  methods: {
    async onSignUp() {
      await this.validate();
      if (Object.keys(this.errors).length !== 0) return
      const user = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        profilePhoto: `https://ui-avatars.com/api/?name=${this.firstName}+${this.lastName}`,
        password: this.password,
        role: 0,
        createdAt: timeHelper.getCurrentDatetime(),
        updatedAt: timeHelper.getCurrentDatetime(),

      }
      user.password = CryptoHelper.hash(10, user.password)
      console.log(user)
      const response = await this.authService.register(user);
      if (response.statusCode === 200) {
        this.$router.push("/sign-in")
      }
      
    },
    async validate() {
      this.errors = {};
      if (this.firstName.length === 0)
        this.errors = {...this.errors, firstName: {message: ERROR_MESSAGES.FIRSTNAME_REQUIRED}};
      if (this.lastName.length === 0)
        this.errors = {...this.errors, lastName: {message: ERROR_MESSAGES.LASTNAME_REQUIRED}};
      if (this.email.length === 0)
        this.errors = {...this.errors, email: {message: ERROR_MESSAGES.EMAIL_REQUIRED}};
      if (this.password.length < 8)
        this.errors = {...this.errors, password: {message: ERROR_MESSAGES.PASSWORD_LENGTH_OF_8}};

      try {
        if (this.email.length <= 0) return
        const response = await this.usersService.getUserByEmail(this.email)

        if (response.statusCode === 200) {
          this.errors = {...this.errors, email: {message: ERROR_MESSAGES.EMAIL_ALREADY_EXISTS}};
        }
      } catch (error) {
        return false
      }

    },
    showPassword() {
      const el = document.querySelector("#password");
      if (!this.show) {
        el.setAttribute("type", "text");
        this.show = true;
      } else {
        el.setAttribute("type", "password");
        this.show = false;
      }
    }
  }
}
</script>

<style scoped>
.password-icon {
  position: absolute;
  top: 50%;
  right: 5%;
  transform: translate(-50%, -50%);
}

.img-wrapper {
  position: relative;
  top: 50%;
  transform: translateY(-50%);
}
</style>