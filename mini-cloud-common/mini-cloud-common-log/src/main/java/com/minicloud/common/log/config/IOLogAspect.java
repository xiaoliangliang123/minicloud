package com.minicloud.common.log.config;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.minicloud.common.constant.MiniCloudCommonConstant;
import com.minicloud.common.dto.IOLogRecordDTO;
import com.minicloud.common.log.annotation.IOLogRecorder;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.Objects;

import static com.minicloud.common.constant.MiniCloudCommonConstant.RequestHeaderConstant.CONTENT_TYPE;
import static com.minicloud.common.constant.MiniCloudCommonConstant.RocketMQConstant.IO_lOG;

@Aspect
public class IOLogAspect {

    @Autowired
    RocketMQTemplate rocketMQTemplate;

    @Pointcut("@annotation(com.minicloud.common.log.annotation.IOLogRecorder)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object record(ProceedingJoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String url = request.getRequestURI();
        String contentType= request.getHeader(CONTENT_TYPE);
        String method = request.getMethod();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        IOLogRecorder ioLogRecorder = methodSignature.getMethod().getAnnotation(IOLogRecorder.class);
        System.out.println(methodSignature.getMethod().getName());
        Object[] args = joinPoint.getArgs();
        String inArgs = JSONUtil.toJsonStr(args);
        Object response =  joinPoint.proceed();
        long timestamp = System.nanoTime();
        IOLogRecordDTO ioLogRecordDTO = IOLogRecordDTO.builder().keyword(ioLogRecorder.keyword()).description(ioLogRecorder.descrition()).url(url).contentType(contentType).method(method).args(inArgs).response(response).dateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))).timestamp(timestamp).build();
        rocketMQTemplate.send(IO_lOG, MessageBuilder.withPayload(ioLogRecordDTO).build());
        return response;
    }

}
