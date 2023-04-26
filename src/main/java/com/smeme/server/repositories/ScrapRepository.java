package com.smeme.server.repositories;

import com.smeme.server.models.Scrap;
import com.smeme.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    List<Scrap> findByUser(User user);
}
