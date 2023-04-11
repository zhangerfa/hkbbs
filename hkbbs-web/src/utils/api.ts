import Request from './request'

const BASE_URL = "http://127.0.0.1"
const TIMEOUT = 10000

const req = new Request(BASE_URL, TIMEOUT)


//card 卡片
class Card {

    private static path = "/cards/"

    static get(args: { posterId: string, goal: number, currentPage: number, pageSize: number }): Promise<any> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static getById(args: { cardId: number }): Promise<any> {
        return req.get({
            url: this.path + args.cardId.toString()
        })
    }

    static publish(args: { posterId: string, images: string[], aboutMe: string, goal: number, expected: string }): Promise<any> {
        return req.post({
            url: this.path,
            data: {
                newCard: args
            }
        })
    }


    static update(args: { cardId: number, aboutMe: string, expected: string }): Promise<any> {
        return req.put({
            url: this.path + args.cardId.toString(),
            data: {
                aboutMe: args.aboutMe,
                expected: args.expected
            }
        })
    }

    static delete(args: { cardId: number }): Promise<any> {
        return req.delete({
            url: this.path + args.cardId.toString()
        })
    }
}

//hole 树洞
class Hole {

    private static path = "/holes/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<any> {
        return req.get({
            url: this.path + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<any> {
        return req.post({
            url: this.path,
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<any> {
        return req.post({
            url: this.path + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<any> {
        return req.delete({
            url: this.path + "delete/post/" + args.postId.toString()
        })
    }
}

//post 帖子
class Post {

    private static path = "/"

    static get(args: { stuId: string, currentPage: string, pageSize: string }): Promise<any> {
        return req.get({
            url: this.path + args.stuId,
            params: {
                currentPage: args.currentPage,
                pageSize: args.pageSize
            }
        })
    }

    static publish(args: { title: string, content: string, images: string[] }): Promise<any> {
        return req.post({
            url: this.path,
            data: {
                newPost: args
            }
        })
    }

    static comment(args: { cardId: number, entityType: string, entityId: string, content: string }): Promise<any> {
        return req.post({
            url: this.path + "comment",
            data: args
        })
    }

    static delete(args: { postId: number }): Promise<any> {
        return req.delete({
            url: this.path + "delete/" + args.postId.toString()
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

    private static path = "/notices/"

    static get(args: { currentPage: number, pageSize: number }): Promise<any> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static readCount(args: { ids: number[] }): Promise<any> {
        return req.put({
            url: this.path + "read",
            data: args
        })
    }

    static unreadCount(args: { actionType: number }): Promise<any> {
        return req.get({
            url: this.path + "unread",
            params: args
        })
    }
}

//user 用户
class User {

    private static path = "/users/"

    static get(args: { stuId: string }): Promise<any> {
        return req.get({
            url: this.path + args.stuId,
        })
    }

    static login(args: { rememberMe: boolean, stuId: string, password: string }): Promise<any> {
        return req.post({
            url: this.path + "login",
            data: args
        })
    }

    static logout(): Promise<any> {
        return req.put({
            url: this.path + "logout"
        })
    }

    static register(args: { code: string, stuId: string, username: string, password: string }): Promise<any> {
        return req.post({
            url: this.path + "register",
            data: args
        })
    }

    static change(args: { newPassword: string, username: string }): Promise<any> {
        return req.put({
            url: this.path,
            data: args
        })
    }

    static exist(args: { stuId: string }): Promise<any> {
        return req.get({
            url: this.path + "isExist",
            params: args
        })
    }

    static sendCode(args: { stuId: string }): Promise<any> {
        return req.get({
            url: this.path + "sendCode",
            params: args
        })
    }
}

export default { Card, Hole, Post, Both, Notice, User }