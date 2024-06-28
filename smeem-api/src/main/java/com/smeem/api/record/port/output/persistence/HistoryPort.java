package com.smeem.api.record.port.output.persistence;

import com.smeem.api.record.domain.History;

public interface HistoryPort {
    void save(History history);
}
