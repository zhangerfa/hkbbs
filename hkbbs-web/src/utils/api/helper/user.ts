import { req } from "./req";
import { User as UserType, ResultBoolean } from "../../schemas";

//user 用户
export class User {
  private static path: string = "/users/";

  static current(): Promise<UserType> {
    return req.get({
      url: this.path,
    });
  }

  static getInfo(args: { stuId: string }): Promise<UserType> {
    return req.get({
      url: this.path + args.stuId,
    });
  }

  static login(args: {
    rememberMe?: boolean;
    stuId: string;
    password: string;
  }): Promise<ResultBoolean> {
    return req.post({
      url: this.path + "login",
      data: args,
      params: args,
    });
  }

  static logout(): Promise<ResultBoolean> {
    return req.put({
      url: this.path + "logout",
    });
  }

  static register(args: {
    code: string;
    stuId: string;
    username: string;
    password: string;
    gender: "0" | "1";
  }): Promise<ResultBoolean> {
    return req.post({
      url: this.path + "register",
      data: args,
    });
  }

  static change(args: {
    newPassword: string;
    username: string;
    headerImage: any;
  }): Promise<ResultBoolean> {
    return req.put({
      url: this.path,
      data: args,
    });
  }

  static exist(args: { stuId: string }): Promise<ResultBoolean> {
    return req.get({
      url: this.path + "isExist",
      params: args,
    });
  }

  static sendCode(args: { stuId: string }): Promise<ResultBoolean> {
    return req.get({
      url: this.path + "sendCode",
      params: args,
    });
  }
}
