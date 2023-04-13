<template>
  <div class="post-container">
    <div class="image-container">
      <van-image
        width="100%"
        height="300"
        fit="cover"
        src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
      />
    </div>
    <div class="info-container">
      <div class="username">
        {{ userInfo.username }}
        <van-icon class="gender-icon" name="chat-o" color="#1989fa" />
        <div>
          <van-tag plain type="primary">{{ userInfo.grade }}</van-tag>
        </div>
      </div>
      <div class="target">
        征友目标
        <div class="content">{{ userInfo.target }}</div>
      </div>
      <div class="about">
        关于我
        <div class="font-14">{{ userInfo.about }}</div>
      </div>
      <div class="favor">
        期望中的TA
        <div class="font-14">{{ userInfo.favor }}</div>
      </div>
    </div>
    <div class="operation-container">
      <van-button type="success" size="small">不感兴趣</van-button>
      <van-button type="primary" size="small">我要发布</van-button>
      <van-button type="danger" size="small">感兴趣</van-button>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { ref } from "vue";
const userInfo = ref({});

userInfo.value = {
  username: "王小明",
  gender: "男",
  target: "喜欢桌游",
  grade: "研一",
  about: "今晚西操三国杀...",
  favor: "有三国杀经验...",
};
</script>
<style lang="scss" scoped>
.font-14 {
  font-size: 14px;
}
.post-container {
  box-sizing: border-box;
  height: calc(100vh - 50px);
  padding: 20px 30px 0 30px;
  .image-container {
    :deep(.van-image__img) {
      border-radius: 10px;
    }
  }
  .info-container {
    display: flex;
    justify-content: space-between;
    flex-wrap: wrap;
    align-content: flex-start;
    margin: 15px 0;
    height: calc(100vh - 450px);
    font-size: 20px;
    .username {
      position: relative;
      font-weight: bold;
      flex: 0 0 70%;
      height: 60px;
      .gender-icon {
        position: absolute;
        top: -10px;
        left: 60px;
      }
    }
    .target {
      .content {
        font-size: 14px;
      }
    }
    .about {
      width: 100%;
      height: 100px;
    }
  }
  .operation-container {
    display: flex;
    justify-content: space-evenly;
  }
}
</style>
