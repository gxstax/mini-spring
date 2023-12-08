package com.ant.minis.web.bind.support;

import com.ant.minis.web.bind.WebDataBinder;

/**
 * <p>
 * Web 绑定初始化
 * </p>
 *
 * @author Ant
 * @since 2023/12/4 9:22
 */
public interface WebBindingInitializer {
    void initBinder(WebDataBinder binder);
}
