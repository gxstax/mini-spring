package com.ant.minis.core.env;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/4/17 01:15
 **/
public interface PropertyResolver {
    /**
     * Return whether the given property key is available for resolution,
     * i.e. if the value for the given key is not {@code null}.
     */
    boolean containsProperty(String key);

    /**
     * Return the property value associated with the given key,
     * or {@code null} if the key cannot be resolved.
     * @param key the property name to resolve
     * @see #getProperty(String, String)
     * @see #getProperty(String, Class)
     * @see #getRequiredProperty(String)
     */
    String getProperty(String key);

    /**
     * Return the property value associated with the given key, or
     * {@code defaultValue} if the key cannot be resolved.
     * @param key the property name to resolve
     * @param defaultValue the default value to return if no value is found
     * @see #getRequiredProperty(String)
     * @see #getProperty(String, Class)
     */
    String getProperty(String key, String defaultValue);

    /**
     * Return the property value associated with the given key,
     * or {@code null} if the key cannot be resolved.
     * @param key the property name to resolve
     * @param targetType the expected type of the property value
     * @see #getRequiredProperty(String, Class)
     */
    <T> T getProperty(String key, Class<T> targetType);

    /**
     * Return the property value associated with the given key,
     * or {@code defaultValue} if the key cannot be resolved.
     * @param key the property name to resolve
     * @param targetType the expected type of the property value
     * @param defaultValue the default value to return if no value is found
     * @see #getRequiredProperty(String, Class)
     */
    <T> T getProperty(String key, Class<T> targetType, T defaultValue);

    /**
     * Return the property value associated with the given key (never {@code null}).
     * @throws IllegalStateException if the key cannot be resolved
     * @see #getRequiredProperty(String, Class)
     */
    String getRequiredProperty(String key) throws IllegalStateException;

    /**
     * Return the property value associated with the given key, converted to the given
     * targetType (never {@code null}).
     * @throws IllegalStateException if the given key cannot be resolved
     */
    <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException;

    /**
     * 处理占位符
     */
    String resolvePlaceholders(String text);

    /**
     * Resolve ${...} placeholders in the given text, replacing them with corresponding
     * property values as resolved by {@link #getProperty}. Unresolvable placeholders with
     * no default value will cause an IllegalArgumentException to be thrown.
     * @return the resolved String (never {@code null})
     * @throws IllegalArgumentException if given text is {@code null}
     * or if any placeholders are unresolvable
     * @see org.springframework.util.SystemPropertyUtils#resolvePlaceholders(String, boolean)
     */
    String resolveRequiredPlaceholders(String text) throws IllegalArgumentException;
}
