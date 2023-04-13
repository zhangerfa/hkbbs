import { req } from "./req"
import { ResultListPostInfo, ResultBoolean } from "../../schemas"

//post 帖子
export class Post {

    private static path: string = "/posts/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<ResultListPostInfo> {
        return req.get({
            url: this.path + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<ResultBoolean> {
        return req.post({
            url: this.path,
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<ResultBoolean> {
        return req.post({
            url: this.path + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<ResultBoolean> {
        return req.delete({
            url: this.path + "delete/" + args.postId.toString()
        })
    }
}