<template>
  <div class="input-wrapper">
    <label :for="identifier" class="mb-2 text-sm font-medium text-black-900 font-bold">{{ labelText }}</label>
    <input :type="type" :id="identifier"
           class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5
           focus:outline-none focus:border-green-500"
           :value="value"
           required/>
  </div>
</template>

<script>
export default {
  name: "inputCustom",
  props: ["identifier", "labelText", "type", "value"],
}
</script>

<style scoped>

</style>