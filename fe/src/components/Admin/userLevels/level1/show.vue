<template>
  <header class=" bg-white shadow relative z-20">
    <div class="mx-auto py-6 px-4 flex flex-wrap gap-5 justify-between items-center sm:px-6 lg:px-8 ">
      <h2 class="text-xl text-gray-800 leading-tight">
        View Level 1: {{ countingData.userId.firstName }} {{ countingData.userId.lastName }}
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
        <label for="starting-point" class="mb-2 text-sm font-medium text-black-900">Starting Point</label>
        <p id="starting-point" class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5">
          {{ countingData.startingPoint }}
        </p>
      </div>
      <div>
        <label for="starting-datetime" class="mb-2 text-sm font-medium text-black-900">Starting Datetime</label>
        <input type="datetime-local" id="starting-datetime"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :value="countingData.startingTime"
               disabled>
      </div>

      <div>
        <label for="boarding-stop" class="mb-2 text-sm font-medium text-black-900">If with public transport,
          boarding stop</label>
        <p id="boarding-stop"
           :class="countingData.boardingStop ? 'text-gray-900' : 'text-blue-500 font-bold'"
           class="border border-gray-300 text-sm rounded-lg block w-full p-2.5">
          {{ countingData.boardingStop ? countingData.boardingStop : 'Not used' }}
        </p>
      </div>
      <div>
        <label for="transport-transfer" class="mb-2 text-sm font-medium text-black-900">If with public
          transport, transfer</label>
        <p id="transport-transfer"
           :class="countingData.transfer ? 'text-gray-900' : 'text-blue-500 font-bold'"
           class="border border-gray-300 text-sm rounded-lg block w-full p-2.5">
          {{ countingData.transfer ? countingData.transfer : 'Not used' }}
        </p>
      </div>

      <div>
        <label for="transport-exit-stop" class="mb-2 text-sm font-medium text-black-900">If with public
          transport, exit stop</label>
        <p id="transport-exit-stop"
           :class="countingData.exitStop ? 'text-gray-900' : 'text-blue-500 font-bold'"
           class="border border-gray-300  text-sm rounded-lg block w-full p-2.5">
          {{ countingData.exitStop ? countingData.exitStop : 'Not used' }}
        </p>
      </div>
      <div>
        <label for="destination" class="mb-2 text-sm font-medium text-black-900">Destination</label>
        <p id="destination" class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5">
          {{ countingData.destination }}
        </p>
      </div>
      <div>
        <label for="destination-datetime" class="mb-2 text-sm font-medium text-black-900">Destination Datetime</label>
        <input type="datetime-local" id="destination-datetime"
               class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5"
               :value="countingData.destinationTime"
               disabled>
      </div>
      <div>
        <label for="score" class="mb-2 text-sm font-medium text-black-900">Points achieved</label>
        <p id="score" class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5">
          {{ countingData.score }}
        </p>
      </div>
    </div>
  </div>

  <h1 class="max-w-4xl mx-auto text-2xl font-bold my-10 z-10">Counting result</h1>

  <div class="max-w-4xl mx-auto bg-white shadow-lg my-4 relative z-10">
    <div class="p-10 overflow-x-auto relative">
      <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-gray-700 uppercase">
        <tr>
          <th scope="col"
              class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">
          </th>
          <th scope="col"
              class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Public
            transport
          </th>
          <th scope="col"
              class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">Public
            spaces
          </th>
          <th scope="col"
              class="px-6 py-3 border-b-2 border-gray-300 text-left leading-4 text-blue-500 tracking-wider">
          </th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(value, index) of ov" :key="index"
            class="bg-white border-b transition duration-500 hover:bg-gray-200 ">
          <td class="py-4 px-6">
            {{ value[0] }}
          </td>
          <td class="py-4 px-6">
            {{ value[1] }}
          </td>
          <td class="py-4 px-6">
            {{ publicSpaces[index][1] }}
          </td>
          <td v-if="index === ov.length-1 && countingData.countingLabels.length === 0" class="py-4 px-6">
            +
          </td>
        </tr>
        </tbody>
        <tbody v-if="countingData.countingLabels.length > 0">
        <tr v-for="(value, index) of countingData.countingLabels" :key="index">
          <td class="py-4 px-6">
            {{ value.name }}
          </td>
          <td class="py-4 px-6">
            {{ value.ov }}
          </td>
          <td class="py-4 px-6">
            {{ value.publicSpaces }}
          </td>
          <td v-if="index === countingData.countingLabels.length-1" class="py-4 px-6">
            +
          </td>
        </tr>
        </tbody>
        <tfoot>
        <tr class="text-green-500 bg-white border-b border-t-4 transition duration-500 hover:bg-gray-200 ">
          <td class="py-4 px-6 font-bold">
            total
          </td>
          <td class="py-4 px-6 font-bold">
            {{ getOvTotal() }}
          </td>
          <td class="py-4 px-6 font-bold">
            {{ getPublicSpacesTotal() }}
          </td>
        </tr>
        </tfoot>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  inject: ["countingService"],
  name: "view",
  data() {
    return {
      countingData: "",
      ov: [],
      publicSpaces: []
    }
  },
  async created() {
    // Calling function to load the data
    await this.loadData()
  },
  methods: {
    async loadData() {
      try {
        // Making call to get the counting data by id
        this.countingData = await this.countingService.getCountingById(this.getQueryId())
        // Storing the ov data from the response
        this.ov = Object.entries(this.countingData.ov);
        // Storing the publicSpaces data from the response
        this.publicSpaces = Object.entries(this.countingData.publicSpaces);
      } catch (error) {
        // Prints error if any
        console.error(error)
      }
    },
    getOvTotal() {
      // Stops function if no ov data
      if (this.ov.length <= 0) return
      let sum = 0
      // Calculating the total number of counted in ov from the default labels
      for (let element of this.ov) sum += element[1]

      // Calculating the total number of counted in ov from the custom labels
      if (this.countingData.countingLabels.length > 0) {
        for (let countingLabel of this.countingData.countingLabels) {
          sum += countingLabel.ov
        }
      }
      // Returns the total number of counted in ov
      return sum
    },
    getPublicSpacesTotal() {
      // Stops function if no ov data
      if (this.publicSpaces.length <= 0) return
      let sum = 0
      // Calculating the total number of counted in publicSpaces from the default labels
      for (let element of this.publicSpaces) sum += element[1]

      // Calculating the total number of counted in publicSpaces from the custom labels
      if (this.countingData.countingLabels.length > 0) {
        for (let countingLabel of this.countingData.countingLabels) {
          sum += countingLabel.publicSpaces
        }
      }
      // Returns the total number of counted in publicSpaces
      return sum
    },

    /**
     * @returns {number|string|*} The played counting level id
     */
    getQueryId() {
      return this.$route.query.countingId
    }
  }
}
</script>
