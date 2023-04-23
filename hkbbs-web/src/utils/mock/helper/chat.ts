import { get } from 'http'
import { getMsg, getStuId, getImageUrl, getTime, getGender, getAge, getAboutMe, getGoal, getName, getHeaderUrl, getExpected, getResultBool, getPosterId } from './custom'
import type { MockMethod } from 'vite-plugin-mock'

//聊天
function getMessage(id: number, posterId: string) {
    return {
        "id": id,
        "posterId": posterId,
        "content": getExpected,
        "createTime": getTime,
        // 消息状态：0-未读，1-已读
        "status": 0,
    }
}

function getChat(id: number = 0) {
    const posterId = getPosterId()
    const mes = []
    for (let i = 0; i <= 5; i++) {
        mes.push(getMessage(i, posterId))
    }
    return {
        "id": id,
        "users": {
            "stuId": getStuId(),
            "username": getName(),
            "headerUrl": getHeaderUrl(),
            "gender": getGender()
        },
        "messages": mes,
        "page": {
            "currentPage": 0,
            "pageSize": Number(getAge()),
            "numOfPostsOnPage": 10,
            "totalPage": Number(getAge()) * 2,
            "numOfPosts": Number(getAge()) * 20
        },
    }
}

function getListChat() {
    const c = []
    for (let i = 0; i <= 5; i++) {
        c.push(getChat(i))
    }
    return c
}

export const chat_ls: MockMethod[] = [
    {
        url: '/chats/',
        method: 'get',
        response: () => {
            return {
                data: getListChat(),
                ...getMsg
            }
        }
    },
    {
        url: '/chats/',
        method: 'post',
        response: () => {
            return {
                data: getChat(),
                ...getMsg
            }
        }
    },
    {
        url: '/chats/:chatToStuId',
        method: 'get',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/chats/image',
        method: 'post',
        response: () => {
            return getResultBool
        }
    },
    {
        url: '/chats/unreadNum',
        method: 'get',
        response: () => {
            return {
                "data": 20,
                ...getMsg
            }
        }
    },
]