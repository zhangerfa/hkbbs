import { defineStore } from "pinia";
import { User } from "@/utils/api/helper/user";
import router from "@/router";
import { LOGIN_TOKEN, USER_INFO } from "@/constant/User";
import type { UserLoginInfo, UserState } from "@/types/index";
import { showSuccessToast } from "vant";
export const useUserStore = defineStore("user", {
  state: (): UserState => {
    return {
      token: "",
    };
  },
  actions: {
    async login(data: UserLoginInfo) {
      const { stuId } = data;
      const res = await User.login(data);
      const userInfoRes = User.getInfo({ stuId });
      console.log(userInfoRes);
      showSuccessToast("登录成功");
      router.push("/");
    },
    async logout() {
      const res = await User.logout();
      showSuccessToast("退出登录成功");
      this.removeAllItem();
      router.push("/login");
    },
    removeAllItem() {
      localStorage.removeItem(LOGIN_TOKEN);
      localStorage.removeItem(USER_INFO);
    },
  },
});
