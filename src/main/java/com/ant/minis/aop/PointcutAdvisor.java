package com.ant.minis.aop;

/**
 * <p>
 * 支持pointcut的Advisor
 * </p>
 *
 * @author Ant
 * @since 2023/12/28 18:29
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
