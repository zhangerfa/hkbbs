import { faker } from '@faker-js/faker/locale/zh_CN'

export const getMsg = {
    "code": 0,
    "msg": "faker"
}

export const getResultBool = {
    data: true,
    ...getMsg
}

export const getGender = () => faker.name.sex() === "male" ? "0" : "1"
export const getStuId = () => "M" + faker.datatype.number({ min: 100000000, max: 999999999 }).toString()
export const getImageUrl = () => faker.image.animals()
export const getTime = () => faker.date.between('2020-01-01T00:00:00.000Z', '2023-04-01T00:00:00.000Z')
export const getAge = () => faker.datatype.number({ min: 10, max: 99 }).toString()
export const getAboutMe = () => faker.lorem.sentence()
export const getGoal = () => faker.datatype.number({ min: 0, max: 3 })
export const getName = () => faker.name.fullName()
export const getHeaderUrl = () => faker.image.avatar()
export const getExpected = () => faker.lorem.sentence()
export const getPosterId = () => faker.datatype.number({ min: 100000, max: 999999 }).toString()
export const getContent = () => faker.lorem.paragraphs()