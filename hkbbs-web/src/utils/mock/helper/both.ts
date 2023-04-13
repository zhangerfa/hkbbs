import type { MockMethod } from 'vite-plugin-mock'
import { getMsg, getGender, getStuId, getImageUrl, getTime, getAge, getAboutMe, getGoal, getName, getHeaderUrl, getPosterId, getContent ,getResultBool} from './custom'

// 共有
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


export const both_ls: MockMethod[] = [
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