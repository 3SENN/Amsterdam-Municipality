<template>
  <Menu as="div" class="relative inline-block text-left">
    <div>
      <MenuButton class="inline-flex w-full justify-center rounded-md  px-4 hover:bg-opacity-30">
        <svg xmlns="http://www.w3.org/2000/svg" class="w-10 h-10" viewBox="0 0 58 58" fill="none">
          <path
              d="M57 29C57 44.464 44.464 57 29 57C13.536 57 1 44.464 1 29C1 13.536 13.536 1 29 1C44.464 1 57 13.536 57 29Z"
              fill="#FEF4F4"/>
          <path
              d="M33.0625 21.5C33.0625 23.7437 31.2436 25.5625 29 25.5625C26.7564 25.5625 24.9375 23.7437 24.9375 21.5C24.9375 19.2563 26.7564 17.4375 29 17.4375C31.2436 17.4375 33.0625 19.2563 33.0625 21.5Z"
              fill="#FEF4F4"/>
          <path
              d="M36.8125 35.25C36.8125 36.5945 36.0511 37.9002 34.6423 38.9065C33.2352 39.9116 31.2434 40.5625 29 40.5625C26.7566 40.5625 24.7648 39.9116 23.3577 38.9065C21.9489 37.9002 21.1875 36.5945 21.1875 35.25C21.1875 33.9055 21.9489 32.5998 23.3577 31.5935C24.7648 30.5884 26.7566 29.9375 29 29.9375C31.2434 29.9375 33.2352 30.5884 34.6423 31.5935C36.0511 32.5998 36.8125 33.9055 36.8125 35.25Z"
              fill="#FEF4F4"/>
          <path
              d="M57 29C57 44.464 44.464 57 29 57C13.536 57 1 44.464 1 29C1 13.536 13.536 1 29 1C44.464 1 57 13.536 57 29Z"
              stroke="#EC0000" stroke-width="1.5"/>
          <path
              d="M33.0625 21.5C33.0625 23.7437 31.2436 25.5625 29 25.5625C26.7564 25.5625 24.9375 23.7437 24.9375 21.5C24.9375 19.2563 26.7564 17.4375 29 17.4375C31.2436 17.4375 33.0625 19.2563 33.0625 21.5Z"
              stroke="#EC0000" stroke-width="1.5"/>
          <path
              d="M36.8125 35.25C36.8125 36.5945 36.0511 37.9002 34.6423 38.9065C33.2352 39.9116 31.2434 40.5625 29 40.5625C26.7566 40.5625 24.7648 39.9116 23.3577 38.9065C21.9489 37.9002 21.1875 36.5945 21.1875 35.25C21.1875 33.9055 21.9489 32.5998 23.3577 31.5935C24.7648 30.5884 26.7566 29.9375 29 29.9375C31.2434 29.9375 33.2352 30.5884 34.6423 31.5935C36.0511 32.5998 36.8125 33.9055 36.8125 35.25Z"
              stroke="#EC0000" stroke-width="1.5"/>
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
          class="absolute md:right-0 mt-2 w-56 origin-top-right divide-y rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
        <div class="px-1 py-1">
          <MenuItem>
            <router-link
                exact-active-class="bg-municipality-500 text-white"
                class="group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm
                  ease-in-out duration-500 hover:bg-municipality-700 hover:text-white"
                to="/sign-in">
              Sign in
            </router-link>
          </MenuItem>
          <MenuItem>
            <router-link
                exact-active-class="bg-municipality-500 text-white"
                class="group flex items-center rounded-md px-2 py-2 ml-2 mr-2 text-sm
                  ease-in-out duration-500 hover:bg-municipality-700 hover:text-white"
                to="/sign-up">
              Sign up
            </router-link>
          </MenuItem>
        </div>
      </MenuItems>
    </transition>
  </Menu>
</template>

<script>
import {Menu, MenuButton, MenuItems, MenuItem} from '@headlessui/vue'

export default {
  name: "SubMenuDefault",
  components: {
    Menu,
    MenuButton,
    MenuItems,
    MenuItem
  }
}
</script>

<style scoped>

</style>