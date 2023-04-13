import { getMsg, getGender, getStuId, getImageUrl, getTime, getAge, getAboutMe, getName, getHeaderUrl, getPosterId, getContent ,getResultBool} from './custom'
import type { MockMethod } from 'vite-plugin-mock'

// 树洞
export const GetOneHoles = (num: number) => {
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

export const getHoles = (start: string, num: string) => {
    let data = []
    for (let i = 1; i <= Number(num); i++) {
        data.push(GetOneHoles(Number(start) + i))
    }
    return data

}

export const hole_ls: MockMethod[] = [
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