<template>
  <div class="profile-container">
    <div class="avatar-container">
      <van-image
        width="100"
        height="100"
        fit="cover"
        src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
      />
      <div class="info-container">
        <div class="username">王小明</div>
      </div>
      <van-button plain type="primary" size="small">设置</van-button>
    </div>
    <van-tabs v-model:active="active">
      <van-tab title="我发布的卡片">内容 1</van-tab>
      <van-tab title="我发布的帖子">内容 2</van-tab>
      <van-tab title="我发布的树洞">内容 3</van-tab>
    </van-tabs>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
const active = ref(0);
</script>
<style lang="scss" scoped>
.profile-container {
  .avatar-container {
    display: flex;
    justify-content: space-between;
    padding: 20px;
    .info-container {
      font-size: 20px;
      font-weight: bold;
      margin-left: 20px;
      line-height: 100px;
    }
  }
}
</style>
