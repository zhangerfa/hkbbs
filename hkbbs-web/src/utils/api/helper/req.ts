import Request from "../request";

const BASE_URL = import.meta.env ? "/api" : "/prod-api";
const TIMEOUT = 10000;

export const req = new Request(BASE_URL, TIMEOUT);
