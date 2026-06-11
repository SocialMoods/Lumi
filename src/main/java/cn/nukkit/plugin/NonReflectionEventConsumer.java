package cn.nukkit.plugin;

import cn.nukkit.event.Event;

@FunctionalInterface
@Deprecated(since = "1.6.1", forRemoval = true)
public interface NonReflectionEventConsumer<T extends Event> {
    void execute(T event);
}
