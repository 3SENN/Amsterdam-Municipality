<template>
  <Menu as="div" class="relative inline-block text-left">
    <div>
      <MenuButton class="inline-flex w-full justify-center rounded-md  px-4 hover:bg-opacity-30">
        <!--        Profile photo -->
        <img :src="profileImage" class="w-14 h-14 rounded-full" alt="Profile Image">
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
          class="absolute w-max  md:right-0  mt-2 w-56 origin-top-right divide-y rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
        <div class="p-4 flex">
          <div class="profile-img-wrapper">
            <img :src="profileImage" class="rounded-full" alt="Profile Image">
          </div>
          <div class="profile-data-wrapper ml-2">
            <h3 class="font-bold">{{ fullName }}</h3>
            <p class="text-sm">Admin</p>
            <p class="text-sm">{{ email }}</p>
          </div>
        </div>
        <div class="px-1 py-1">
          <div class="mt-2 group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm">
            <p class="text-neutral-300">Profile</p>
          </div>
          <MenuItem>
            <router-link
                exact-active-class="bg-municipality-500 text-white"
                class="group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm ease-in-out duration-500
                hover:bg-municipality-700 hover:text-white"
                to="/profile/manage/account">
              Manage Profile
            </router-link>
          </MenuItem>
          <MenuItem>
            <router-link
                exact-active-class="bg-municipality-500 text-white"
                class="mt-2 group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm
                  ease-in-out duration-500 hover:bg-municipality-700 hover:text-white"
                to="/profile/journal">
              Journal
            </router-link>
          </MenuItem>
        </div>
        <div class="px-1 py-1">
          <div class="px-1 py-1">
            <div class="group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm">
              <p class="text-neutral-300">Administrator</p>
            </div>
            <MenuItem>
              <router-link
                  exact-active-class="bg-municipality-500 text-white"
                  class="group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm
                  ease-in-out duration-500 hover:bg-municipality-700 hover:text-white"
                  to="/admin">
                Dashboard
              </router-link>
            </MenuItem>
          </div>
        </div>
        <div class="px-2 py-2">
          <MenuItem>
            <router-link
                exact-active-class="bg-municipality-500 text-white"
                class="group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm ease-in-out duration-500
                hover:bg-municipality-700 hover:text-white"
                to="/sign-out">
              Sign out
            </router-link>
          </MenuItem>
        </div>
      </MenuItems>
    </transition>
  </Menu>
</template>

<script>
import {Menu, MenuButton, MenuItems, MenuItem} from '@headlessui/vue'
import LocalStorageHelper from "@/assets/js/utils/localStoragehelper";

export default {
  inject: ["authService"],
  name: "SubMenuAdmin",
  components: {
    Menu,
    MenuButton,
    MenuItems,
    MenuItem
  },
  data() {
    return {
      fullName: "",
      role: "",
      email: "",
      profileImage: ""
    };
  },
  created() {
    this.getUserData();
  },
  methods: {
    getUserData() {
      this.role = this.authService.getRole()
      this.email = this.authService.getEmail()
      this.fullName = this.authService.getFullName()
      this.profileImage = this.authService.getProfilePicture()
    },
  }
}
</script>

<style scoped>
.profile-data-wrapper p {
  color: #767676;
}
</style>