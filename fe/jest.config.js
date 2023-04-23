module.exports = {
  testEnvironment: 'jsdom',
  resetMocks: false,
  preset: '@vue/cli-plugin-unit-jest',
  setupFilesAfterEnv: ["jest-expect-message"],
  moduleFileExtensions: [
    "js",
    "json",
    "vue"
  ],
  transform: {
    '^.+\\.(js|jsx|ts|tsx|mjs)$': 'babel-jest',
    ".*\\.(vue)$": "@vue/vue3-jest",
  },
  moduleNameMapper: {
    "^@/(.*)$": "<rootDir>/src/$1",
    "/images/(.*)$": "<rootDir>/public/images/$1",
  },
}
