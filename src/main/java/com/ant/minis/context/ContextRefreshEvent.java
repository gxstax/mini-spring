
package com.ant.minis.context;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/23 17:04
 */
public class ContextRefreshEvent extends ApplicationEvent{

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ContextRefreshEvent(Object source) {
        super(source);
    }

    public String toString() {
        return this.msg;
    }
}
