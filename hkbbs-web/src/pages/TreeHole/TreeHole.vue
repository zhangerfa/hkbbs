<template>
  <div class="treehole-container">
    <van-tabs v-model:active="active">
      <van-tab title="帖子">
        <van-search v-model="keyword" placeholder="请输入搜索关键词" />
        <van-list class="treehole-list">
          <TreeHoleItem :list="postList" />
        </van-list>
        <van-button
          class="publish-btn"
          round
          icon="plus"
          type="success"
        ></van-button>
      </van-tab>
      <van-tab title="树洞">
        <van-search v-model="keyword" placeholder="请输入搜索关键词" />
        <van-list class="treehole-list">
          <TreeHoleItem :list="treeholeList" />
        </van-list>
        <van-button
          class="publish-btn"
          round
          icon="plus"
          type="success"
        ></van-button>
      </van-tab>
    </van-tabs>
  </div>
</template>
<script lang="ts" setup>
import { ref, reactive, watch } from "vue";
import TreeHoleItem from "./TreeHoleItem.vue";
import { Hole } from "@/utils/api/helper/hole";
import { getStuId } from "@/utils/mock/helper/custom";
import { Post } from "@/utils/api/helper/post";

const active = ref(0);
interface IProps {
  type: number;
}
const props = defineProps<IProps>();
const pageParams = reactive({
  currentPage: 1,
  pageSize: 10,
});
const postList = ref<any[]>([]);
const treeholeList = ref<any[]>([]);
const getList = async () => {
  const res = await Post.getList({
    stuId: "0",
    ...pageParams,
    type: active.value,
  });
  if (active.value === 0) {
    postList.value = res as any;
  } else {
    treeholeList.value = res as any;
  }
  console.log(res);
};

getList();
watch(active, () => {
  getList();
});
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
