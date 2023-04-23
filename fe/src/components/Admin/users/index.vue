<template>
  <header class="bg-white shadow">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Manage Users
      </h2>
      <!-- Create new user link -->
      <router-link to="/admin/users/create"
                   class="flex justify-center gap-2 pl-2 pr-4 py-2 bg-green-600 border text-white rounded transition
                   duration-300 hover:bg-green-500 hover:text-white focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
             class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15"/>
        </svg>
        Create user
      </router-link>
    </div>
  </header>


  <div class="w-4/5 mx-auto py-12 sm:px-6 lg:px-8">
    <!-- Success alert -->
    <success-alert v-if="newUser" @onClose="newUser = null"
                   :message="'New user successfully created: ' + newUser.email" category="success"></success-alert>
    <div class="bg-white shadow-xl sm:rounded-lg">
      <div class="p-10 overflow-x-auto relative">
        <!-- Dropdown to change table page -->
        <div class="flex justify-start mt-5">
          <Menu as="div" class="relative inline-block text-left">
            <div>
              <MenuButton
                  class="inline-flex w-full justify-center items-center gap-2 rounded-md bg-green-500 px-4 py-2 text-sm
                  font-medium text-white hover:bg-green-500 focus:outline-none focus-visible:ring-2 focus-visible:ring-white
                  focus-visible:ring-opacity-75">
                Select page
                <svg width="12" height="7" viewBox="0 0 12 7" fill="none" xmlns="http://www.w3.org/2000/svg">
                  <path opacity="0.8" d="M1 1L6 6L11 1" stroke="white" stroke-width="1.4" stroke-linecap="round"
                        stroke-linejoin="round"/>
                </svg>

              </MenuButton>
            </div>

            <transition
                enter-active-class="transition duration-100 ease-out"
                enter-from-class="transform scale-95 opacity-0"
                enter-to-class="transform scale-100 opacity-100"
                leave-active-class="transition duration-75 ease-in"
                leave-from-class="transform scale-100 opacity-100"
                leave-to-class="transform scale-95 opacity-0"
            >
              <MenuItems
                  class="absolute left-0 mt-2 w-56 origin-top-right divide-y divide-gray-100 rounded-md bg-white
                  shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                <div class="px-1 py-1 h-fit max-h-96 overflow-auto">
                  <MenuItem v-for="(page, index) in usersSplit" :key="index">
                    <button @click="onPageSelect(index)"
                            :class="pageNumber === index+1 ? 'bg-green-200' : 'bg-white' "
                            class="group text-left w-full rounded-md px-2 py-2 text-sm cursor-pointer hover:bg-gray-200">
                      {{ index + 1 }}
                    </button>
                  </MenuItem>
                </div>
              </MenuItems>
            </transition>
          </Menu>
        </div>
        <!-- Users table -->
        <table class="users-table w-full text-sm text-left text-gray-500">
          <thead class="text-gray-700 uppercase">
          <tr>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">ID
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">User
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Email
            </th>
            <th scope="col"
                class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider"></th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="user in usersPaginate" :key="user.id"
              class="bg-white border-b transition duration-500 hover:bg-gray-200 ">
            <td class="id-data py-4 px-6">
              {{ user.id }}
            </td>
            <td class="profile-data py-4 px-6 flex items-center">
              <img :src="user.profilePhoto" alt="Profile photo" class="rounded-full mr-2 w-10 h-10">
              <p>{{ user.firstName }} {{ user.lastName }}</p>
            </td>
            <td class="email-data py-4 px-6">
              {{ user.email }}
            </td>
            <td class="py-4 px-6">
              <div class="flex justify-evenly gap-5">
                <router-link :to="{path:'/admin/users/view', query:{id: user.id}}"
                             class="view-btn px-5 py-2 border-blue-500 border text-blue-500 rounded transition duration-300
                               hover:bg-blue-500 hover:text-white focus:outline-none">
                  view
                </router-link>
                <router-link :to="'/admin/users/update/' + user.id"
                             class="px-5 py-2 border-yellow-500 border text-yellow-500 rounded transition duration-300
                                hover:bg-yellow-500 hover:text-white focus:outline-none">
                  update
                </router-link>
                <button v-on:click="handleDeleteButton(user)"
                        class="delete-btn px-5 py-2 border-municipality-500 border text-municipality-500 rounded transition
                          duration-300 hover:bg-municipality-500 hover:text-white focus:outline-none">
                  delete
                </button>
              </div>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

  <div v-if="userToDelete">
    <router-view :user="userToDelete" @deleteUser="onDelete"></router-view>
  </div>

</template>

<script>
import SuccessAlert from "@/components/includes/alerts/successAlert";
import User from "@/models/User";
import {Menu, MenuButton, MenuItems, MenuItem} from '@headlessui/vue'
import Pagination from "@/assets/js/utils/pagination";

export default {
  inject: ["usersService"],
  emits: ["deleteUser"],
  name: "index",
  components: {
    SuccessAlert,
    Menu,
    MenuButton,
    MenuItems,
    MenuItem
  },
  data() {
    return {
      pageNumber: 1,
      usersPaginate: [],
      usersSplit: [],
      userToDelete: null,
      newUser: null,
      deleteModal: false,
    };
  },
  watch: {
    // watching the URL queries for changes
    '$route.query'(query) {
      if (query.id && query.message) {
        // Calling the function to show a user alert
        this.handleNewUserAlert(query)
      }
    }
  },
  async created() {
    try {
      if (this.$route.query) await this.handleNewUserAlert(this.$route.query)
      // Calling function to load data
      await this.loadData()
      // Creating new pagination instance - questionsList as data and 5 rows for each chunk
      this.pagination = new Pagination(this.usersData, 5)
      // Calling function to paginate users
      await this.paginateUsers()
    } catch (error) {
      // Prints error if any
      console.error(error)
    }
  },
  methods: {
    /**
     * Function will show a new success alert when the admin created a new user.
     * @param query The queries in the URL
     * @returns {Promise<void>}
     */
    async handleNewUserAlert(query) {
      // Checking if the value of key message equals NEW
      if (query.message === "NEW") {
        // Making call to get the user by id
        const response = await this.usersService.getUserById(parseInt(query.id))
        // Getting the data from the response and storing it
        const user = response.data

        // Calculating the time difference
        const timeDifference = Math.abs(new Date().getTime() - new Date(user.createdAt).getTime()) / 1000;
        if (timeDifference <= 10) {
          this.newUser = user
        } else {
          this.$router.push(this.$route.matched[0].path);
        }
      }
    },

    /**
     * Async function loads the data. Can be used to refresh list.
     * @returns {Promise<void>}
     */
    async loadData() {
      try {
        // Calling function
        await this.loadFromService()
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Loading all users with calling the function `findAll()` from the `usersService.js` file.
     * @returns {Promise<void>}
     */
    async loadFromService() {
      try {
        // Making call to load all the users
        const response = await this.usersService.findAll();
        // Getting the data from the response and storing it
        this.usersData = response.data
      } catch (error) {
        console.error(error)
      }
    },

    /**
     * Async function paginates the users
     * @returns {Promise<void>}
     */
    async paginateUsers() {
      this.usersPaginate = this.pagination.dataPaginate
      this.usersSplit = this.pagination.dataSplit
    },

    /**
     * When the user clicks for another value in the dropdown, the right chunk will be returned from the split data
     *    array[0] -> 1..nRows, where 0 is the value clicked by user in dropdown
     *    array[1] -> 1..nRows, where 1 is the value clicked by user in dropdown
     * @param number The selected number from the dropdown
     * @returns {Promise<void>}
     */
    async onPageSelect(number) {
      this.pageNumber = number + 1
      this.usersPaginate = this.pagination.onPageSelect(number)
    },

    /**
     * Method handles the delete button in the table.
     * @param user The user object
     */
    handleDeleteButton(user) {
      console.log(user)
      if (user != null) {
        this.userToDelete = User.copyConstructor(user)
        this.$router.push(this.$route.matched[0].path + "/delete/" + user.id);
        console.log("h")
      } else if (this.userToDelete != null) {
        this.$router.push(this.$route.matched[0].path);
      }
    },

    /**
     * Function deletes the user with the function from the users service
     * @param user The user to delete
     * @returns {Promise<void>}
     */
    async onDelete(user) {
      try {
        await this.usersService.deleteById(user.id)
        await this.loadData();

        this.pagination.data = this.usersData
        this.pagination.splitAndPaginate()
        await this.paginateUsers()
        this.$router.push(this.$route.matched[0].path);
      } catch (error) {
        console.error(`Error occurred while deleting the user ${error}`)
      }
    },
  }
}
</script>

<style scoped>

</style>