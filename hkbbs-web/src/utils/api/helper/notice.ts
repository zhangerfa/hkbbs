import { req } from "./req"
import { ResultListNoticeInfo, ResultInteger } from "../../schemas"

//notice 通知
export class Notice {

    private static path: string = "/notices/"

    static get(args: { currentPage: number, pageSize: number }): Promise<ResultListNoticeInfo> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static readCount(args: { ids: number[] }): Promise<ResultInteger> {
        return req.put({
            url: this.path + "read",
            data: args
        })
    }

    static unreadCount(args: { actionType: number }): Promise<ResultInteger> {
        return req.get({
            url: this.path + "unread",
            params: args
        })
    }
}