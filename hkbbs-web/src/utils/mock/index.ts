import type { MockMethod } from 'vite-plugin-mock'
import { card_ls } from './helper/card'
import { post_ls } from './helper/post'
import { notice_ls } from './helper/notice'
import { user_ls } from './helper/user'
import { chat_ls } from './helper/chat'

let mockList: MockMethod[] = [...card_ls, ...post_ls, ...notice_ls, ...user_ls, ...chat_ls]
export default mockList