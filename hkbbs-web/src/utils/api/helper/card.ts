import { req } from "./req"
import { ResultListCardContainsPoster, ResultCardContainsPoster, ResultBoolean } from "../../schemas"

//card 卡片
export class Card {

    private static path: string = "/cards/"

    static get(args: { currentPage: number, pageSize: number, posterId: string, goal: string }): Promise<ResultListCardContainsPoster> {
        return req.get({
            url: this.path,
            params: args
        })
    }

    static getById(args: { cardId: number }): Promise<ResultCardContainsPoster> {
        return req.get({
            url: this.path + args.cardId.toString()
        })
    }

    static publish(args: { aboutMe: string, expected: string, goal: string, images: string[] }): Promise<ResultBoolean> {
        return req.post({
            url: this.path,
            data: {
                newCard: args
            }
        })
    }


    static update(args: { cardId: number, aboutMe: string, expected: string }): Promise<ResultBoolean> {
        return req.put({
            url: this.path + args.cardId.toString(),
            data: {
                aboutMe: args.aboutMe,
                expected: args.expected
            }
        })
    }

    static delete(args: { cardId: number }): Promise<ResultBoolean> {
        return req.delete({
            url: this.path + args.cardId.toString()
        })
    }
}