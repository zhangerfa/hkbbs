<script setup lang="ts">
import Tabbar from "./components/Tabbar/Tabbar.vue";
</script>

<template>
  <router-view />
  <Tabbar />
</template>

<style scoped>
@import "./assets/styles/common.scss";
body,
html,
#app {
  background-color: #f1f1f1;
}
</style>
