package com.minicloud.common.auth.config;

import cn.hutool.core.util.ReUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @Author alan.wang
 * @desc: 将一些Ignore url 忽略掉
 */
@ConfigurationProperties(prefix = "minicloud.auth.ignore")
public class MiniCloudIgnoreAndFeignUrlIniter implements InitializingBean, ApplicationContextAware {


    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    @Autowired
    private ApplicationContext applicationContext;


    @Getter
    @Setter
    private List<String> urls = new ArrayList<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(info -> {
            HandlerMethod handlerMethod = map.get(info);

            // 获取方法上边的注解 替代path variable 为 *
            MiniCloudFeignInterface miniCloudFeignInterface = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), MiniCloudFeignInterface.class);
            Optional.ofNullable(miniCloudFeignInterface)
                    .filter(m->!miniCloudFeignInterface.enableAuth()).ifPresent(feignInterface -> info.getPatternsCondition().getPatterns()
                            .forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, "*"))));


        });

    }




    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
