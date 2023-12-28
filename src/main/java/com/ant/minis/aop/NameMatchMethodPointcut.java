package com.ant.minis.aop;

import com.ant.minis.util.PatternMatchUtils;

import java.lang.reflect.Method;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/28 18:44
 */
public class NameMatchMethodPointcut implements MethodMatcher, Pointcut {
    private String mappedName = "";

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        if (mappedName.equals(method.getName()) ||isMatch(method.getName(), mappedName) ) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * 判断方法名是否匹配给定的模式
     * </p>
     *
     * @param methodName
     * @param mappedName
     * @return boolean
     */
    protected boolean isMatch(String methodName, String mappedName) {
        return PatternMatchUtils.simpleMatch(mappedName, methodName);
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
