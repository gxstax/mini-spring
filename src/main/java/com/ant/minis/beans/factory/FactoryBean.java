package com.ant.minis.beans.factory;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/25 10:03
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}