<template>
  <div class="card-wall-container" v-if="cardList[currentIndex]">
    <!-- 轮播图 -->
    <div class="image-container">
      <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
        {{ cardList[currentIndex].imageUrls }}
        <template v-if="cardList[currentIndex].imageUrls">
          <van-swipe-item
            v-for="item in cardList[currentIndex]?.imageUrls"
            :key="item"
          >
            <van-image
              width="100%"
              height="100%"
              fit="cover"
              position="center"
              :src="item"
            />
          </van-swipe-item>
        </template>
        <template v-else>
          <van-swipe-item v-for="item in 5" :key="item">
            <van-image
              width="100%"
              height="100%"
              fit="cover"
              position="center"
              src="https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg"
            />
          </van-swipe-item>
        </template>
      </van-swipe>
    </div>
    <div class="info-container">
      <div class="header">
        <div class="left">
          <div class="username">
            <span>{{ cardList[currentIndex]?.poster?.username }}</span>
            <van-icon class="gender-icon" name="chat-o" color="#1989fa" />
          </div>
          <div class="grade">
            硕士 | {{ cardList[currentIndex]?.grade || "研一" }} -
            {{ cardList[currentIndex]?.age || 20 }} 岁
          </div>
        </div>
        <div class="target">
          <van-icon
            name="https://fastly.jsdelivr.net/npm/@vant/assets/icon-demo.png"
          />
          <span class="title">征友目标</span>
          <div class="content">
            {{ goalMap[cardList[currentIndex].goal].label }}
          </div>
        </div>
      </div>

      <van-divider />
      <div class="about">
        <van-icon
          name="https://fastly.jsdelivr.net/npm/@vant/assets/icon-demo.png"
        />
        <span class="title">自我介绍</span>
        <div class="content">{{ cardList[currentIndex]?.aboutMe }}</div>
      </div>
      <van-divider />
      <div class="favor">
        <van-icon
          name="https://fastly.jsdelivr.net/npm/@vant/assets/icon-demo.png"
        />
        <span class="title"> 期望中的TA</span>
        <div class="content">{{ cardList[currentIndex]?.expected }}</div>
      </div>
    </div>

    <div class="operation-container">
      <van-button type="success" size="small" @click="handlelikeType(1)"
        >感兴趣</van-button
      >
      <van-button type="primary" size="small" @click="handlePublishCard"
        >我要发布</van-button
      >
      <van-button type="warning" size="small" @click="handlelikeType(0)"
        >不感兴趣</van-button
      >
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive, onMounted, watch } from "vue";
import { Card } from "@/utils/api/helper/card";
import type { CardContainsPoster } from "@/utils/schemas";

import { PAGE_SIZE, GOAL_ARRAY } from "@/constant/CardWall";
import { showToast } from "vant";
import { useRouter } from "vue-router";
const cardInfo = reactive({
  currentPage: 1,
  pageSize: PAGE_SIZE,
  posterId: "0",
  goal: "DEFAULT",
});
const router = useRouter();
const cardList = ref<CardContainsPoster[]>([]);
// 当前展示的索引
const currentIndex = ref(0);
const goalMap = ref(GOAL_ARRAY);

watch(currentIndex, (nv) => {
  if (nv == PAGE_SIZE) {
    // 重新请求5张卡片
    currentIndex.value = 0;
    getCardList();
  }
});

// 可能在DOM挂载完后才拿到数据
const getCardList = () => {
  Card.get(cardInfo).then((res) => {
    cardList.value = res;
  });
};

const handlelikeType = (type: number) => {
  currentIndex.value++;
  if (type === 1) {
    showToast({
      message: "已添加感兴趣",
      icon: "like-o",
    });
  } else {
    showToast({
      message: "已添加不感兴趣",
      icon: "dislike-o",
    });
  }
};

const handlePublishCard = () => {
  router.push({
    name: "Post",
    params: {
      type: "card",
    },
  });
};

onMounted(() => {
  getCardList();
});
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
    padding: 25px 20px 10px 20px;
    font-size: 20px;
    .header {
      display: flex;
      align-items: center;
      .left {
        flex: 1;
        .username {
          font-weight: bold;
        }
        .grade {
          font-weight: bold;
          margin: 10px 0;
          font-size: 14px;
        }
      }
      .target {
        text-align: center;
        .content {
          font-size: 14px;
          padding-top: 10px;
        }
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
