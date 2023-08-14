<template>
  <div class="register-container">
    <p class="register-title">Hkbbs注册页面</p>
    <van-form @submit="handleRegister">
      <van-cell-group>
        <van-field
          v-model="registerInfo.stuId"
          name="学号"
          label="学号"
          placeholder="请输入学号"
          :rules="[{ required: true, message: '请填写学号' }]"
        />
        <van-field
          v-model="registerInfo.username"
          name="用户名"
          label="用户名"
          placeholder="请输入用户名"
          :rules="[{ required: true, message: '请填写用户名' }]"
        />
        <van-field name="radio" label="性别">
          <template #input>
            <van-radio-group
              v-model="registerInfo.gender"
              direction="horizontal"
            >
              <van-radio name="0">男</van-radio>
              <van-radio name="1">女</van-radio>
            </van-radio-group>
          </template>
        </van-field>
        <van-field
          v-model="registerInfo.password"
          type="password"
          name="password"
          label="密码"
          placeholder="密码"
          :rules="[{ required: true, message: '请填写密码' }]"
        />
        <van-field
          v-model="registerInfo.code"
          center
          clearable
          label="短信验证码"
          placeholder="请输入短信验证码"
        >
          <template #button>
            <van-button
              :disabled="sendBtnType === 'primary' ? false : true"
              size="small"
              type="primary"
              @click="handleSendCode"
              >{{ sendMsg }}</van-button
            >
          </template>
        </van-field>
      </van-cell-group>
      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          注册
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup lang="ts">
interface IregisterInfo {
  stuId: string;
  username: string;
  gender: "0" | "1";
  password: string;
  confirmPassword?: string;
  code: string;
}
// import { setInterval } from "timers-browserify";
import { setInterval } from "timers";
import { ref, reactive } from "vue";
import { User } from "@/utils/api/helper/user";
import { useRouter } from "vue-router";
const registerInfo: IregisterInfo = reactive({
  stuId: "",
  username: "",
  gender: "0",
  password: "",
  code: "",
});
const TIME_COUNT = 60;
const sendBtnType = ref("primary");
const sendMsg = ref("发送验证码");
const timer = ref();
const sendCountDown = ref(0);

const router = useRouter();
const handleRegister = () => {
  User.register(registerInfo).then((res) => {
    console.log(res);
    if (res.code === 0) {
      router.push("/login");
    }
  });
};
const handleSendCode = () => {
  // console.log("发送验证码,倒计时60s");
  // User.sendCode({
  //   stuId: registerInfo.stuId,
  // });
  // if (!timer.value) {
  //   sendCountDown.value = TIME_COUNT;
  //   sendBtnType.value = "default";
  //   timer.value = setInterval(() => {
  //     if (sendCountDown.value > 0 && sendCountDown.value <= TIME_COUNT) {
  //       sendCountDown.value--;
  //     } else {
  //       sendBtnType.value = "primary";
  //       clearInterval(timer.value);
  //       timer.value = null;
  //     }
  //   }, 1000);
  // }
};
</script>

<style lang="scss" scoped>
.register-container {
  padding: 100px 30px 0 30px;
  text-align: center;
  .register-title {
    font-size: 30px;
    font-weight: bold;
  }
}
</style>
