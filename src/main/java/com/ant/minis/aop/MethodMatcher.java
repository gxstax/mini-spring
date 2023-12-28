package com.ant.minis.aop;

import java.lang.reflect.Method;

/**
 * <p>
 * 方法的匹配算法
 * </p>
 *
 * @author Ant
 * @since 2023/12/27 18:46
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> targetClass);
}
