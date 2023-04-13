import { getMsg, getStuId, getImageUrl, getTime, getAge, getAboutMe, getGoal, getName, getHeaderUrl, getExpected,getResultBool} from './custom'
import type { MockMethod } from 'vite-plugin-mock'

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

export const card_ls: MockMethod[] = [
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
