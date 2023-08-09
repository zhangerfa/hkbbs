<template>
  <div class="chat-panel">
    <van-nav-bar left-arrow title="小明" @click-left="onClickLeft" />
    <div class="chat-content">
      <ChatItem />
    </div>
  </div>
</template>

<script setup lang="ts">
import ChatItem from "./ChatItem.vue";
import { useRoute } from "vue-router";

const route = useRoute();
const onClickLeft = () => {
  history.back();
};
</script>

<style lang="scss" scoped>
.chat-panel {
  .chat-content {
    height: calc(100vh - 46px);
    padding: 10px;
    overflow-y: auto;
    background-color: #f2f2f2;
  }
}
</style>
