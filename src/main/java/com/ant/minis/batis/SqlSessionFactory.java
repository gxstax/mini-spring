package com.ant.minis.batis;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/12/19 10:13
 */
public interface SqlSessionFactory {
    SqlSession openSession();

    MapperNode getMapperNode(String name);
}
