package com.smeme.server.repositories;

import com.smeme.server.models.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
}
