package com.dl.mjweb.handle;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.dl.common.exception.BadRequestException;
import com.dl.common.exception.BeanUtilsException;
import com.dl.common.pojo.DlException;
import com.dl.common.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BeanUtilsException.class)
    public void doHandlerBeanUtilsException(BeanUtilsException e){
        DateTime date = DateUtil.date();
        DlException exception = new DlException();
        exception.setTime(date);

        String stackTrace = ExceptionUtils.getStackTrace(e);
        exception.setExceptionMsg(stackTrace);
        log.debug("BeanUtilsException :"+stackTrace);
        System.out.println("发生错误："+stackTrace);
    }

    @ExceptionHandler(BadRequestException.class)
    public void doHandlerBadRequestException(BadRequestException e){
        DateTime date = DateUtil.date();
        DlException exception = new DlException();
        exception.setTime(date);

        String stackTrace = ExceptionUtils.getStackTrace(e);
        exception.setExceptionMsg(stackTrace);
        log.debug("BadRequestException :"+stackTrace);
        System.out.println("发生错误："+stackTrace);
    }

    @ExceptionHandler(RuntimeException.class)
    public void doHandlerRuntimeException(RuntimeException e){
        DateTime date = DateUtil.date();
        DlException exception = new DlException();
        exception.setTime(date);

        String stackTrace = ExceptionUtils.getStackTrace(e);
        exception.setExceptionMsg(stackTrace);
        log.debug("BadRequestException :"+stackTrace);
        System.out.println("发生错误："+stackTrace);
    }
}
