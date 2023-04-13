import type { MockMethod } from 'vite-plugin-mock'
import { getMsg, getGender, getStuId, getGoal, getName, getHeaderUrl, getExpected, getPosterId, getContent ,getResultBool} from './custom'

// 通知
const getOneNotice = (num: number) => {
    return {
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

const getNotices = (start: string, num: string) => {
    let data = []
    for (let i = 1; i <= Number(num); i++) {
        data.push(getOneNotice(Number(start) + i))
    }
    return data
}

export const notice_ls: MockMethod[] = [
    {
        url: '/notices/',
        method: 'get',
        response: (options: any) => {
            return {
                data: getNotices(options.query.currentPage, options.query.pageSize),
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