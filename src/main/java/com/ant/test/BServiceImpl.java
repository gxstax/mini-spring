package com.ant.test;


/**
 * <p>
 * 测试用例类
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 12:24
 **/
public class BServiceImpl implements DemoService {

    private String name;
    private Integer level;

    public BServiceImpl(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    @Override
    public void sayHello() {
        System.out.println("b service say hello");
    }

}

