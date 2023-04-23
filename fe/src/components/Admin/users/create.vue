<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Create user
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

  <div class="w-4/5 mx-auto py-12 ">
    <danger-alert v-for="(message, index) in messages" category="message" :message="message" :key="index"
                  @onClose="closeAlert(index)"></danger-alert>
    <div class="bg-white overflow-hidden shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <form @submit.prevent="onSubmit" class="create-user-wrapper flex justify-between flex-wrap">
          <!-- Left column for form -->
          <div class="left-column">
            <!-- Input box for first name -->
            <div class="space-y-2">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Firstname</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="text" placeholder="John" v-model="user.firstName">
              <ul v-if="msg.firstName" class="list-disc">
                <li class="text-sm font-bold text-municipality-500" v-for="msg in msg.firstName" :key="msg.id">
                  {{ msg.error }}
                </li>
              </ul>
            </div>
            <!-- Input box for last name -->
            <div class="space-y-2 mt-4">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Lastname</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="text" placeholder="Doe" v-model="user.lastName">
              <ul v-if="msg.lastName" class="list-disc">
                <li class="text-sm font-bold text-municipality-500" v-for="msg in msg.lastName" :key="msg.id">
                  {{ msg.error }}
                </li>
              </ul>
            </div>
            <!-- Input box for email -->
            <div class="space-y-2 mt-4">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Email</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="email" placeholder="john@gmail.com" v-model="user.email">
              <ul v-if="msg.email" class="list-disc">
                <li class="text-sm font-bold text-municipality-500" v-for="msg in msg.email" :key="msg.id">
                  {{ msg.error }}
                </li>
              </ul>
            </div>
            <!-- Input box for role -->
            <div class="space-y-2 mt-4">
              <label class="block mb-2 text-sm font-medium text-gray-900 ">Select a role</label>
              <select v-model="user.isAdmin"
                      class="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                <option selected>Choose a role</option>
                <option :value="false">User</option>
                <option :value="true">Admin</option>
              </select>
            </div>
          </div>
          <!-- Right column for form -->
          <div class="right-column">
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
                <input type="file" class="hidden" @change="onFileChange"/>
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
            <!-- Input box for password -->
            <div class="space-y-2 mt-8">
              <label class="text-sm font-medium text-gray-700">Password</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="password" placeholder="*********" v-model="user.password">
              <ul v-if="msg.password" class="list-disc">
                <li class="text-sm font-bold text-municipality-500" v-for="msg in msg.password" :key="msg.id">
                  {{ msg.error }}
                </li>
              </ul>
            </div>
            <!-- Input box for password repeat -->
            <div class="space-y-2 mt-4">
              <label class="text-sm font-medium text-gray-700 tracking-wide">Repeat Password</label>
              <input
                  class="w-full text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-green-500"
                  type="password" placeholder="*********" v-model="repeatPassword">
              <ul v-if="msg.repeatPassword" class="list-disc">
                <li class="text-sm font-bold text-municipality-500" v-for="msg in msg.repeatPassword" :key="msg.id">
                  {{ msg.error }}
                </li>
              </ul>
            </div>
            <div class="space-y-2 mt-14">
              <button
                  class="w-full text-base px-4 py-2 bg-green-600 rounded-lg text-white"
                  type="submit">create user
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import User from "@/models/User";
import timeHelper from "@/assets/js/utils/timeHelper.js"
import {CryptoHelper} from "@/assets/js/utils/cryptoHelper";
import {CONFLICT} from "@/assets/js/utils/httpStatusCodes";
import DangerAlert from '@/components/includes/alerts/dangerAlert'

export default {
  inject: ["usersService"],
  name: "create",
  components: {
    DangerAlert
  },
  data() {
    return {
      labels: ["firstName", "lastName", "email", "password", "repeatPassword"],
      imagePreview: null,
      allValidated: true,
      messages: [],
      msg: {
        firstName: [],
        lastName: [],
        email: [],
        password: [],
        repeatPassword: []
      },
      user: {
        firstName: "",
        lastName: "",
        email: "",
        isAdmin: "",
        profilePhoto: "",
        password: ""
      },
      repeatPassword: "",
    }
  },
  watch: {
    // Watching the first name for changes
    'user.firstName'(value) {
      this.validateInput(value, "firstName")
    },
    // Watching the last name for changes
    'user.lastName'(value) {
      this.validateInput(value, "lastName")
    },
    // Watching the email for changes
    'user.email'(value) {
      this.validateInput(value, "email")
    },
    // Watching the password for changes
    'user.password'(value) {
      this.validateInput(value, "password")
    },
    // Watching the password repeat for changes
    'repeatPassword'(value) {
      this.validateInput(value, "repeatPassword")
    }
  },

  methods: {
    /**
     * Function will be executed when the user clicks submit in the form. The validation process will be started, when
     * everything is successfully checked and no errors where detected the user will be added to the database
     * @returns {Promise<void>}
     */
    async onSubmit() {
      // Loops through the user data and validates it
      for (let key in this.user) {
        this.validateInput(this.user[key], key)
      }

      for (let label of this.labels) {
        if (this.msg[label].length > 0) return;
      }

      // When the optional profile photo is empty, a new profile photo will be created with the ui-avatars URL.
      if (this.user.profilePhoto.length === 0) {
        this.user.profilePhoto = `https://ui-avatars.com/api/?background=random&name=${this.user.firstName}+${this.user.lastName}`
      }

      const newUser = User.copyConstructor(this.user);
      newUser.password = CryptoHelper.hash(10, this.user.password)
      // Calling the helper functions to get the right current datetime
      newUser.createdAt = timeHelper.getCurrentDatetime()
      newUser.updatedAt = timeHelper.getCurrentDatetime()

      await this.addUser(newUser)
    },
    /**
     * Function will make a call to the back end to add the user. If the user was successfully added the user admin
     * will be redirected to the users overview with queries (?id=[id value]?message=NEW)
     * @param user
     * @returns {Promise<void>}
     */
    async addUser(user) {
      try {
        const response = await this.usersService.add(user);
        const data = response.data;

        this.$router.push({
          path: '/admin/users', query: {
            id: data.id,
            message: "NEW"
          }
        })

      } catch (error) {
        if (error.message.status === CONFLICT) {
          this.messages.push(error.message.message)
        }
        console.error(error)
      }
    },
    /**
     * Function will validate the input values.
     * @param value The value of the input to validate
     * @param label The label of the input to validate
     */
    validateInput(value, label) {
      this.msg[label] = []
      if (value.length <= 0) {
        this.msg[label].push({id: 1, error: `${label} is required!`})
      } else if (label === "password") {
        let difference = 8 - value.length;
        if (value.length < 8) {
          this.msg["password"].push({id: 2, error: `Must be 8 characters! ${difference} characters left.`});
        }
      } else if (label === "repeatPassword") {
        if (value !== this.user.password) this.msg[label].push({id: 3, error: "password is not the same"})
      } else {
        this.msg[label] = ""
      }
    },
    /**
     * The function will be called when the user changes the image for profile photo
     * @param e
     */
    onFileChange(e) {
      const file = e.target.files[0];
      this.imagePreview = URL.createObjectURL(file);
      this.user.profilePhoto = this.imagePreview
    }, closeAlert(index) {
      this.messages = this.messages.filter((element, i) => {
        return index !== i
      })
    }
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
