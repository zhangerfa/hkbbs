import Request from './request'

const BASE_URL = "http://127.0.0.1"
const TIMEOUT = 10000

const req = new Request(BASE_URL, TIMEOUT)

//card 卡片
class Card {

    URL = "/cards/"

    static get(args: { posterId: string, goal: number, currentPage: number, pageSize: number }): Promise<any> {
        return req.get({
            url: URL.toString(),
            params: args
        })
    }

    static getById(args: { cardId: number }): Promise<any> {
        return req.get({
            url: URL.toString() + args.cardId.toString()
        })
    }

    static publish(args: { posterId: string, images: string[], aboutMe: string, goal: number, expected: string }): Promise<any> {
        return req.post({
            url: URL.toString(),
            data: {
                newCard: args
            }
        })
    }


    static update(args: { cardId: number, aboutMe: string, expected: string }): Promise<any> {
        return req.put({
            url: URL.toString() + args.cardId.toString(),
            data: {
                aboutMe: args.aboutMe,
                expected: args.expected
            }
        })
    }

    static delete(args: { cardId: number }): Promise<any> {
        return req.delete({
            url: URL.toString() + args.cardId.toString()
        })
    }
}

//hole 树洞
class Hole {

    URL = "/holes/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<any> {
        return req.get({
            url: URL.toString() + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<any> {
        return req.post({
            url: URL.toString(),
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<any> {
        return req.post({
            url: URL.toString() + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<any> {
        return req.delete({
            url: URL.toString() + "delete/post/" + args.postId.toString()
        })
    }
}

//post 帖子
class Post {

    URL = "/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<any> {
        return req.get({
            url: URL.toString() + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<any> {
        return req.post({
            url: URL.toString(),
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<any> {
        return req.post({
            url: URL.toString() + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<any> {
        return req.delete({
            url: URL.toString() + "delete/" + args.postId.toString()
        })
    }
}

//both 帖子、树洞共有
class Both {
    static deleteComment(args: { commentId: string }): Promise<any> {
        return req.delete({
            url: "/delete/comment/" + args.commentId
        })
    }

    static getPostDetail(args: { postId: number, currentPage: number, pageSize: number }): Promise<any> {
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

    URL = "/notices/"

    static get(args: { currentPage: number, pageSize: number }): Promise<any> {
        return req.get({
            url: URL.toString(),
            params: args
        })
    }

    static readCount(args: { ids: number[] }): Promise<any> {
        return req.put({
            url: URL.toString() + "read",
            data: args
        })
    }

    static unreadCount(args: { actionType: number }): Promise<any> {
        return req.get({
            url: URL.toString() + "unread",
            params: args
        })
    }
}

//user 用户
class User {

    URL = "/users/"

    static get(args: { stuId: string }): Promise<any> {
        return req.get({
            url: URL.toString() + args.stuId,
        })
    }

    static login(args: { rememberMe: boolean, stuId: string, password: string }): Promise<any> {
        return req.post({
            url: URL.toString() + "login",
            data: args
        })
    }

    static logout(): Promise<any> {
        return req.put({
            url: URL.toString() + "logout"
        })
    }

    static register(args: { code: string, stuId: string, username: string, password: string }): Promise<any> {
        return req.post({
            url: URL.toString() + "register",
            data: args
        })
    }

    static change(args: { newPassword: string, username: string }): Promise<any> {
        return req.put({
            url: URL.toString(),
            data: args
        })
    }

    static exist(args: { stuId: string }): Promise<any> {
        return req.get({
            url: URL.toString() + "isExist",
            params: args
        })
    }

    static sendCode(args: { stuId: string }): Promise<any> {
        return req.get({
            url: URL.toString() + "sendCode",
            params: args
        })
    }
}

export default { Card, Hole, Post, Both, Notice, User }