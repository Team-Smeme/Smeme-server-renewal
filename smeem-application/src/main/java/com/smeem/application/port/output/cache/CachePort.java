package com.smeem.application.port.output.cache;

public interface CachePort {
    void setBit(String key, long offset, boolean value);
    boolean getBit(String key, long offset);
}
