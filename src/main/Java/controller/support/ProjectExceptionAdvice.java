package controller.support;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 异常处理的AOP通知
@RestControllerAdvice // REST风格的为控制层对象提供功能增强的AOP通知
public class ProjectExceptionAdvice {
    @ExceptionHandler(Exception.class) // 标注进行处理的异常类型
    public Result doException(Exception ex){
        return new Result(Code.Exception, null);
    }
}
