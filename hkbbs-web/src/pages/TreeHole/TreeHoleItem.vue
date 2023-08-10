<template>
  <template v-for="item in props.list" :key="item.id">
    <div class="tree-item">
      <div class="title">#{{ item.title }}</div>
      <div class="upload-info">
        <span class="upload-time">{{ item.createTime }}</span>
        <span class="upload-author">{{ item.posterName }}</span>
        <van-image
          width="15"
          height="15"
          :src="item.posterHeaderUrl"
        ></van-image>
      </div>
      <van-text-ellipsis class="content" :content="item.content" rows="3" />
      <div class="operation">
        <van-icon name="like-o" /><span class="text">{{ item.likeNum }}</span>
        <van-icon name="comment-o" /><span class="text">20</span>
        <van-icon name="star-o" /><span class="text">25</span>
      </div>
    </div>
  </template>
</template>
<script lang="ts" setup>
const props = defineProps({
  list: {
    type: Array,
    required: true,
  },
});
</script>
<style lang="scss" scoped>
.tree-item {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  height: 155px;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 10px;
  background-color: #deecea;
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
    flex: 1;
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
