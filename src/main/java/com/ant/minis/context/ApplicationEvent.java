package com.ant.minis.context;


import java.util.EventObject;

/**
 * <p>
 * 所有应用时间的超类
 * </p>
 *
 * @author Ant
 * @since 2023/3/17 01:00
 **/
public class ApplicationEvent extends EventObject {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}

