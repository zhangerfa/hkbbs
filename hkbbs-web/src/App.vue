<script setup lang="ts">
import TabBar from "./components/Tabbar/Tabbar.vue";
import {useRoute} from "vue-router";
import {onMounted} from "vue";

const route = useRoute()
console.log(route.path)

</script>

<template>
  <router-view />
  <tab-bar v-show="route.meta.hasTabbar"/>
</template>

<style scoped>
@import "./assets/styles/common.scss";
body,
html,
#app {
  background-color: #f1f1f1;
}
</style>
