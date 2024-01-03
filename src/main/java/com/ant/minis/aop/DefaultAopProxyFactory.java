package com.ant.minis.aop;

import java.lang.reflect.Proxy;

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
    public AopProxy createAopProxy(Object target, PointcutAdvisor advisor) {
        // Check if the target is an interface or a proxy class
        if (target.getClass().isInterface() || Proxy.isProxyClass(target.getClass())) {
            // Create a JDK dynamic AOP proxy
            return new JdkDynamicAopProxy(target, advisor);
        }
        // Create a CGLIB AOP proxy
        return new CglibAopProxy(target, advisor);
    }
}
