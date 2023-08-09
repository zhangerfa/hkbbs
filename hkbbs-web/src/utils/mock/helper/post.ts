import { getGoal, getMsg, getGender, getStuId, getImageUrl, getTime, getAge, getAboutMe, getName, getHeaderUrl, getPosterId, getContent, getResultBool } from './custom'
import type { MockMethod } from 'vite-plugin-mock'

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

// 树洞
export const GetOnePost = (num: number) => {
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

export const getPosts = (start: string, num: string) => {
    let data = []
    for (let i = 1; i <= Number(num); i++) {
        data.push(GetOnePost(Number(start) + i))
    }
    return data

}

// 帖子
export const post_ls: MockMethod[] = [
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
                data: getPosts(options.query.currentPage, options.query.pageSize),
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
    },
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

