<template>
  <div class="login-container">
    <p class="title">Hkbbs登录</p>
    <van-form label-width="40" ref="formRef">
      <van-cell-group inset>
        <van-field
          v-model="userForm.stuId"
          name="学号"
          label="学号"
          placeholder="请填写学号"
          :rules="[{ required: true, message: '请填写学号' }]"
        />
        <van-field
          v-model="userForm.password"
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
        <van-button
          block
          type="primary"
          native-type="submit"
          style="margin-bottom: 10px"
          @click="handleUserLogin"
        >
          登 录
        </van-button>
        <van-button plain type="primary" block> 注册 </van-button>
      </div>
    </van-form>
  </div>
</template>

<script lang="ts" setup>
import { ref, reactive } from "vue";
import { showSuccessToast, showFailToast } from "vant";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/modules/user";
const router = useRouter();
const userStore = useUserStore();
const rememberMe = ref(false);
const stuId = ref("");
const password = ref("");

interface IUserForm {
  stuId: string;
  password: string;
  rememberMe?: boolean;
}
const userForm = reactive({
  stuId: "M222271503",
  password: "z123456",
});

const handleUserLogin = () => {
  console.log(userForm);
  userStore.login(userForm);
};
</script>

<style lang="scss" scoped>
.login-container {
  padding: 20px;
  margin-top: 40%;
  .title {
    text-align: center;
    font-size: 30px;
    font-weight: bold;
  }
  .remember-text {
    // fix：如果未设置宽度，flex布局会导致点击空白区域仍可导致复选框选中的bug
    width: 100px;
    padding: 10px 25px 0 25px;
    font-size: 14px;
  }
}
</style>
