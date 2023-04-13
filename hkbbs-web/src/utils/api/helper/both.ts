import { req } from "./req"
import { ResultBoolean, ResultPostDetailsPost } from "../../schemas"

//both 帖子、树洞共有
export class Both {
    static deleteComment(args: { commentId: string }): Promise<ResultBoolean> {
        return req.delete({
            url: "/delete/comment/" + args.commentId
        })
    }

    static getPostDetail(args: { postId: number, currentPage: number, pageSize: number }): Promise<ResultPostDetailsPost> {
        return req.get({
            url: "/details/" + args.postId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }
}