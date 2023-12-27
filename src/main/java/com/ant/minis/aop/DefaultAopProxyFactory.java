package com.ant.minis.aop;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/25 11:07
 */
public class DefaultAopProxyFactory implements AopProxyFactory {
    @Override
    public AopProxy createAopProxy(Object target, Advisor advisor) {
        return new JdkDynamicAopProxy(target, advisor);
    }
}
