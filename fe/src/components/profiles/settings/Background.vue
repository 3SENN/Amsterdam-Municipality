<template>
  <div class="container mx-auto">
    <div class="top-0 right-0 absolute hidden md:block">
      <img :src="subtractVector" alt="Subtract Vector">
    </div>

    <div class="background flex flex-col sm:flex-row justify-between mt-16 m-3">
      <div class="info">
        <h1 class="text-3xl mb-4"><strong>Background settings</strong></h1>
        <p class="mb-3 text-subtext-500">Update your account background for more fun and personal touch.</p>
      </div>
    </div>

    <div class="backgrounds flex flex-col justify-between mt-16 m-3">
      <div class="backgrounds">
        <h1 class="text-3xl mb-4 font-extrabold">Unlockable</h1>
      </div>

      <div class="backgrounds-container">
        <div class="flex flex-row flex-wrap gap-4">
          <div class="selectable-background">
            <img src="../../../assets/img/profiles/test-background2.png" alt="selected-background"/>
          </div>
          <div class="selectable-background">
            <img src="../../../assets/img/profiles/test-background.png" class="selected-background" alt="test-background"/>
          </div>
          <div class="selectable-background">
            <img src="../../../assets/img/profiles/test-background.png" alt="test-background"/>
          </div>
          <div class="selectable-background">
            <img src="../../../assets/img/profiles/test-background.png" alt="test-background"/>
          </div>
        </div>
      </div>
    </div>

    <div class="color-mode flex flex-col justify-between mt-16 m-3">
      <div class="color-mode">
        <h1 class="text-3xl mb-4 font-extrabold">Standard</h1>
      </div>

      <div class="color-mode-container">
        <div class="flex flex-row flex-wrap gap-4">
          <div class="color-mode-box">
            <div class="mode-box border-gray-400 bg-gray-300">
              <p class="text-center text-black">Light-Mode</p>
            </div>
          </div>

          <div class="color-mode-box">
            <div class="mode-box border-black bg-gray-800">
              <p class="text-center text-white">Dark-Mode</p>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import subtractVector from '@/assets/svg/vorm1.svg'

export default {
  name: "Background",
  data() {
    return {
      subtractVector: subtractVector
    }
  }
}
</script>

<style scoped>
.selectable-background {
  @apply flex;
  @apply justify-center;
  @apply bg-white;
  @apply drop-shadow-lg;
  @apply rounded-lg;
  @apply p-4;
  @apply w-72;
}

.selectable-background img {
  @apply basis-1/2;
  @apply h-64;
}

.color-mode-box {
  @apply flex;
  @apply justify-center;
  @apply shadow-lg;
  @apply rounded-lg;
  @apply p-4;
  @apply w-72;
}

.mode-box {
  @apply flex;
  @apply justify-center;
  @apply items-center;
  @apply border-4;
  @apply p-4;
  @apply rounded-lg;
  @apply w-64;
  @apply h-64;
}

.mode-box p {
  @apply font-bold;
  @apply text-2xl;
}

/*Active background, add to img*/
.selected-background {
  @apply border-4;
  @apply border-red-400;
  @apply rounded-xl;
}
</style>