export interface BasicResponse {
    "code": number,
    // "description": "请求处理状态码",
    "msg": string,
    // "description": "请求处理结果信息"
}

// "返回结果"
export interface ResultBoolean extends BasicResponse {
    "data": boolean,
    // "description": "响应数据"
}

// "返回结果"
export interface ResultUser extends BasicResponse {
    "data": User,
}

// "用户信息"
export interface User {
    "stuId": string,
    // "description": "学号" 0-9
    // "pattern": "[UMD][0-9]{9}"
    "username": string,
    // "description": "用户名"
    "headerUrl": string,
    // "description": "头像地址"
    "gender": "0" | "1",
    // "description": "用户性别"
}

// "帖子"
export interface Post {
    "id": number,
    "posterId": string,
    // "description": "发帖人学号"
    "title": string,
    // "description": "标题"
    "content": string,
    // "description": "正文"
    "createTime": string,
    // "description": "发帖时间",
    // "format": "date-time"
    "commentNum": number,
    // "description": "评论数量",
    "hot": number,
    // "description": "热度",
    "images": string[]
    // "description": "帖子中图片URL集合"
}


// "封装帖子的信息，包含id，发帖人昵称、头像，帖子的标题、内容、发帖时间、评论数量、热度"
export interface PostInfo {
    "poster": User,
    "post": Post
}

// "返回结果"
export interface ResultListPostInfo extends BasicResponse {
    "data": PostInfo[],
    // "description": "响应数据",,
}

// "返回结果"
export interface ResultInteger extends BasicResponse {
    "data": number,
    // "description": "响应数据",
}

// "返回结果"
export interface ResultListNoticeInfo extends BasicResponse {
    "data": NoticeInfo[]
    // "description": "响应数据",
}

// "谁对一个帖子中的实体进行了什么动作actionUser.username + actionType + \"了您的\" + entityType + \"(\" +\n        entityContent + \"):\" + actionContent举例：东九小韭菜评论了您的帖子（今晚三国杀）:约呀"
export interface NoticeInfo {
    "actionUser": User,
    "postId": number,
    // "description": "帖子Id",
    "entityType": string,
    // "description": "被动作指向实体的类型，如树洞，评论等"
    "entityContent": string,
    // "description": "被动作指向实体的内容，如树洞、评论的内容"
    "actionContent": string,
    // "description": "动作内容，如评论树洞的具体内容"
}

// "评论"
export interface Comment {
    "posterId": string,
    // "description": "发布评论人的学号"
    "entityType": number,
    // "description": "被评论实体的类型"
    "entityId": number,
    // "description": "被评论实体的ID"
    "content": string,
    // "description": "评论内容"
    "createTime": string,
    // "description": "评论时间"
    // "format": "date-time"

}

// "封装评论的详细数据，包括评论内容，发布者信息，评论信息，评论的分页信息"
export interface CommentDetails {
    "post": Comment,
    "poster": User,
    "page": Page,
    "deep": number,
    // "description": "评论深度"
}

// "封装帖子的详细数据，包括帖子内容，发布者信息，评论信息，评论的分页信息"
export interface PostDetailsPost {
    "post": Post,
    "poster": User,
    "commentDetails": CommentDetails[],
    "page": Page
}

// "返回结果"
export interface ResultPostDetailsPost extends BasicResponse {
    "data": PostDetailsPost,
}

// "记录分页信息，并封装当前页的数据"
export interface Page {
    "currentPage": number,
    // "description": "当前页码"
    "pageSize": number,
    // "description": "每页大小",
    "numOfPostsOnPage": number
    // "description": "当前页上的帖子数"
    "totalPage": number,
    // "description": "总页数"
    "numOfPosts": number,
    // "description": "总帖子数"
}

// "响应数据"
export interface CardContainsPoster {
    "id": number,
    "imageUrls": string[],
    // "description": "照片url集合"
    "age": string,
    // "description": "年级"
    "aboutMe": string,
    // "title": "关于我",
    // "description": "自我介绍"
    "goal": 0 | 1 | 2 | 3 | -1,
    // "title": "交友目标",
    // "description": "0-恋爱， 1-电子游戏， 2-桌游， 3-学习, -1-默认"
    "expected": string,
    // "description": "期望的TA",
    // "default": "描述期望中的理想征友对象",
    "create_time": string,
    // "description": "发布时间",
    // "format": "date-time"
    "poster": User
}

// "返回结果"
export interface ResultCardContainsPoster extends BasicResponse {
    "data": CardContainsPoster,
}

// "返回结果"
export interface ResultListCardContainsPoster extends BasicResponse {
    "data": CardContainsPoster[],
    // "description": "响应数据"
}