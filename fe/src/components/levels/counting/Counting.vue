<template>
  <header class=" bg-white shadow relative z-20">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        Level 1: Counting Exercise
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

  <h1 class="max-w-4xl mx-auto text-2xl font-bold my-6 bg-white z-10">Route data</h1>
  <div class="max-w-4xl mx-auto bg-white p-16 shadow-lg my-4 relative z-10">
    <div class="grid gap-6 mb-6 lg:grid-cols-2">
      <div>
        <div v-if="!startingPointValidated" class="text-md font-medium text-red-600">Please fill in this field</div>
        <label for="starting-point" class="mb-2 text-sm font-medium text-black-900">Starting Point</label>
        <input type="text" id="starting-point" ref="starting-point"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="startingPoint">
      </div>
      <div>
        <div v-if="!startingDatetimeValidated" class="text-md font-medium text-red-600">Please fill in this field</div>
        <label for="starting-datetime" class="mb-2 text-sm font-medium text-black-900">Starting Datetime</label>
        <input type="datetime-local" id="starting-datetime"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="startingTime">
      </div>

      <div>
        <label for="boarding-stop" class="mb-2 text-sm font-medium text-black-900">If with public transport,
          boarding stop</label>
        <input type="text" id="boarding-stop" ref="boarding-stop"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="boardingStop">
      </div>
      <div>
        <label for="transport-transfer" class="mb-2 text-sm font-medium text-black-900">If with public
          transport, transfer</label>
        <input type="text" id="transport-transfer" ref="transport-transfer"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="transfer">
      </div>

      <div>
        <label for="transport-exit-stop" class="mb-2 text-sm font-medium text-black-900">If with public
          transport, exit stop</label>
        <input type="text" id="transport-exit-stop" ref="transport-exit-stop"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="exitStop">
      </div>
      <div></div>

      <div>
        <div v-if="!destinationValidated" class="text-md font-medium text-red-600">Please fill in this field</div>
        <label for="destination" class="mb-2 text-sm font-medium text-black-900">Destination</label>
        <input type="text" id="destination" ref="destination"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="destination">
      </div>
      <div>
        <div v-if="!destinationDatetimeValidated" class="text-md font-medium text-red-600">Please fill in this field
        </div>
        <label for="destination-datetime" class="mb-2 text-sm font-medium text-black-900">Destination
          Datetime</label>
        <input type="datetime-local" id="destination-datetime"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :disabled="viewCounting" v-model="destinationTime">
      </div>
    </div>

    <button class="text-white bg-red-600 hover:bg-red-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 text-center
       float-right route-save-button" v-on:click="validateInput()" :disabled="viewCounting">
      Save
    </button>
  </div>

  <h1 class="max-w-4xl mx-auto text-2xl font-bold my-10 z-10">Counting</h1>
  <div class="max-w-6xl mx-auto p-16 shadow-lg my-6 bg-white relative z-10">
    <div class="grid grid-cols-2 lg:grid-cols-3 gap-6">
      <div class="mb-6">
        <div class="mb-12"></div>
        <div class="mb-12">Person with cane/crutches</div>
        <div class="mb-12">Person with walker</div>
        <div class="mb-12">Person with a touch stick</div>
        <div class="mb-12">Person with guide/assistance dog</div>
        <div class="mb-12">Person in manual wheelchair (pushed)</div>
        <div class="mb-12">Person in electric wheelchair</div>
        <div class="mb-12">Person with scooter</div>
        <div class="mb-12">Child in pram</div>
        <div v-if="!labelsValidated" class="text-md font-medium text-red-600">Please name the category(s)</div>
        <div class="sm:mb-4 md:mb-6 lg:mb-6 labels" v-for="label in labels" v-bind:key="label.id">
          <input type="text" class="rounded-lg block w-full p-2.5 border border-gray-200 -ml-2.5"
                 :disabled="viewCounting" v-model="label.name" placeholder="Type here to name category">
        </div>
      </div>

      <div class="mb-6">
        <div class="mb-4 title"><p class="font-bold">Number in public spaces</p></div>
        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicCane === 0 ? numberPublicCane : numberPublicCane--" :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberPublicCane
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicCane++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicWalker === 0 ? numberPublicWalker : numberPublicWalker--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberPublicWalker
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicWalker++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicTouchStick === 0 ? numberPublicTouchStick : numberPublicTouchStick--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ numberPublicTouchStick }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicTouchStick++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicDog === 0 ? numberPublicDog : numberPublicDog--" :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberPublicDog
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicDog++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicManualWheelchair === 0 ? numberPublicManualWheelchair : numberPublicManualWheelchair--"
                  :disabled="viewCounting">
            -
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ numberPublicManualWheelchair }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicManualWheelchair++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicElectricWheelchair === 0 ? numberPublicElectricWheelchair : numberPublicElectricWheelchair--"
                  :disabled="viewCounting">
            -
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ numberPublicElectricWheelchair }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicElectricWheelchair++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicScooter === 0 ? numberPublicScooter : numberPublicScooter--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberPublicScooter
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicScooter++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicChildParam === 0 ? numberPublicChildParam : numberPublicChildParam--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ numberPublicChildParam }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberPublicChildParam++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting" v-for="label in labels" v-bind:key="label.name">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="label.publicSpaces === 0 ? label.publicSpaces : label.publicSpaces--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ label.publicSpaces }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="label.publicSpaces++" :disabled="viewCounting">+
          </button>
        </div>
      </div>

      <div class="mb-6 lg:hidden">
        <div class="mb-12"></div>
        <div class="mb-12">Person with cane/crutches</div>
        <div class="mb-12">Person with walker</div>
        <div class="mb-12">Person with a touch stick</div>
        <div class="mb-12">Person with guide/assistance dog</div>
        <div class="mb-12">Person in manual wheelchair (pushed)</div>
        <div class="mb-12">Person in electric wheelchair</div>
        <div class="mb-12">Person with scooter</div>
        <div class="mb-12">Child in pram</div>
        <div v-if="!labelsValidated" class="text-md font-medium text-red-600">Please name the category(s)</div>
        <div class="mb-12" v-for="label in labels" v-bind:key="label.id">
          <input type="text" class="rounded-lg block w-full mb-12 p-2.5 border border-gray-200 -ml-2.5"
                 :disabled="viewCounting" v-model="label.name" placeholder="Type here to name category">
        </div>
      </div>

      <div class="mb-6">
        <div class="mb-4 title-ov"><p class="font-bold">Number in public transport</p></div>
        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVCane === 0 ? numberOVCane : numberOVCane--" :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{ numberOVCane }}</p>
          </div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVCane++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVWalker === 0 ? numberOVWalker : numberOVWalker--" :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{ numberOVWalker }}</p>
          </div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVWalker++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVTouchStick === 0 ? numberOVTouchStick : numberOVTouchStick--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberOVTouchStick
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVTouchStick++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVDog === 0 ? numberOVDog : numberOVDog--" :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{ numberOVDog }}</p>
          </div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVDog++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVManualWheelchair === 0 ? numberOVManualWheelchair : numberOVManualWheelchair--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ numberOVManualWheelchair }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVManualWheelchair++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVElectricWheelchair === 0 ? numberOVElectricWheelchair : numberOVElectricWheelchair--"
                  :disabled="viewCounting">
            -
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ numberOVElectricWheelchair }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVElectricWheelchair++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVScooter === 0 ? numberOVScooter : numberOVScooter--" :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberOVScooter
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVScooter++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVChildParam === 0 ? numberOVChildParam : numberOVChildParam--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>{{
              numberOVChildParam
            }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="numberOVChildParam++" :disabled="viewCounting">+
          </button>
        </div>

        <div class="mb-6 flex flex-row gap-4 counting" v-for="label in labels" v-bind:key="label.name">
          <button class="bg-red-600 hover:bg-red-800 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="label.ov === 0 ? label.ov : label.ov--"
                  :disabled="viewCounting">-
          </button>
          <div class="rounded-lg border-gray-300 border-2 w-20 h-12 grid place-items-center"><p>
            {{ label.ov }}</p></div>
          <button class="bg-green-500 hover:bg-green-700 rounded-xl text-white text-center text-2xl w-14 h-12"
                  v-on:click="label.ov++" :disabled="viewCounting">+
          </button>
        </div>
      </div>
    </div>
    <div v-if="!saveCompleted" class="text-lg font-medium text-green-600">Something went wrong, please try again.</div>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <button v-on:click="addCountingLabel()" class="text-white bg-green-600 hover:bg-green-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 text-center
       float-left lg:mr-24 md:mr-24 add-extra-category-button" :disabled="viewCounting">
        Add extra category
      </button>
      <button v-on:click="saveCounting()" class="text-white bg-green-600 hover:bg-green-800 font-medium rounded-lg text-sm w-full sm:w-auto px-14 py-2.5 text-center
       float-right lg:mr-24 md:mr-24 counting-done-button">
        Done
      </button>
    </div>
  </div>
  <div class="absolute z-0 -top-0 -right-0">
    <img src="../../../assets/svg/counting-shape-1.svg" alt="background">
  </div>
  <div class="absolute z-0 top-80">
    <img src="../../../assets/svg/counting-shape-2.svg" alt="background">
  </div>
</template>

<script>
import {Counting} from '@/models/Counting.js';
import {CountingLabel} from "@/models/CountingLabel";
import {onMounted, onUnmounted} from "vue";

export default {
  name: "Counting",
  inject: ['countingService', 'authService'],

  data() {
    return {
      numberPublicCane: 0,
      numberPublicWalker: 0,
      numberPublicTouchStick: 0,
      numberPublicDog: 0,
      numberPublicManualWheelchair: 0,
      numberPublicElectricWheelchair: 0,
      numberPublicScooter: 0,
      numberPublicChildParam: 0,

      numberOVCane: 0,
      numberOVWalker: 0,
      numberOVTouchStick: 0,
      numberOVDog: 0,
      numberOVManualWheelchair: 0,
      numberOVElectricWheelchair: 0,
      numberOVScooter: 0,
      numberOVChildParam: 0,

      startingPoint: null,
      startingTime: null,
      boardingStop: null,
      transfer: null,
      exitStop: null,
      destination: null,
      destinationTime: null,

      viewCounting: false,
      formValidated: false,
      startingPointValidated: true,
      startingDatetimeValidated: true,
      destinationValidated: true,
      destinationDatetimeValidated: true,
      saveCompleted: true,

      labels: [],
      labelsValidated: true
    }
  },

  async created() {
    if (this.$store.state.counting.countingId > 0) {
      await this.showCountingLevel();
    } else {
      this.setStartDateTime();
    }
  },

  methods: {
    async showCountingLevel() {
      this.viewCounting = true;
      const countingLevel = await this.countingService.getCountingById(this.$store.state.counting.countingId);

      this.startingPoint = countingLevel.startingPoint;
      this.startingTime = countingLevel.startingTime;
      this.boardingStop = countingLevel.boardingStop;
      this.transfer = countingLevel.transfer;
      this.exitStop = countingLevel.exitStop;
      this.destination = countingLevel.destination;
      this.destinationTime = countingLevel.destinationTime;

      this.numberPublicCane = countingLevel.publicSpaces.cane;
      this.numberPublicWalker = countingLevel.publicSpaces.walker;
      this.numberPublicTouchStick = countingLevel.publicSpaces.touchStick;
      this.numberPublicDog = countingLevel.publicSpaces.dog;
      this.numberPublicManualWheelchair = countingLevel.publicSpaces.manualWheelchair;
      this.numberPublicElectricWheelchair = countingLevel.publicSpaces.electricWheelchair;
      this.numberPublicScooter = countingLevel.publicSpaces.scooter;
      this.numberPublicChildParam = countingLevel.publicSpaces.childParam;

      this.numberOVCane = countingLevel.ov.cane;
      this.numberOVWalker = countingLevel.ov.walker;
      this.numberOVTouchStick = countingLevel.ov.touchStick;
      this.numberOVDog = countingLevel.ov.dog;
      this.numberOVManualWheelchair = countingLevel.ov.manualWheelchair;
      this.numberOVElectricWheelchair = countingLevel.ov.electricWheelchair;
      this.numberOVScooter = countingLevel.ov.scooter;
      this.numberOVChildParam = countingLevel.ov.childParam;

      const data = await this.countingService.getLabelsFromCountingId(this.$store.state.counting.countingId);
      for (const label in data) {
        this.labels.push(data[label]);
      }
    },

    validateInput() {
      const startingPoint = this.updateValidation(this.startingPoint, 'startingPoint');
      const startingTime = this.updateValidation(this.startingTime, 'startingTime');
      const destination = this.updateValidation(this.destination, 'destination');
      const destinationTime = this.updateValidation(this.destinationTime, 'destinationTime');

      if (startingPoint && startingTime && destination && destinationTime) {
        this.formValidated = true;
      }
    },

    updateValidation(field, validation) {
      switch (validation) {
        case 'startingPoint':
          if (field == null || field === "") {
            this.startingPointValidated = false;
            return false;
          }
          this.startingPointValidated = true;
          return true;
        case 'startingTime':
          if (field == null || field === "") {
            this.startingDatetimeValidated = false;
            return false;
          }
          this.startingDatetimeValidated = true;
          return true;
        case 'destination':
          if (field == null || field === "") {
            this.destinationValidated = false;
            return false;
          }
          this.destinationValidated = true;
          return true;
        case 'destinationTime':
          if (field == null || field === "") {
            this.destinationDatetimeValidated = false;
            return false;
          }
          this.destinationDatetimeValidated = true;
          return true;
      }
    },

    async saveCounting() {
      if (this.$store.state.counting.countingId > 0) {
        this.$router.push("/levels");
        return;
      }

      this.validateInput();
      this.validateLabels();
      if (this.formValidated && this.labelsValidated) {
        const countingPublic = {
          cane: this.numberPublicCane,
          walker: this.numberPublicWalker,
          touchStick: this.numberPublicTouchStick,
          dog: this.numberPublicDog,
          manualWheelchair: this.numberPublicManualWheelchair,
          electricWheelchair: this.numberPublicElectricWheelchair,
          scooter: this.numberPublicScooter,
          childParam: this.numberPublicChildParam
        };

        const countingOV = {
          cane: this.numberOVCane,
          walker: this.numberOVWalker,
          touchStick: this.numberOVTouchStick,
          dog: this.numberOVDog,
          manualWheelchair: this.numberOVManualWheelchair,
          electricWheelchair: this.numberOVElectricWheelchair,
          scooter: this.numberOVScooter,
          childParam: this.numberOVChildParam
        };

        const score = 400;
        const countingData = new Counting(this.startingPoint, this.startingTime, this.boardingStop, this.transfer,
            this.exitStop, this.destination, this.destinationTime, countingPublic, countingOV, score);

        const requestCounting = await this.countingService.addCounting(countingData, this.authService.getId());
        if (requestCounting.statusCode === 200) {
          for (let label in this.labels) {
            const request = await this.countingService.addLabel(this.labels[label], requestCounting.data.id);

            if (request.statusCode !== 200) {
              this.saveCompleted = false;
              return;
            }

          }
          this.saveCompleted = true;
          this.$router.push('/levels');
        } else {
          this.saveCompleted = false;
        }
      } else {
        window.scrollTo(0, 0);
      }
    },

    setStartDateTime() {
      const currentDateTime = new Date(Date.now());

      const dateString = (currentDateTime.toString().split(" "));

      const month = dateString[1];
      const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
      const monthDate = months.indexOf(month) + 1;
      const fullMonth = monthDate < 10 ? '0' + monthDate : monthDate;

      const timeSplitter = dateString[4].toString().split(":");
      const hour = timeSplitter[0] === '24' ? '00' : timeSplitter[0];

      this.startingTime = `${dateString[3]}-${fullMonth}-${dateString[2]}T${hour}:${timeSplitter[1]}`;
    },

    addCountingLabel() {
      this.labels.push(CountingLabel.copyConstructor(new CountingLabel(null, 0, 0)));
    },

    validateLabels() {
      for (let label in this.labels) {
        this.labelsValidated = !(this.labels[label]['name'] == null || this.labels[label]['name'] === "");
      }
    },

  },

  mounted() {
    const src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyDDDlpvB16od50-02XFUVdLfNSU3KuXBfE&language=en&libraries=places";

    new Promise((resolve, reject) => {
      let script = document.querySelector(`script[src="${src}"]`);

      if (!script) {
        script = document.createElement("script");
        script.src = src;
        script.async = true;
        script.setAttribute("data-status", "loading");
        document.head.append(script);
      }

      if (script.getAttribute("data-status") === "loaded") {
        resolve();
      }

      function onScriptLoad() {
        resolve();
        script.setAttribute("data-status", "loaded");
      }

      function onScriptError() {
        reject();
        script.setAttribute("data-status", "error");
      }

      script.addEventListener("load", onScriptLoad);
      script.addEventListener("error", onScriptError);

    }).then(() => {

      for (let ref in this.$refs) {
        // eslint-disable-next-line no-undef
        const autocomplete = new google.maps.places.Autocomplete(this.$refs[ref], {
          types: ["address"],
          fields: ["address_components"]
        });

        autocomplete.setComponentRestrictions({
          country: ["nl"]
        });

        this.$refs[ref].placeholder = "";

        // eslint-disable-next-line no-undef
        google.maps.event.addListener(autocomplete, "place_changed", () => {
          const place = autocomplete.getPlace()["address_components"];
          let street;
          let city;
          let country;

          place.forEach((component) => {
            component.types.forEach((type) => {
              switch (type) {
                case "route" :
                  street = component.long_name;
                  break;
                case "locality":
                  city = component.long_name;
                  break;
                case "country":
                  country = component.long_name;
                  break;
              }
            });
          });

          const address = `${street}, ${city}, ${country}`;

          switch (ref) {
            case "starting-point":
              this.startingPoint = address;
              break;
            case "boarding-stop":
              this.boardingStop = address;
              break;
            case "transport-transfer":
              this.transfer = address;
              break;
            case "transport-exit-stop":
              this.exitStop = address;
              break;
            case "destination":
              this.destination = address;
              break;
          }
        });
      }
    });
  },
}
</script>

<style scoped>

@media screen and (max-width: 425px) {
  .title {
    margin-top: -1.5rem;
  }

  .title-ov {
    margin-top: -1.8rem;
  }

  .counting, .labels {
    margin-bottom: 3.5rem;
  }

}

@media screen and (min-width: 425px) and (max-width: 575px) {
  .counting, .labels {
    margin-bottom: 2.5rem;
  }
}
</style>