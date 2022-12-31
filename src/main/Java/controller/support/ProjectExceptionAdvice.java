package controller.support;

import exception.BusinessException;
import exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 异常处理的AOP通知
@RestControllerAdvice // REST风格的为控制层对象提供功能增强的AOP通知
public class ProjectExceptionAdvice {

    // 处理系统异常
    @ExceptionHandler(SystemException.class) // 标注进行处理的异常类型
    public Result doSystemException(SystemException ex){
        // 记录日志
        // 发消息给运维
        // 发消息给开发人员

        // 返回响应
        // 异常由数据层、业务层捕获并封装为自定义异常
        // 在自定义异常对象中以及封装了异常编码和返回给用户的信息
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    // 处理业务异常（用户不当操作引起的异常）
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        return new Result(ex.getCode(), null, ex.getMessage());
    }

    // 处理其他异常（由后端bug引起的异常）
    // 未被两个自定义异常处理器处理的其他所有异常将有该处理器处理
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        // 记录日志
        // 将异常对象发送给开发人员

        // 安抚用户
        return new Result(Code.SYSTEM_UNKNOW_EXCEPTION,
                null, "系统繁忙，请重试！");
    }
}
