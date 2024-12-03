package com.smeem.application.port.output.cache;

import java.util.Optional;

public interface CachePort {
    void setBit(String key, long offset, boolean value);
    boolean getBit(String key, long offset);
    String getBitmap(String key);

    Optional<Integer> getInt(String key);
    void setInt(String key, int value);
    void incrementInt(String key);
}
