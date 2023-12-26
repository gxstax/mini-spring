package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/25 10:55
 */
public interface AopProxy {
    Object getProxy();

    Object getProxy(ClassLoader var1);
}
