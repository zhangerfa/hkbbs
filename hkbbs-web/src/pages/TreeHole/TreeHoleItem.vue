<template>
  <div class="tree-item">
    <div class="title"># 寻找有缘人</div>
    <div class="upload-info">
      <span class="upload-time">2023.04.04</span>
      <span class="upload-author">王小明</span>
    </div>
    <div class="content">
      可以在帖子中点击用户头像或用户名查看用户的基本资料和历史发帖；但无法查看用户历史发布卡片和历史发布树洞
    </div>
    <div class="operation">
      <van-icon name="like-o" /><span class="text">15</span>
      <van-icon name="comment-o" /><span class="text">20</span>
      <van-icon name="star-o" /><span class="text">25</span>
    </div>
  </div>
</template>
<script lang="ts" setup></script>
<style lang="scss" scoped>
.tree-item {
  box-sizing: border-box;
  height: 155px;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 20px;
  background-color: #fff;
  line-height: 1.5;
  .title {
    font-weight: bold;
  }
  .upload-info {
    line-height: 2;
    font-size: 12px;
    color: #ccc;
    .upload-time {
      padding-right: 20px;
    }
  }
  .content {
    font-size: 14px;
    margin-bottom: 10px;
  }
  .operation {
    font-size: 14px;
    line-height: 1;
    color: #ccc;
    .van-icon {
      margin-right: 2px;
    }
    .text {
      font-size: 12px;
      margin-right: 10px;
    }
  }
}
</style>
