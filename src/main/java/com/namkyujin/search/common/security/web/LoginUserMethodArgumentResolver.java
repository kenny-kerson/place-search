package com.namkyujin.search.common.security.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.security.Principal;
import java.util.Objects;

@Slf4j
public class LoginUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return LoginUserPrincipal.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
            throws Exception {

        Principal userPrincipal = webRequest.getUserPrincipal();
        try {
            if (Objects.nonNull(userPrincipal)
                    && LoginUserHttpServletRequest.LoginUserPrincipalWrapper.class.isAssignableFrom(userPrincipal.getClass())) {

                return ((LoginUserHttpServletRequest.LoginUserPrincipalWrapper) userPrincipal).getPrincipal();
            }
        } catch (Exception e) {
            log.error("Failed to get principal. Cause By {}", e.getMessage(), e);
        }

        return null;
    }
}
