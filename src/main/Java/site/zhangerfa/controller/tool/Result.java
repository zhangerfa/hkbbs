package site.zhangerfa.controller.tool;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "返回结果")
public class Result {
    @Schema(description = "响应数据")
    private Object data; // 封装响应数据，可能为任意类型，用Object对象接收
    @Schema(description = "请求处理状态码")
    private Integer code;
    @Schema(description = "请求处理结果信息")
    private String msg;

    public Result(){}

    // data和code是必须的，msg在查询成功时不需要
    public Result(Integer code, Object data){
        this.data = data;
        this.code = code;
    }

    public Result(Integer code, Object data, String msg){
        this(code, data);
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
