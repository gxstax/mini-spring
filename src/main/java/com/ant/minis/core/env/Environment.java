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
    /**
     * 返回生效的环境配置集合
     *
     * @return
     */
    String[] getActiveProfiles();

    /**
     * 返回默认的环境配置集合
     *
     * @return
     */
    String[] getDefaultProfiles();

    /**
     * 判断给定的一个活多个环境是否是生效的
     *
     * @param profiles
     * @return
     */
    boolean acceptsProfiles(String... profiles);
}
