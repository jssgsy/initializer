package com.univ.initializer.exception;


import com.univ.initializer.util.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注意，此bean必须能被component-scan扫描到，否则不起作用
 * @author univ
 * 2022/9/05
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    /**
     * 项目中所有抛出的UnsupportedOperationException及其子异常都在这里处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {BizException.class})
    public R<String> catchUnsupportedOperationException(BizException exception) {
        log.error("业务异常， message:{}, 具体堆栈:{}", exception.getMessage(), exception);
        return R.fail(exception.getMessage());
    }

    /**
     * 项目中所有抛出的RuntimeException及其子异常都在这里处理
     * 可以直接使之成为一个controller(使用@Controller修饰)，并返回json数据
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public R<String> catchRuntimeException(Exception exception) {
        log.error("Exception异常， message:{}, 具体堆栈:{}", exception.getMessage(), exception);
        return R.fail(exception.getMessage());
    }

}
