package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/25 10:54
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(Object target);
}
