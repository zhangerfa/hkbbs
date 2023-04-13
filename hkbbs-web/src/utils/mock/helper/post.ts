import { getMsg,getResultBool} from './custom'
import type { MockMethod } from 'vite-plugin-mock'
import {getHoles} from './hole'

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

