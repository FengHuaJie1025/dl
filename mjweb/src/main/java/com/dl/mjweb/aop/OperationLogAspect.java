package com.dl.mjweb.aop;

import com.dl.common.model.entity.Account;
import com.dl.common.model.entity.DlOperationLog;
import com.dl.mjweb.service.IDlOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private IDlOperationLogService operationLogService;

    //@Pointcut注解用于描述方法,定义切入点
    //bean(sysUserServiceImpl)为一种切入点表达式
    //sysUserServiceImpl为spring容器中的一个bean的名字
    //切入点表达式中的bean表达式
    //@Pointcut("bean(sysUserServiceImpl)")
    //@Pointcut("bean("*ServiceImpl")
    //切入点表达式中的注解表达式
    //由此注解描述的方法作为切入点.
    @Pointcut("@annotation(com.dl.mjweb.aop.OperationLogAnn)")
    public void logPointCut() {
    }

    /**
     * 前置通知 用于记录session中的用户  因为在after切面中 response已经响应，session已经关闭
     */
    @Before("logPointCut()")
    public void before(JoinPoint joinPoint)
            throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Account account = (Account) request.getSession().getAttribute("account");
        request.setAttribute("account", account);
    }

    @After("logPointCut()")
    public void after(JoinPoint joinPoint)
            throws Throwable {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            Account account = (Account) request.getSession().getAttribute("account");
            if (Objects.isNull(account)) {
                account.setName("未登录账户");
            }
            String ip = getRemoteHost(request);
            String param = getParams(joinPoint.getArgs());
            String msg = getControllerMethodDescription(joinPoint);
            DlOperationLog operationLog = DlOperationLog.builder().account(account.getName())
                    .operateTime(new Date()).ipAddress(ip).param(param).msg(msg).build();
            operationLogService.save(operationLog);
        } catch (Exception e) {
            log.debug("===== DlOperationLog insert error! =====");
        }

    }

    private String getControllerMethodDescription(JoinPoint joinPoint) throws ClassNotFoundException {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        Method[] methods = targetClass.getMethods();

        String value = "";
        for (Method method : methods) {
            if (Objects.equals(method.getName(), methodName)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                //这里判断一下方法中的参数个数是否相同  防止方法重载的情况
                //但是有个bug，万一参数个数相同参数类型不同也会进入这里
                if (parameterTypes.length == args.length) {
                    value = method.getAnnotation(OperationLogAnn.class).value();
                    break;
                }
            }
        }
        return value;
    }

    private String getParams(Object[] args) {
        for (Object param : args) {
            if (param instanceof HttpServletRequest || param instanceof HttpServletResponse) {
                continue;
            } else if (param instanceof CommonsMultipartFile) {
                return "upload file : " + ((CommonsMultipartFile) param).getOriginalFilename();
            } else {
                //按照框架规范，这里的参数都是@PathVariable @RequestParam 这里不做处理
                if (!isPrimitive(param)) {
                    log.debug(param.toString());
                    return param.toString();
                }
            }
        }
        return "";
    }

    private boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }

}
