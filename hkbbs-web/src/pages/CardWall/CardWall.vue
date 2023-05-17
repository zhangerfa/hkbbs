<template>
  <div class="card-wall-container">
    <!-- 轮播图 -->
    <div class="image-container">
      <!-- <van-image
        width="100%"
        height="300"
        fit="cover"
        src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
      /> -->
      <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
        <van-swipe-item
          v-for="item in cardList[currentIndex].imageUrls"
          :key="item"
        >
          <img :src="item" />
        </van-swipe-item>
      </van-swipe>
    </div>
    <div class="info-container">
      <div class="username">
        <span>{{ cardList[currentIndex].poster.username }}</span>
        <van-icon class="gender-icon" name="chat-o" color="#1989fa" />
      </div>
      <div class="grade">
        硕士 | {{ cardList[currentIndex].grade || "研一" }} -
        {{ cardList[currentIndex].age }} 岁
      </div>
      <div class="target">
        <van-icon
          name="https://fastly.jsdelivr.net/npm/@vant/assets/icon-demo.png"
        />
        <span class="title">征友目标</span>
        <div class="content">{{ cardList[currentIndex].expected }}</div>
      </div>
      <van-divider />
      <div class="about">
        <van-icon
          name="https://fastly.jsdelivr.net/npm/@vant/assets/icon-demo.png"
        />
        <span class="title">关于我</span>
        <div class="content">{{ cardList[currentIndex].aboutMe }}</div>
      </div>
      <van-divider />
      <div class="favor">
        <van-icon
          name="https://fastly.jsdelivr.net/npm/@vant/assets/icon-demo.png"
        />
        <span class="title"> 期望中的TA</span>
        <div class="content">{{ cardList[currentIndex].aboutMe }}</div>
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
import { ref, reactive } from "vue";
import { Card } from "@/utils/api/helper/card";
import type { CardContainsPoster } from "@/utils/schemas";
const userInfo = ref({});

enum GOAL {
  DEFAULT,
  LOVE,
  GAME,
  BOARD_GAME,
  STUDY,
  SPORT,
  TRAVEL,
  WALK,
}

const cardInfo = reactive({
  currentPage: 1,
  pageSize: 10,
  posterId: "0",
  goal: "DEFAULT",
});

const cardList = ref([] as CardContainsPoster[]);
const currentIndex = ref(0);

Card.get(cardInfo).then((res) => {
  console.log(res);
  cardList.value = res.data;
});

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
.title {
  margin-left: 5px;
  font-size: 16px;
  font-weight: bold;
  vertical-align: top;
}
.font-14 {
  font-size: 14px;
}
.card-wall-container {
  box-sizing: border-box;
  height: calc(100vh - 50px);
  overflow-y: auto;
  .image-container {
    :deep(.van-image__img) {
      border-radius: 10px;
    }
    .my-swipe {
      .van-swipe-item {
        height: 300px;
        color: #fff;
        font-size: 20px;
        line-height: 150px;
        text-align: center;
        background-color: #39a9ed;
      }
      img {
        background: no-repeat center center;
      }
    }
  }
  .info-container {
    padding: 25px 20px 0 20px;
    font-size: 20px;
    .username {
      font-weight: bold;
    }
    .grade {
      font-weight: bold;
      margin: 10px 0;
      font-size: 14px;
    }
    .target {
      margin: 10px 0;
      .content {
        font-size: 14px;
        padding-top: 10px;
      }
    }
    .about {
      margin: 10px 0;
      .content {
        font-size: 14px;
        padding-top: 10px;
      }
    }
    .favor {
      margin: 10px 0;
      .content {
        font-size: 14px;
        padding-top: 10px;
      }
    }
  }
  .operation-container {
    display: flex;
    justify-content: space-evenly;
  }
}
</style>
