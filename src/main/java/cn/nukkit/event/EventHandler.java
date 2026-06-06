package cn.nukkit.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as an event handler.
 * <p>
 * The method must take exactly one argument that extends {@link Event}.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    /**
     * Defines the handler priority.
     *
     * @return handler priority
     */
    EventPriority priority() default EventPriority.NORMAL;

    /**
     * Allows the handler to run asynchronously when supported by the bus.
     *
     * @return {@code true} to run asynchronously
     */
    boolean async() default false;

    /**
     * Controls whether cancelled events should be ignored.
     *
     * @return {@code true} to skip cancelled events
     */
    boolean ignoreCancelled() default false;
}
