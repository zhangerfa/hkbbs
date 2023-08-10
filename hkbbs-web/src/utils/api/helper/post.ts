import { req } from "./req";
import {
  ResultListPostInfo,
  ResultBoolean,
  ResultPostDetailsPost,
} from "../../schemas";

//post 帖子
export class Post {
  private static path: string = "/posts/";

  static getList(args: {
    stuId: string;
    currentPage: number;
    pageSize: number;
    type: number;
  }): Promise<ResultListPostInfo> {
    return req.get({
      url: this.path + args.stuId,
      params: {
        type: args.type,
        currentPage: args.currentPage,
        pageSize: args.pageSize,
      },
    });
  }

  static getById(args: {
    postId: number;
    currentPage: number;
    pageSize: number;
  }): Promise<ResultPostDetailsPost> {
    return req.get({
      url: `/details/${args.postId}`,
      params: {
        currentPage: args.currentPage,
        pageSize: args.pageSize,
      },
    });
  }

  static publish(args: {
    title: string;
    content: string;
    type: number;
    images: string[];
  }): Promise<ResultBoolean> {
    return req.post({
      url: this.path,
      data: {
        newPost: args,
      },
    });
  }

  static comment(args: {
    entityType: number;
    entityId: number;
    content: string;
    postId: number;
  }): Promise<ResultBoolean> {
    return req.post({
      url: this.path + "comment",
      data: args,
    });
  }

  static deleteComment(args: { commentId: number }): Promise<ResultBoolean> {
    return req.delete({
      url: `/delete/comment/${args.commentId}`,
    });
  }

  static deletePost(args: { postId: number }): Promise<ResultBoolean> {
    return req.delete({
      url: this.path + "delete/" + args.postId.toString(),
    });
  }
}
