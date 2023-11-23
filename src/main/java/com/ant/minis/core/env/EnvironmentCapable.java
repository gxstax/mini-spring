package com.ant.minis.core.env;

/**
 * <p>
 * 提供获取 Environment 实例的能力
 * </p>
 *
 * @author Ant
 * @since 2023/4/17 01:15
 **/
public interface EnvironmentCapable {

    /**
     * Return the {@link Environment} associated with this component.
     */
    Environment getEnvironment();

}
