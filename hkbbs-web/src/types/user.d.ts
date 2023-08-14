export interface UserState {
  token: string;
}

export interface UserLoginInfo {
  rememberMe?: boolean;
  stuId: string;
  password: string;
}
