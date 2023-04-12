<template>
  <div class="treehole-container">
    <van-search v-model="keyword" placeholder="请输入搜索关键词" />
    <van-list class="treehole-list">
      <template v-for="item in 5">
        <TreeHoleItem />
      </template>
    </van-list>
    <van-button
      class="publish-btn"
      round
      icon="plus"
      type="success"
    ></van-button>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
import TreeHoleItem from "./TreeHoleItem.vue";
const keyword = ref("");
</script>
<style lang="scss" scoped>
.treehole-container {
  height: calc(100vh - 50px);
  overflow-y: auto;
  background-color: #f8f8f8;
  .treehole-list {
    padding: 10px;
  }
  .publish-btn {
    position: fixed;
    right: 20px;
    bottom: 100px;
  }
}
</style>
