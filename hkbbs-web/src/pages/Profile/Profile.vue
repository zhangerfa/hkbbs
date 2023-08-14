<template>
  <div class="profile-container">
    <div class="info-container">
      <van-image
        round
        width="100"
        height="100"
        fit="cover"
        src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg"
        @click="showImgPreview"
      />
      <div class="username">王小明</div>
    </div>
    <van-divider />
    <van-cell :border="false" title="卡片" is-link to="/editUserInfo">
      <template #icon>
        <van-icon class="config-icon" name="friends-o" />
      </template>
    </van-cell>
    <van-cell :border="false" title="帖子" is-link to="/editUserInfo">
      <template #icon>
        <van-icon class="config-icon" name="friends-o" />
      </template>
    </van-cell>
    <van-cell :border="false" title="树洞" is-link to="/editUserInfo">
      <template #icon>
        <van-icon class="config-icon" name="friends-o" />
      </template>
    </van-cell>
    <van-cell :border="false" title="个人信息" is-link to="/editUserInfo">
      <template #icon>
        <van-icon class="config-icon" name="friends-o" />
      </template>
    </van-cell>
    <van-cell :border="false" title="账号与安全" is-link to="/editUserInfo">
      <template #icon>
        <van-icon class="config-icon" name="friends-o" />
      </template>
    </van-cell>
    <van-cell
      :border="false"
      title="退出登录"
      is-link
      @click="showLogoutAction = true"
    >
      <template #icon>
        <van-icon
          style="font-size: 20px; margin-right: 10px"
          name="friends-o"
        />
      </template>
    </van-cell>
    <van-action-sheet
      teleport="body"
      v-model:show="showLogoutAction"
      :actions="logoutActions"
      cancel-text="取消"
      description="确认退出登录吗"
      close-on-click-action
    />
  </div>
</template>
<script lang="ts" setup>
import { showImagePreview, showToast } from "vant";
import { ref } from "vue";
import { useUserStore } from "@/stores/modules/user";
const active = ref(0);
const showLogoutAction = ref(false);

const userAvatar = ref("https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg");
const showImgPreview = () => {
  showImagePreview([userAvatar.value]);
};

const userStore = useUserStore();
const logoutActions = [
  {
    name: "退出登录",
    color: "#ee0a24",
    callback: () => {
      userStore.logout();
      showToast("退出成功");
    },
  },
];
</script>
<style lang="scss" scoped>
.profile-container {
  // height: calc(100vh - 50px);
  display: flex;
  flex-direction: column;
  margin-top: 30%;
  padding: 20px;
  .info-container {
    text-align: center;
    font-size: 18px;
    font-weight: bold;
  }
  .config-icon {
    position: relative;
    top: 3px;
    font-size: 20px;
    margin-right: 10px;
  }
}
</style>
