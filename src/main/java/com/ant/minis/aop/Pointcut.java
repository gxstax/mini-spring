package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/28 18:27
 */
public interface Pointcut {
    MethodMatcher getMethodMatcher();
}
