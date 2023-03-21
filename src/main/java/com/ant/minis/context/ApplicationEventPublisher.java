package com.ant.minis.context;

/**
 * <p>
 * 事件发布功能接口
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 00:58
 **/
public interface ApplicationEventPublisher {

    /**
     * <p>
     * 事件发布
     * </p>
     *
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);
}
