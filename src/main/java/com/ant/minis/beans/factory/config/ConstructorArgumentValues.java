package com.ant.minis.beans.factory.config;


import java.util.*;

/**
 * <p>
 * 多参数的描述类
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 12:43
 **/
public class ConstructorArgumentValues {
    private final List<ConstructorArgumentValue> argumentValueList = new ArrayList<ConstructorArgumentValue>();

    public ConstructorArgumentValues() {
    }

    public void addArgumentValue(ConstructorArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        ConstructorArgumentValue argumentValue = this.argumentValueList.get(index);
        return argumentValue;
    }

    public int getArgumentCount() {
        return (this.argumentValueList.size());
    }

    public boolean isEmpty() {
        return (this.argumentValueList.isEmpty());
    }

}

