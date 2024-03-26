package com.github.jovialen.motor.render.resource;

import org.tinylog.Logger;

import java.util.HashMap;
import java.util.Map;

public class ResourceStorage<K, V extends DestructibleResource> implements DestructibleResource {
    private final ResourceProvider<K, V> provider;
    private final Map<K, V> storage = new HashMap<>();

    public ResourceStorage(ResourceProvider<K, V> provider) {
        this.provider = provider;
    }

    @Override
    public void destroy() {
        storage.forEach((k, v) -> v.destroy());
    }

    public V get(K key) {
        if (!storage.containsKey(key)) {
            Logger.tag("RENDER").debug("Creating resource for {}", key);
            storage.put(key, provider.provide(key));
        }
        return storage.get(key);
    }
}
