package com.ant.minis.core.env;

/**
 * <p>
 * 用于属性获取
 * </p>
 *
 * @author Ant
 * @since 2023/4/17 01:16
 **/
public interface Environment extends PropertyResolver {
    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);
}
