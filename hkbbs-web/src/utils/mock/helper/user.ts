import type { MockMethod } from 'vite-plugin-mock'
import { getMsg, getGender, getStuId, getName, getHeaderUrl,getResultBool} from './custom'

// 用户
export const user_ls: MockMethod[] = [
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
                data: {
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