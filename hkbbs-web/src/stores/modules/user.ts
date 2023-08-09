import { defineStore } from "pinia";
import { User } from "@/utils/api/helper/user";
interface UserState {
  token: string;
}
interface UserLoginInfo {
  rememberMe: boolean | undefined;
  stuId: string;
  password: string;
}

export const useUserStore = defineStore("user", {
  state: (): UserState => {
    return {
      token: "",
    };
  },
  actions: {
    login(data: UserLoginInfo) {
      return User.login(data);
    },
  },
});
