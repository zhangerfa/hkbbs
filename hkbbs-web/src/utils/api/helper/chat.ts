import { req } from "./req"
import { ResultListChat, ResultChat, ResultBoolean, ResultInteger } from "../../schemas"

//chat 聊天
export class Chat {

    private static path: string = "/chats/"

    static getList(args: { currentPage: number, pageSize: number }): Promise<ResultListChat> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static getById(args: { chatToStuId: string, currentPage: number, pageSize: number }): Promise<ResultChat> {
        return req.get({
            url: this.path + args.chatToStuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static sendMessage(args: { toStuId: string, content: string }): Promise<ResultBoolean> {
        return req.post({
            url: this.path,
            data: args
        })
    }

    static sendImage(args: { toStuId: string, content: any }): Promise<ResultBoolean> {
        return req.post({
            url: this.path + "image",
            data: args
        })
    }

    static unread(args: { chatToStuId: string }): Promise<ResultInteger> {
        return req.get({
            url: this.path + "unreadNum",
            params: args
        })
    }

}