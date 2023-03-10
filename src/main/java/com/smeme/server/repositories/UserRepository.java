package com.smeme.server.repositories;

import com.smeme.server.models.Social;
import com.smeme.server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findBySocialAndSocialId(Social social, String socialId);

    @Query("select u.id from User u")
    public Long findIdBySocialId(String socialId);

    public boolean existsBySocialId(String socialId);
    public boolean existsByRefreshToken(String refreshToken);
}
