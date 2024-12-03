package com.smeem.application.port.output.persistence;

import java.time.LocalDate;

public interface VisitPort {
    void update(LocalDate date, String bitmap);
}
