import axios, { AxiosInstance } from 'axios'

// 二次封装axios
class Request {
    instance: AxiosInstance
    constructor(baseURL: string, timeout: number = 10000) {
        this.instance = axios.create({
            baseURL,
            timeout
        })
        //添加请求拦截器
        this.instance.interceptors.request.use(config => {
            return config
        }, err => {
            return err
        })
        //添加响应拦截器
        this.instance.interceptors.response.use(res => {
            return res
        }, err => {
            return err
        })
    }

    request(config: any) {
        return new Promise((resolve, reject) => {
            this.instance.request(config).then(res => {
                resolve(res.data)
            }).catch(err => {
                reject(err)
            })
        })
    }

    get(config: any) {
        return this.request({ ...config, method: "get" })
    }

    post(config: any) {
        return this.request({ ...config, method: "post" })
    }

    put(config: any) {
        return this.request({ ...config, method: "put" })
    }

    delete(config: any) {
        return this.request({ ...config, method: "delete" })
    }

}

export default Request;
