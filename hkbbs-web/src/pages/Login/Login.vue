<template>
  <div class="login-container">
    <p class="title">Hkbbs登录页</p>
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="stuId"
          name="学号"
          label="学号"
          placeholder="请填写学号"
          :rules="[{ required: true, message: '请填写学号' }]"
        />
        <van-field
          v-model="password"
          type="password"
          name="密码"
          label="密码"
          placeholder="请填写密码"
          :rules="[{ required: true, message: '请填写密码' }]"
        />
      </van-cell-group>
      <div>
        <van-checkbox v-model="rememberMe" shape="square" class="remember-text"
          >记住我</van-checkbox
        >
      </div>

      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { showSuccessToast, showFailToast } from "vant";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/modules/user";
const router = useRouter();
const userStore = useUserStore();
const rememberMe = ref(false);
const stuId = ref("");
const password = ref("");

const onSubmit = () => {
  const data = {
    rememberMe: rememberMe.value,
    stuId: stuId.value,
    password: password.value,
  };
  userStore.login(data).then((res) => {
    if (res.code === 0) {
      showSuccessToast("登录成功");
      router.push("/");
    } else {
      showFailToast("失败文案");
    }
  });
};
</script>

<style lang="scss" scoped>
.login-container {
  padding-top: 120px;
  width: 100vw;
  .title {
    text-align: center;
    font-size: 30px;
    font-weight: bold;
  }
  .remember-text {
    // fix：如果未设置宽度，flex布局会导致点击空白区域仍可导致复选框选中的bug
    width: 100px;
    padding: 10px 25px 0 25px;
  }
}
</style>
