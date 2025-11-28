package com.example.umc9th.global.web.page;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OneBasedPageable {
}