<template>
  <div class="chat-item">
    <div class="time">13:16</div>
    <div class="content">
      <div class="avatar">
        <van-image
          round
          width="45px"
          height="45px"
          src="https://t7.baidu.com/it/u=3684448567,3293516379&fm=74&app=80&size=f256,256&n=0&f=PNG?sec=1683824400&t=a0ea4eff33d83d86ce3436d445730cc4"
        />
      </div>
      <div class="text">欢迎来到Hkbbs😄,查收这份新人使用指南吧</div>
    </div>
  </div>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
.chat-item {
  .time {
    font-size: 12px;
    text-align: center;
    color: #ccc;
    margin-bottom: 10px;
  }
  .content {
    display: flex;
    .avatar {
      width: 60px;
    }
    .text {
      padding: 10px;
      width: 240px;
      background-color: #fff;
      border-radius: 10px;
    }
  }
}
</style>
