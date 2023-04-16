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
    private final Map<Integer, ConstructorArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ConstructorArgumentValue> genericConstructorArgumentValues = new LinkedList<>();

    public ConstructorArgumentValues() {
    }

    public boolean hasIndexArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ConstructorArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericConstructorArgumentValues.add(new ConstructorArgumentValue(value, type));
    }

    public void addIndexedArgumentValues(Integer index, ConstructorArgumentValue constructorArgumentValue) {
        this.indexedArgumentValues.put(index, constructorArgumentValue);
    }

    public void addGenericArgumentValue(ConstructorArgumentValue newValue) {
        if (null != newValue.getName()) {
            Iterator<ConstructorArgumentValue> iterator = this.genericConstructorArgumentValues.iterator();
            for (Iterator<ConstructorArgumentValue> it =
                 iterator; it.hasNext();) {
                ConstructorArgumentValue currentArgument = it.next();
                if (currentArgument.getName().equals(newValue.getName())) {
                    it.remove();
                }
            }
            this.genericConstructorArgumentValues.add(newValue);
        }
    }

    public ConstructorArgumentValue getArgumentValue(String requireName) {
        for (ConstructorArgumentValue valueHolder : this.genericConstructorArgumentValues) {
            if (null != valueHolder.getName() &&
                    (null == requireName || valueHolder.getName().equals(requireName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericConstructorArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericConstructorArgumentValues.isEmpty();
    }

}

