<template>
  <nav class="relative flex flex-wrap items-center justify-between px-2 py-3 bg-white z-50 relative">
    <div class="container px-4 mx-auto flex flex-wrap items-center justify-between">
      <div class="w-full relative flex justify-between lg:w-auto  px-4 lg:static lg:block lg:justify-start">
        <a class="" href="">
          <img src="../../assets/img/logo.png" class="mr-3 h-10" alt="Gemeente Amsterdam Logo">
        </a>
        <button
            class="cursor-pointer py-1 border border-solid border-transparent rounded bg-transparent block lg:hidden outline-none focus:outline-none"
            type="button" v-on:click="toggleNavbar()">
          <svg xmlns="http://www.w3.org/2000/svg" width="36" height="22" viewBox="0 0 36 22" fill="none">
            <line x1="2" y1="2" x2="34" y2="2" stroke="#333333" stroke-width="4" stroke-linecap="round"/>
            <line x1="2" y1="11" x2="34" y2="11" stroke="#333333" stroke-width="4" stroke-linecap="round"/>
            <line x1="2" y1="20" x2="34" y2="20" stroke="#333333" stroke-width="4" stroke-linecap="round"/>
          </svg>
        </button>
      </div>
      <div v-bind:class="{'hidden': !showMenu, 'flex': showMenu}"
           class="lg:flex lg:flex-grow items-center mt-8 lg:mt-0">
        <ul class="flex flex-col lg:flex-row list-none ml-auto gap-4">
          <li class="nav-item mt-auto mb-auto m-2">
            <router-link to="/" exact-active-class="bg-municipality-500 text-white"
                         class="rounded p-1 px-3.5 hover:bg-municipality-700 hover:text-white ease-in-out duration-500">
              Home
            </router-link>
          </li>
          <li class="nav-item mt-auto mb-auto m-2">
            <router-link to="/leaderboards" exact-active-class="bg-municipality-500 text-white"
                         class="rounded p-1 px-3.5 hover:bg-municipality-700 hover:text-white ease-in-out duration-500">
              Leaderboard
            </router-link>
          </li>
          <li v-if="isLoggedIn" class="nav-item mt-auto mb-auto m-2">
            <router-link to="/levels" exact-active-class="bg-municipality-500 text-white"
                         class="rounded p-1 px-3.5 hover:bg-municipality-700 hover:text-white ease-in-out duration-500">
              Levels
            </router-link>
          </li>
<!--          <li v-if="isLoggedIn" class="nav-item mt-auto mb-auto m-2">-->
<!--            <router-link to="/events" exact-active-class="bg-municipality-500 text-white"-->
<!--                         class="rounded p-1 px-3.5 hover:bg-municipality-700 hover:text-white ease-in-out duration-500">-->
<!--              Events-->
<!--            </router-link>-->
<!--          </li>-->
          <li class="m-2 relative z-50">
            <div v-if="isLoggedIn">
              <div v-if="isAdmin">
                <sub-menu-admin></sub-menu-admin>
              </div>
              <div v-else>
                <SubMenuUser/>
<!--                <sub-menu-user></sub-menu-user>-->
              </div>
            </div>
            <div v-else>
              <sub-menu-default></sub-menu-default>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>

import SubMenuDefault from "@/components/includes/submenu/SubMenuDefault";
import SubMenuUser from "@/components/includes/submenu/SubMenuUser";
import SubMenuAdmin from "@/components/includes/submenu/SubMenuAdmin";
export default {
  inject: ["authService"],
  name: "NavBar",
  components: {
    SubMenuDefault,
    SubMenuUser,
    SubMenuAdmin
  },
  data() {
    return {
      isActive: false,
      showMenu: false,
      isLoggedIn: this.authService.getId(),
      isAdmin: this.authService.isAdmin(),
    }
  },
  methods: {
    toggleNavbar: function () {
      this.showMenu = !this.showMenu;
    },
  }
}
</script>
<style scoped>
nav {
  box-shadow: 0 5px 10px 5px rgba(0, 0, 0, 0.05);
}

</style>