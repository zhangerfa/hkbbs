import axios, {AxiosInstance} from 'axios'

const BASE_URL = "http://zhangerfa.site"
const TIMEOUT = 10000

// 二次封装axios
class Request{
    instance: AxiosInstance
    constructor(baseURL: string, timeout: number= 10000) {
        this.instance = axios.create({
            baseURL,
            timeout
        })

        this.instance.interceptors.request.use(config => {
            return config
        }, err => {
            return err
        })

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
}

export default new Request(BASE_URL, TIMEOUT)
