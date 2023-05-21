package org.elsfs.security.core.properties;

import lombok.Data;

import org.elsfs.security.util.KeyGeneratorUtils;
import org.elsfs.security.util.SaveRSA;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Pattern;

@Data
@ConfigurationProperties(prefix = "elsfs.security")
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware, BeanPostProcessor {

    private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

    // spring上下文
    private ApplicationContext applicationContext;

    // 前端请求接口的时候，如果在这个url里面，直接放行
    private List<String> urls = new ArrayList<>();

    private List<RequestMatcher> requestMatchers = new ArrayList<>();

    private String publicKeyPath;

    private String privateKeyPath;

    private Boolean KeyGenerator = false;

    // 替代字符串
    public String ASTERISK = "*";

    @Override
    public String toString() {

        var url = new StringBuffer();
        if (!urls.isEmpty()) {
            urls.forEach(u -> url.append(u).append("  "));
        }
        var requestMatcher = new StringBuffer();
        if (!requestMatchers.isEmpty()) {
            requestMatchers.forEach(r -> requestMatcher.append(r.toString()).append("    "));
        }
        return "PermitAllUrlProperties{" + "urls=" + url + ", requestMatchers=" + requestMatcher + '}';
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // ignoreUrlHandle();
        checkKey();
    }

    private void checkKey() throws IOException, NoSuchAlgorithmException {
        if (KeyGenerator && publicKeyPath == null) {
            String property = System.getProperty("user.dir");
            File file = new File(property + File.separator + "key");
            file.mkdirs();
            KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
            SaveRSA.save(keyPair, file);
        }
    }
    //
    // private void ignoreUrlHandle() {
    // /**
    // * requestMappingHandlerMapping
    // * controllerEndpointHandlerMapping
    // */
    // // 将整个项目所有bean都拿出来
    // RequestMappingHandlerMapping mapping =
    // getApplicationContext().getBean("requestMappingHandlerMapping",RequestMappingHandlerMapping.class);
    //
    // // 获取 每个url 与 方法、类的对应关系
    // var map = mapping.getHandlerMethods();
    // // info 是代表每个url对象
    // map.keySet().forEach(info -> {
    // // 获取类和方法的信息
    // var handlerMethod = map.get(info);
    // // 获取方法上边的注解，如果此方法被“ AllowAccess ”注解，则返回该方法，否则返回null
    // var method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(),
    // AllowAccess.class);
    // // method为null啥也不干，否则替代path variable 为 *
    // Optional.ofNullable(method).ifPresent(allowAccess ->
    // Objects.requireNonNull(info.getPatternsCondition().getPatterns())
    // .forEach(url -> urls.add(replaceAll(url, PATTERN, ASTERISK))));
    // // 获取类上边的注解，如果此类被“ AllowAccess ”注解，则返回该类，否则返回null
    // var controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(),
    // AllowAccess.class);
    // // controller为null啥也不干，否则替代path variable 为 *
    // Optional.ofNullable(controller).ifPresent(allowAccess ->
    // Objects.requireNonNull(info.getPatternsCondition().getPatterns())
    // .forEach(url -> urls.add(replaceAll(url, PATTERN, ASTERISK))));
    // });
    //
    // }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private String replaceAll(String text, Pattern regex, String replacement) {
        if (StringUtils.hasText(text)) {
            return text;
        }
        return regex.matcher(text).replaceAll(replacement);
    }

}
