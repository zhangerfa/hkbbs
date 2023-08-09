<template>
  <van-tabbar v-model="active" route>
    <van-tabbar-item replace to="/cardwall" icon="comment-o"
      >卡片墙</van-tabbar-item
    >
    <van-tabbar-item replace to="/treehole" icon="cluster-o"
      >路口</van-tabbar-item
    >
    <van-tabbar-item replace to="/notice" icon="award-o" badge="5"
      >通知</van-tabbar-item
    >
    <van-tabbar-item replace to="/profile" icon="user-circle-o"
      >我的</van-tabbar-item
    >
  </van-tabbar>
</template>

<script lang="ts" setup>
import { ref } from "vue";
const active = ref(0);
</script>
<style lang="scss" scoped></style>
