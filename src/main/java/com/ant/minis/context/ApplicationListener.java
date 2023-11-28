package com.ant.minis.context;

import lombok.extern.slf4j.Slf4j;

import java.util.EventListener;

/**
 * <p>
 * 事件监听
 * </p>
 *
 * @author Ant
 * @since 2023/11/23 17:03
 */
//@Slf4j
public class ApplicationListener implements EventListener {

    void onApplicationEvent(ApplicationEvent event) {
        //
//        log.info(event.toString());
    }
}
