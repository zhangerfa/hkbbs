package site.zhangerfa.controller.exception;

public class BusinessException extends RuntimeException{
    private final Integer code;

    public BusinessException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
