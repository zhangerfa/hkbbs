import type { MockMethod } from 'vite-plugin-mock'
import { faker } from '@faker-js/faker/locale/zh_CN'
import * as Schemas from "../schemas"

const getMsg = {
    "code": 0,
    "msg": "faker"
}

const getGender = () => faker.name.sex() === "male" ? "0" : "1"
const getStuId = () => "M" + faker.datatype.number({ min: 100000000, max: 999999999 }).toString()
const getImageUrl = () => faker.image.animals()
const getTime = () => faker.date.between('2020-01-01T00:00:00.000Z', '2023-04-01T00:00:00.000Z')
const getAge = () => faker.datatype.number({ min: 10, max: 99 }).toString()
const getAboutMe = () => faker.lorem.sentence()
const getGoal = () => faker.datatype.number({ min: 0, max: 3 })
const getName = () => faker.name.fullName()
const getHeaderUrl = () => faker.image.avatar()
const getExpected = () => faker.lorem.sentence()
const getPosterId = () => faker.datatype.number({ min: 100000, max: 999999 }).toString()
const getContent = () => faker.lorem.paragraphs()

const getResultBool = {
    data: true,
    ...getMsg
}

// 卡片
const getOneCard = (num: number) => {
    return {
        "id": num,
        "imageUrls": [
            getImageUrl(), getImageUrl(), getImageUrl()
        ],
        "age": getAge(),
        "aboutMe": getAboutMe(),
        "goal": getGoal(),
        "expected": getExpected(),
        "create_time": getTime(),
        "poster": {
            "stuId": getStuId(),
            "username": getName(),
            "headerUrl": getHeaderUrl()
        }
    }
}

const getCards = (start: string, num: string) => {
    let data = []
    for (let i = 1; i <= Number(num); i++) {
        data.push(getOneCard(Number(start) + i))
    }
    return data

}

let card_ls: MockMethod[] = [
    {
        url: '/cards/',
        method: 'get',
        response: (options: any) => {
            return {
                "data": getCards(options.query.currentPage, options.query.pageSize),
                ...getMsg
            }
        }
    },
    {
        url: '/cards/',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/cards/:cardId',
        method: 'get',
        response: (options: any) => {
            return {
                data: getOneCard(options.params.cardId),
                ...getMsg
            }
        }
    },
    {
        url: '/cards/:cardId',
        method: 'put',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/cards/:cardId',
        method: 'delete',
        response: () => {
            return getResultBool
        }
    }
]


// 树洞
const GetOneHoles = (num: number) => {
    return {
        "poster": {
            "stuId": getStuId(),
            "username": getName(),
            "headerUrl": getHeaderUrl(),
            "gender": getGender()
        },
        "post": {
            "id": num,
            "posterId": getPosterId(),
            "title": getAboutMe(),
            "content": getContent(),
            "createTime": getTime(),
            "commentNum": Number(getAge()),
            "hot": Number(getAge()),
            "images": [
                getImageUrl(), getImageUrl(), getImageUrl()
            ]
        }
    }
}

const getHoles = (start: string, num: string) => {
    let data = []
    for (let i = 1; i <= Number(num); i++) {
        data.push(GetOneHoles(Number(start) + i))
    }
    return data

}

let hole_ls: MockMethod[] = [
    {
        url: '/holes/',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/holes/:stuId',
        method: 'get',
        response: (options: any) => {
            return {
                data: getHoles(options.query.currentPage, options.query.pageSize),
                ...getMsg
            }
        }
    },
    {
        url: '/holes/comment',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/holes/delete/:postId',
        method: 'delete',
        response: () => {
            return getResultBool
        }
    }
]

// 帖子
let post_ls: MockMethod[] = [
    {
        url: '/posts/',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/posts/:stuId',
        method: 'get',
        response: (options: any) => {
            return {
                data: getHoles(options.query.currentPage, options.query.pageSize),
                ...getMsg
            }
        }
    },
    {
        url: '/posts/comment',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/posts/delete/:postId',
        method: 'delete',
        response: () => {
            return getResultBool
        }
    }
]

const getPosterDetail = (postId: string) => {
    return {
        "post": {
            "id": 0,
            "posterId": postId,
            "title": getAboutMe(),
            "content": getContent(),
            "createTime": getTime(),
            "commentNum": Number(getAge()),
            "hot": Number(getAge()),
            "images": [
                getImageUrl(), getImageUrl(), getImageUrl()
            ]
        },
        "poster": {
            "stuId": getStuId(),
            "username": getName(),
            "headerUrl": getHeaderUrl(),
            "gender": getGender()
        },
        "commentDetails": [
            {
                "post": {
                    "posterId": getPosterId(),
                    "entityType": getGoal(),
                    "entityId": getStuId(),
                    "content": getContent(),
                    "createTime": getTime()
                },
                "poster": {
                    "stuId": getStuId(),
                    "username": getName(),
                    "headerUrl": getHeaderUrl(),
                    "gender": getGender()
                },
                "page": {
                    "currentPage": 0,
                    "pageSize": Number(getAge()),
                    "numOfPostsOnPage": 10,
                    "totalPage": Number(getAge()) * 2,
                    "numOfPosts": Number(getAge()) * 20
                },
                "deep": 0
            }
        ],
        "page": {
            "currentPage": 0,
            "pageSize": Number(getAge()),
            "numOfPostsOnPage": 10,
            "totalPage": Number(getAge()) * 2,
            "numOfPosts": Number(getAge()) * 20
        }
    }
}

// 共有
let both_ls: MockMethod[] = [
    {
        url: '/delete/comment/:commentId',
        method: 'delete',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/details/:postId',
        method: 'get',
        response: (options: any) => {
            return {
                data: getPosterDetail(options.params.postId)
            }
        }
    },
]

// 通知
const getOneNotice=(num:number)=>{
    return{
        "actionUser": {
            "stuId": getStuId(),
            "username": getName(),
            "headerUrl": getHeaderUrl(),
            "gender": getGender()
        },
        "postId": getPosterId(),
        "entityType": getGoal(),
        "entityContent": getContent(),
        "actionContent": getExpected()
    }
}

const getNotices=(start: string, num: string)=>{
    let data = []
    for (let i = 1; i <= Number(num); i++) {
        data.push(getOneNotice(Number(start) + i))
    }
    return data
}

let notice_ls: MockMethod[] = [
    {
        url: '/notices/',
        method: 'get',
        response: (options:any) => {
            return {
                data:getNotices(options.query.currentPage, options.query.pageSize),
                ...getMsg
            }
        }
    },
    {
        url: '/notices/read',
        method: 'put',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/notices/read',
        method: 'get',
        response: () => {
            return {
                "data": 20,
                ...getMsg
            }
        }
    },
]

// 用户
let user_ls: MockMethod[] = [
    {
        url: '/users/',
        method: 'put',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/users/:stuId',
        method: 'get',
        response: () => {
            return {
                data:{
                    "stuId": getStuId(),
                    "username": getName(),
                    "headerUrl": getHeaderUrl(),
                    "gender": getGender()
                },
                ...getMsg
            }
        }
    },
    {
        url: '/users/isExist',
        method: 'get',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/users/login',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/users/logout',
        method: 'put',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/users/register',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/users/sendCode',
        method: 'get',
        response: () => {
            return getResultBool
        }
    },
]

let mockList: MockMethod[] = [...card_ls, ...hole_ls, ...post_ls, ...both_ls,...notice_ls,...user_ls]
export default mockList