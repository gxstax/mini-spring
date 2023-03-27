package com.ant.minis.beans;


import java.util.*;

/**
 * <p>
 * 多参数的描述类
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 12:43
 **/
public class ArgumentValues {
    private final Map<Integer, ArgumentValue> indexedArgumentValues = new HashMap<>(0);
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    public ArgumentValues() {
    }

    public boolean hasIndexArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }

    public void addIndexedArgumentValues(Integer index, ArgumentValue argumentValue) {
        this.indexedArgumentValues.put(index, argumentValue);
    }

    public void addGenericArgumentValue(ArgumentValue newValue) {
        if (null != newValue.getName()) {
            Iterator<ArgumentValue> iterator = this.genericArgumentValues.iterator();
            for (Iterator<ArgumentValue> it =
                 iterator; it.hasNext();) {
                ArgumentValue currentArgument = it.next();
                if (currentArgument.getName().equals(newValue.getName())) {
                    it.remove();
                }
            }
            this.genericArgumentValues.add(newValue);
        }
    }

    public ArgumentValue getArgumentValue(String requireName) {
        for (ArgumentValue valueHolder : this.genericArgumentValues) {
            if (null != valueHolder.getName() &&
                    (null == requireName || valueHolder.getName().equals(requireName))) {
                continue;
            }
            return valueHolder;
        }
        return null;
    }

    public int getArgumentCount() {
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty() {
        return this.genericArgumentValues.isEmpty();
    }

}

