import { LOGIN_TOKEN } from "@/constant/User";
import axios, { AxiosInstance } from "axios";

// 二次封装axios
class Request {
  instance: AxiosInstance;
  constructor(baseURL: string, timeout: number = 10000) {
    this.instance = axios.create({
      baseURL,
      timeout,
    });

    //在请求上携带用户token
    const token = localStorage.getItem(LOGIN_TOKEN);
    if (token) {
      this.setToken(token);
    }
    //添加请求拦截器
    this.instance.interceptors.request.use(
      (config) => {
        return config;
      },
      (err) => {
        return err;
      }
    );
    //添加响应拦截器
    this.instance.interceptors.response.use(
      (res) => {
        const { data, code } = res.data;
        if (code !== 20011 && !data) {
          return Promise.reject(new Error("错误"));
        }
        return res.data;
      },
      (err) => {
        return err;
      }
    );
  }

  request(config: any) {
    return new Promise((resolve, reject) => {
      this.instance
        .request(config)
        .then((res) => {
          resolve(res.data);
        })
        .catch((err) => {
          reject(err);
        });
    });
  }

  get(config: { url: string; params?: any }): Promise<any> {
    return this.request({ ...config, method: "get" });
  }

  post(config: {
    url: string;
    data: any;
    params?: any;
    headers?: any;
  }): Promise<any> {
    return this.request({ ...config, method: "post" });
  }

  put(config: { url: string; data?: any }): Promise<any> {
    return this.request({ ...config, method: "put" });
  }

  delete(config: { url: string }): Promise<any> {
    return this.request({ ...config, method: "delete" });
  }

  setToken(token: string): void {
    this.instance.defaults.headers.Authorization = token;
  }
}

export default Request;
