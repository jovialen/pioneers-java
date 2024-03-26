package com.github.jovialen.motor.render.resource;

public interface ResourceProvider<K, V extends DestructibleResource> {
    V provide(K key);
}
