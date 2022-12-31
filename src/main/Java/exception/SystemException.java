package exception;

// 系统异常（不是用户和后端老哥的问题引发的异常）
// 继承RuntimeException就可以在运行时自动向上抛，无需手动抛
public class SystemException extends RuntimeException{
    private final Integer code; // 自定义异常之间无法直接区分，设置编号进行区分

    public SystemException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public SystemException(Integer code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
