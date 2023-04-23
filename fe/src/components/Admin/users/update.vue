<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Update user:
        <span v-if="user">{{ user.firstName }} {{ user.lastName }}(
          <code class="text-municipality-500">ID: {{ user.id }}</code>)
        </span>
      </h2>
      <button @click="$router.go(-1)"
              class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-blue-600 border text-white rounded transition duration-300 hover:bg-blue-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round"
                d="M11.25 9l-3 3m0 0l3 3m-3-3h7.5M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
        Go back
      </button>
    </div>
  </header>

  <div class="max-w-5xl mx-auto py-12 sm:px-6 lg:px-8">
    <div v-if="messages.length > 0" class="messages-wrapper">
      <!-- Success alert -->
      <success-alert v-for="(message, index) in messages" category="Success!" :message="message" :key="index"
                     @onClose="closeAlert(index)"></success-alert>
    </div>

    <div v-if="user" class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <form @submit.prevent="onUpdate" class="create-user-wrapper flex justify-between flex-wrap gap-5">
          <!-- Left column of form -->
          <div class="left-column w-1/3">
            <!-- Input box for first name -->
            <div class="space-y-2">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Firstname</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="text"
                  v-model="user.firstName"
              />
              <span v-if=" errors.firstName"
                    class="text-sm font-bold text-municipality-500">{{ errors.firstName.message }}</span>
            </div>
            <!-- Input box for last name -->
            <div class="space-y-2 mt-4">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Lastname</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="text"
                  v-model="user.lastName"
              />
              <span v-if=" errors.lastName"
                    class="text-sm font-bold text-municipality-500">{{ errors.lastName.message }}</span>
            </div>
            <!-- Input box for email -->
            <div class="space-y-2 mt-4">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Email</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="text"
                  v-model="user.email"
              />
              <span v-if=" errors.email"
                    class="text-sm font-bold text-municipality-500">{{ errors.email.message }}</span>
            </div>
            <!-- Input box for role -->
            <div class="space-y-2 mt-4">
              <label class="block mb-2 text-sm font-medium text-gray-900 ">Select a role</label>
              <select id="roles"
                      v-model="user.role"
                      class="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                <option>Choose a role</option>
                <option value="0">User</option>
                <option value="1">Admin</option>
              </select>
              <span v-if=" errors.role"
                    class="text-sm font-bold text-municipality-500">{{ errors.role.message }}</span>
            </div>
          </div>
          <!-- Right column of form -->
          <div class="right-column w-1/2">
            <!-- Input box for profile photo -->
            <div class="space-y-2">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Profile Photo
                <small>*Leave empty for default</small>
              </label>
              <label
                  class="flex flex-col items-center transition duration-500 ease-out px-4 py-6 bg-white text-red rounded-lg shadow-lg tracking-wide uppercase border border-red cursor-pointer hover:bg-red-500 hover:text-white">
                <svg class="w-8 h-8" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                  <path
                      d="M16.88 9.1A4 4 0 0 1 16 17H5a5 5 0 0 1-1-9.9V7a3 3 0 0 1 4.52-2.59A4.98 4.98 0 0 1 17 8c0 .38-.04.74-.12 1.1zM11 11h3l-4-4-4 4h3v3h2v-3z"/>
                </svg>
                <span class="mt-2 text-base leading-normal">Select a file</span>
                <input type='file' class="hidden" @change="onFileChange"/>
              </label>
              <!-- Image preview -->
              <div v-if="imagePreview" class="flex justify-around items-center mt-4" id="preview">
                    <span
                        class="text-sm font-semibold inline-block py-1 px-2 uppercase rounded-full text-neutral-600 bg-neutral-200 uppercase last:mr-0 mr-1">
                      Image preview:
                    </span>
                <img v-if="imagePreview" :src="imagePreview" alt="Image preview"/>
              </div>
            </div>
            <!-- Input box for password] -->
            <div class="space-y-2 mt-8">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Password</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="password"
                  v-model="password"
                  placeholder="*********"
              />
              <span v-if=" errors.password"
                    class="text-sm font-bold text-municipality-500">{{ errors.password.message }}</span>
            </div>
            <!-- Input box for repeat password -->
            <div class="space-y-2 mt-4">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Repeat Password</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="password"
                  v-model="passwordRepeat"
                  placeholder="*********"
              />
              <span v-if=" errors.repeatPassword"
                    class="text-sm font-bold text-municipality-500">{{ errors.repeatPassword.message }}</span>
            </div>
            <div class="space-y-2 mt-14">
              <button
                  class="w-full text-base px-4 py-2 bg-green-600 rounded-lg text-white"
                  type="submit">update user
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>

import timeHelper from "@/assets/js/utils/timeHelper";
import SuccessAlert from '@/components/includes/alerts/successAlert'
import {HTTP_OK_CODE} from "@/assets/js/utils/httpStatusCodes";
import {CryptoHelper} from "@/assets/js/utils/cryptoHelper";

export default {
  inject: ["usersService"],
  name: "update",
  components: {
    SuccessAlert
  },
  data() {
    return {
      user: null,
      imagePreview: null,
      messages: [],
      password: "",
      initialPassword: "",
      passwordRepeat: "",
      passwordErrors: "",
      errors: {}
    }
  },
  /**
   * Called after the instance has finished processing all state-related options.
   * @returns {Promise<void>}
   */
  async created() {
    await this.loadUser()
  },
  methods: {
    /**
     * Function loads user data by user id
     * @returns {Promise<void>}
     */
    async loadUser() {
      const user = await this.usersService.getUserById(this.getParamID())
      this.user = user.data
      this.imagePreview = user.data.profilePhoto
      console.log(user.data)
      this.initialPassword = user.data.password
    },
    /**
     * Function is called when user clicks update in the form.
     * Makes call to back-end to update the user data
     * @returns {Promise<void>}
     */
    async onUpdate() {
      this.errors = {}
      this.validate()
      if (Object.keys(this.errors).length !== 0) return
      const user = this.user
      user.updatedAt = timeHelper.getCurrentDatetime()

      if (this.password.length > 0) {
        this.password = CryptoHelper.hash(10, this.password)
        user.password = this.password.length
        this.password = this.passwordRepeat = this.passwordErrors = ""
      } else {
        user.password = this.initialPassword
      }


      try {
        const data = await this.usersService.saveUser(this.getParamID(), user)
        this.messages.push("Successfully updated the user!")

        this.$router.push("/admin/users")
      } catch (error) {
        console.error(error)
      }

    },
    /**
     * The function will be called when the user changes the image for profile photo
     * @param e
     */
    onFileChange(e) {
      const file = e.target.files[0];
      this.imagePreview = URL.createObjectURL(file);

      console.log(e)
    },
    /**
     * Function removes the message from the array
     * @param index The index of the error in the array
     */
    closeAlert(index) {
      this.messages = this.messages.filter((element, i) => {
        return index !== i
      })
    },
    validate() {
      const emailRegex = /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/;
      if (this.user.firstName.length === 0) {
        this.errors = {...this.errors, firstName: {message: 'First name is required!'}};
      }
      if (this.user.lastName.length === 0) {
        this.errors = {...this.errors, lastName: {message: 'Last name is required!'}};
      }
      if (!this.user.email.match(emailRegex)) {
        this.errors = {...this.errors, email: {message: 'Not a valid email!'}};
      }
      if (parseInt(this.user.role) !== 0 && parseInt(this.user.role) !== 1) {
        this.errors = {...this.errors, role: {message: 'Not a valid role!'}};
      }

      if (this.password.length > 0) {
        if (this.password.length < 8) {
          this.errors = {...this.errors, password: {message: 'Password must be longer then 8 characters'}};
        }
        if (this.password !== this.passwordRepeat) {
          this.errors = {...this.errors, repeatPassword: {message: 'Password must be the same'}};
        }
      }
      console.log(this.errors)
    },
    /**
     * @returns {number} The id of the user to edit
     */
    getParamID() {
      return this.$route.params.id
    },
  }
}
</script>

<style scoped>
#preview img {
  border-radius: 1000px;
  height: 100px;
  width: 100px;
  object-fit: cover;
}
</style>