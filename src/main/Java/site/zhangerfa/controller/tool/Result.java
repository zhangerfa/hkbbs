package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "返回结果")
public class Result<T> {
    @Schema(description = "响应数据")
    private T data; // 封装响应数据，可能为任意类型，用Object对象接收
    @Schema(description = "请求处理状态码")
    private Integer code;
    @Schema(description = "请求处理结果信息")
    private String msg;

    public Result(){}

    public Result(Integer code){
        this.code = code;
        this.data = null;
        this.msg = "成功";
    }

    public Result(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, T data){
        this(code);
        this.data = data;
    }

    public Result(Integer code, T data, String msg){
        this(code, data);
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
