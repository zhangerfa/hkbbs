import Request from './request'
import * as Schemas from "./schemas"

const BASE_URL = ""
const TIMEOUT = 10000

const req = new Request(BASE_URL, TIMEOUT)


//card 卡片
class Card {

    private static path: string = "/cards/"

    static get(args: { posterId: string, goal: number, currentPage: number, pageSize: number }): Promise<Schemas.ResultListCardContainsPoster> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static getById(args: { cardId: number }): Promise<Schemas.ResultCardContainsPoster> {
        return req.get({
            url: this.path + args.cardId.toString()
        })
    }

    static publish(args: { posterId: string, images: string[], aboutMe: string, goal: number, expected: string }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path,
            data: {
                newCard: args
            }
        })
    }


    static update(args: { cardId: number, aboutMe: string, expected: string }): Promise<Schemas.ResultBoolean> {
        return req.put({
            url: this.path + args.cardId.toString(),
            data: {
                aboutMe: args.aboutMe,
                expected: args.expected
            }
        })
    }

    static delete(args: { cardId: number }): Promise<Schemas.ResultBoolean> {
        return req.delete({
            url: this.path + args.cardId.toString()
        })
    }
}

//hole 树洞
class Hole {

    private static path: string = "/holes/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<Schemas.ResultListPostInfo> {
        return req.get({
            url: this.path + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path,
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<Schemas.ResultBoolean> {
        return req.delete({
            url: this.path + "delete/" + args.postId.toString()
        })
    }
}

//post 帖子
class Post {

    private static path: string = "/posts/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<Schemas.ResultListPostInfo> {
        return req.get({
            url: this.path + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path,
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<Schemas.ResultBoolean> {
        return req.delete({
            url: this.path + "delete/" + args.postId.toString()
        })
    }
}

//both 帖子、树洞共有
class Both {
    static deleteComment(args: { commentId: string }): Promise<Schemas.ResultBoolean> {
        return req.delete({
            url: "/delete/comment/" + args.commentId
        })
    }

    static getPostDetail(args: { postId: number, currentPage: number, pageSize: number }): Promise<Schemas.ResultPostDetailsPost> {
        return req.get({
            url: "/details/" + args.postId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }
}

//notice 通知
class Notice {

    private static path: string = "/notices/"

    static get(args: { currentPage: number, pageSize: number }): Promise<Schemas.ResultListNoticeInfo> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static readCount(args: { ids: number[] }): Promise<Schemas.ResultInteger> {
        return req.put({
            url: this.path + "read",
            data: args
        })
    }

    static unreadCount(args: { actionType: number }): Promise<Schemas.ResultInteger> {
        return req.get({
            url: this.path + "unread",
            params: args
        })
    }
}

//user 用户
class User {

    private static path: string = "/users/"

    static get(args: { stuId: string }): Promise<Schemas.User> {
        return req.get({
            url: this.path + args.stuId,
        })
    }

    static login(args: { rememberMe?: boolean, stuId: string, password: string }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path + "login",
            data: args
        })
    }

    static logout(): Promise<Schemas.ResultBoolean> {
        return req.put({
            url: this.path + "logout"
        })
    }

    static register(args: { code: string, stuId: string, username: string, password: string, gender: "0" | "1" }): Promise<Schemas.ResultBoolean> {
        return req.post({
            url: this.path + "register",
            data: args
        })
    }

    static change(args: { newPassword: string, username: string }): Promise<Schemas.ResultBoolean> {
        return req.put({
            url: this.path,
            data: args
        })
    }

    static exist(args: { stuId: string }): Promise<Schemas.ResultBoolean> {
        return req.get({
            url: this.path + "isExist",
            params: args
        })
    }

    static sendCode(args: { stuId: string }): Promise<Schemas.ResultBoolean> {
        return req.get({
            url: this.path + "sendCode",
            params: args
        })
    }
}

export default { Card, Hole, Post, Both, Notice, User }