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

    private void addArgumentValue(Integer key, ArgumentValue value) {
        this.indexedArgumentValues.put(key, value);
    }

    public boolean hasIndexArgumentValue(int index) {
        return this.indexedArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexArgumentValue(int index) {
        return this.indexedArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type) {
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }

    private void addGenericArgumentValue(ArgumentValue newValue) {
        if (null != newValue.getName()) {
            Iterator<ArgumentValue> iterator = this.genericArgumentValues.iterator();
            for (Iterator<ArgumentValue> it =
                 this.genericArgumentValues.iterator(); it.hasNext();) {
                ArgumentValue currentArgument = it.next();
                if (currentArgument.getName().equals(currentArgument.getName())) {
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

