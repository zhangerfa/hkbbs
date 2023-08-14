<template>
  <van-nav-bar
    title="发布帖子"
    left-text="返回"
    left-arrow
    @click-left="onClickLeft"
  />
  <div class="card-post-container">
    <van-form @submit="onSubmit">
      <!-- <van-cell-group inset> -->
      <van-field
        v-model="cardParams.goal"
        is-link
        readonly
        name="picker"
        label="征友目标"
        placeholder="点击选择征友目标"
        :rules="[{ required: true, message: '请选择征友目标' }]"
        @click="showPicker = true"
      />
      <van-popup v-model:show="showPicker" position="bottom">
        <van-picker
          :columns="goalMap"
          @confirm="onConfirm"
          :columns-field-names="customFieldName"
          @cancel="showPicker = false"
        />
      </van-popup>
      <van-field
        v-model="cardParams.aboutMe"
        rows="2"
        label="关于我"
        type="textarea"
        maxlength="50"
        placeholder="请输入自我介绍"
        :rules="[{ required: true, message: '请输入自我介绍' }]"
        show-word-limit
      />
      <van-field
        v-model="cardParams.expected"
        rows="2"
        autosize
        label="征友要求"
        type="textarea"
        maxlength="50"
        placeholder="请输入征友要求"
        :rules="[{ required: true, message: '请输入征友要求' }]"
        show-word-limit
      />
      <!-- <div style="padding: 10px">
        <div style="font-size: 14px; margin-bottom: 10px">
          上传一些吸引他人的图片吧
        </div>
        <van-uploader
          v-model="imgList"
          :after-read="afterRead"
          :rules="[{ validator, message: '请上传您的图片' }]"
        />
      </div> -->
      <van-field
        name="uploader"
        label="上传图片"
        accept="image/*"
        :rules="[{ required: true, message: '请上传您的图片' }]"
      >
        <template #input>
          <van-uploader v-model="imgList" :after-read="afterRead" />
        </template>
      </van-field>
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { GOAL_ARRAY } from "@/constant/CardWall";
import { Card } from "@/utils/api/helper/card";
import type { UploaderFileListItem } from "vant";
import { useRouter } from "vue-router";
import { showSuccessToast } from "vant";
const goalMap = ref(GOAL_ARRAY);
const cardParams = reactive({
  aboutMe: "",
  expected: "",
  goal: "",
  images: new FormData(),
});
const customFieldName = ref({
  text: "label",
  value: "type",
});
const showPicker = ref(false);

const router = useRouter();
const onClickLeft = () => {
  router.back();
};
const clearParams = () => {
  cardParams.aboutMe = "";
  cardParams.expected = "";
  cardParams.goal = "";
  cardParams.images = new FormData();
};
const onSubmit = () => {
  Card.publish(cardParams).then((res) => {
    if (res.code === 0) {
      showSuccessToast("发布成功");
      // 清空表单
      clearParams();
      // onClickLeft();
    }
  });
};
const onConfirm = ({ selectedOptions }) => {
  showPicker.value = false;
  cardParams.goal = selectedOptions[0].label;
};

const imgList = ref([] as UploaderFileListItem[]);

const formData = new FormData();
const afterRead = (img: UploaderFileListItem) => {
  formData.append("file", img.file);
  // formData.forEach((value, key) => {
  //   console.log(`key ${key}: value ${value}`);
  // });
  cardParams.images = formData;
};
</script>

<style lang="scss" scoped>
.card-post-container {
  padding: 20px;
  height: 80vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
