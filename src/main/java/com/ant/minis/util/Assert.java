package com.ant.minis.util;

import com.sun.istack.internal.Nullable;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/30 18:45
 */
public abstract class Assert {
    public Assert() {}

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
