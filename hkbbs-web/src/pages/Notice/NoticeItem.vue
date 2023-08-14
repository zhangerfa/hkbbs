<script setup lang="ts">
import type { INoticeItem } from "@/types";
interface INoticeProps {
  notice: INoticeItem;
}
const props = defineProps<INoticeProps>();
</script>
<template>
  <div class="notice-item">
    <div class="avatar">
      <van-image
        round
        width="45px"
        height="45px"
        :src="
          notice.avatar
            ? notice.avatar
            : 'https://t7.baidu.com/it/u=3684448567,3293516379&fm=74&app=80&size=f256,256&n=0&f=PNG?sec=1683824400&t=a0ea4eff33d83d86ce3436d445730cc4'
        "
      />
    </div>
    <div class="content">
      <div class="info">
        <div class="name">{{ notice.nickName }}</div>
        <div class="time">{{ notice.time }}</div>
      </div>
      <div class="digest">{{ notice.content }}</div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.notice-item {
  display: flex;
  padding: 10px 15px;
  .avatar {
    width: 60px;
  }
  .content {
    flex: 1;
    font-size: 14px;
    .info {
      display: flex;
      justify-content: space-between;
      margin-bottom: 5px;
      .name {
        font-weight: bold;
      }
      .time {
        color: #ccc;
      }
    }
    .digest {
      color: #ccc;
    }
  }
}
</style>
