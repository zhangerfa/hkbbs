import Request from './request'

const BASE_URL = "http://127.0.0.1"
const TIMEOUT = 10000

const req = new Request(BASE_URL,TIMEOUT)

//card 卡片
class Card{

    static get(args:{posterId:string,goal:number,currentPage:number,pageSize:number}) :any{
        
    }

    static getById(args:{cardId:number}):any{

    }

    static publish(args:{posterId:string,images:string[],aboutMe:string,goal:number,expected:string}):any{

    }


    static update(args:{cardId:number,aboutMe:string,expected:string}):any{

    }

    static delete(args:{cardId:number}):any{

    }
}

//hole 树洞
class Hole{

    static get(args:{stuId:string,currentPage:string,pageSize:string}):any{

    }

    static publish(args:{title:string,content:string,images:string[]}):any{

    }

    static comment(args:{cardId:number,entityType:string,entityId:string,content:string}):any{

    }

    static delete(args:{postId:number}):any{

    }
}

//both 帖子
class Post{
    static get(args:{stuId:string,currentPage:string,pageSize:string}):any{

    }

    static publish(args:{title:string,content:string,images:string[]}):any{

    }

    static comment(args:{cardId:number,entityType:string,entityId:string,content:string}):any{

    }

    static delete(args:{postId:number}):any{

    }
}

//both 帖子、树洞共有
class Both{
    static deleteComment(args:{commentId:string}):any{

    }

    static getPostDetail(args:{postId:number,currentPage:number,pageSize:number}):any{

    }
}

//notice 通知
class Notice{
    static get(args:{currentPage:number,pageSize:number}):any{

    }

    static readCount(args:{ids:number[]}):any{

    }

    static unreadCount(args:{actionType:number}):any{

    }
}

//user 用户
class User{
    static get(args:{stuId:string}):any{
        
    }

    static login(args:{rememberMe:boolean,stuId:string,password:string}):any{

    }

    static logout():any{

    }

    static register(args:{code:string,stuId:string,username:string,password:string}):any{

    }

    static change(args:{newPassword:string,username:string}):any{

    }

    static exist(args:{stuId:string}):any{

    }

    static sendCode(args:{stuId :string}):any{

    }
}

export default {Card,Hole,Post,Both,Notice,User}