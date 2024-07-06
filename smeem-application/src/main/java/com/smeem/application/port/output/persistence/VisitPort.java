package com.smeem.application.port.output.persistence;

import com.smeem.application.domain.visit.Visit;

public interface VisitPort {
    void visit(Visit visit);
    boolean isExistByMemberAndToday(long memberId);
}
