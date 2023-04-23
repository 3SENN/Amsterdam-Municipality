<template>
  <h1>Successfully logged out</h1>
</template>

<script>
export default {
  inject: ["authService"],
  name: "SignOut",
  created() {
    this.authService.logout()
    this.$router.push("/sign-in");
  }
}
</script>

<style scoped>

</style>