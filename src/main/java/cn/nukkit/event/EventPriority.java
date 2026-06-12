package cn.nukkit.event;

import lombok.Getter;
import org.densy.eventbus.api.EventPriorities;

/**
 * Event handler execution order.
 *
 * @author MagicDroidX
 * Nukkit Project
 */
@Getter
public enum EventPriority {
    /**
     * Highest-priority handler. Runs first.
     */
    LOWEST(EventPriorities.FIRST),
    /**
     * Runs early, but after {@link #LOWEST}.
     */
    LOW(EventPriorities.EARLY),
    /**
     * Default priority for most handlers.
     */
    NORMAL(EventPriorities.NORMAL),
    /**
     * Runs after {@link #NORMAL}.
     */
    HIGH(EventPriorities.LATE),
    /**
     * Runs late, just before {@link #MONITOR}.
     */
    HIGHEST(EventPriorities.LAST),
    /**
     * Monitoring handler. Use this for observing the final event state.
     */
    MONITOR(-1000);

    private final int slot;

    EventPriority(int slot) {
        this.slot = slot;
    }   
}
