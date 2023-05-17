<template>
  <div class="notice-container">
    <van-nav-bar title="通知" />
    <template v-for="(item, index) in itemList" :key="index">
      <NoticeItem :notice="item" @click="onClickNotice(item)" />
    </template>
  </div>
</template>
<script lang="ts" setup>
import { useRouter } from "vue-router";
import NoticeItem from "./NoticeItem.vue";
import type { INoticeItem } from "@/types";
const itemList = [
  {
    id: 1,
    avatar:
      "https://t7.baidu.com/it/u=3684448567,3293516379&fm=74&app=80&size=f256,256&n=0&f=PNG?sec=1683824400&t=a0ea4eff33d83d86ce3436d445730cc4",
    nickName: "小明",
    time: "13:07",
    content: "测试内容测试内容测试内容",
  },
  {
    id: 2,
    avatar:
      "https://t7.baidu.com/it/u=3684448567,3293516379&fm=74&app=80&size=f256,256&n=0&f=PNG?sec=1683824400&t=a0ea4eff33d83d86ce3436d445730cc4",
    nickName: "小明",
    time: "13:07",
    content: "测试内容测试内容测试内容",
  },
  {
    id: 3,
    avatar:
      "https://t7.baidu.com/it/u=3684448567,3293516379&fm=74&app=80&size=f256,256&n=0&f=PNG?sec=1683824400&t=a0ea4eff33d83d86ce3436d445730cc4",
    nickName: "小明",
    time: "13:07",
    content: "测试内容测试内容测试内容",
  },
  {
    id: 4,
    avatar:
      "https://t7.baidu.com/it/u=3684448567,3293516379&fm=74&app=80&size=f256,256&n=0&f=PNG?sec=1683824400&t=a0ea4eff33d83d86ce3436d445730cc4",
    nickName: "小明",
    time: "13:07",
    content: "测试内容测试内容测试内容",
  },
];

const router = useRouter();
const onClickNotice = (item: INoticeItem) => {
  console.log(item.id);
  router.push({
    name: "Chat",
    params: {
      id: item.id,
    },
  });
};
</script>
<style lang="scss" scoped>
.notice-container {
  height: calc(100vh - 50px);
  overflow-y: auto;
}
</style>
