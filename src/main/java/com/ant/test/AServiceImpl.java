package com.ant.test;


/**
 * <p>
 * 测试用例类
 * </p>
 *
 * @author Ant
 * @since 2023/3/16 00:47
 **/
public class AServiceImpl implements DemoService {
    private String property1;

    public AServiceImpl(String property1) {
        this.property1 = property1;
    }

    @Override
    public void sayHello() {
        System.out.println("a service say hello");
    }
}

