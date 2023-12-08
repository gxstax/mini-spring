package com.ant.minis.context;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author Ant
 * @since 2023/11/23 17:10
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    List<ApplicationListener> listeners = new ArrayList<>();

    /**
     * <p>
     * 事件发布
     * </p>
     *
     * @param event 事件
     */
    @Override
    public void publishEvent(ApplicationEvent event) {
        // 调用监听者的监听方法
        for (ApplicationListener listener : listeners) {
            listener.onApplicationEvent(event);
        }
    }

    /**
     * 添加监听事件
     *
     * @param listener
     */
    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }
}
