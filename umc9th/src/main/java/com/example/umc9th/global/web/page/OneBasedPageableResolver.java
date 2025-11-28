package com.example.umc9th.global.web.page;

import com.example.umc9th.global.apiPayload.exception.GeneralException;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class OneBasedPageableResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_SIZE = 10;   // 한 페이지 10개 고정

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OneBasedPageable.class)
                && Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {

        String pageParam = webRequest.getParameter("page"); // 쿼리 스트링 page
        int page = 1; // 기본값 1 페이지

        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                // 숫자가 아니면 BAD_REQUEST
                throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
            }
        }

        if (page < 1) {
            // 0 이하인 경우 에러
            throw new GeneralException(GeneralErrorCode.BAD_REQUEST);
        }

        // PageRequest는 0-based 이므로 -1
        return PageRequest.of(page - 1, DEFAULT_SIZE);
    }
}
