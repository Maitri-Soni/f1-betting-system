package com.sportygroup.f1betting.repo;

import com.sportygroup.f1betting.model.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Long> {
    List<Bet> findBySessionId(String sessionId);
    List<Bet> findByUserId(int userId);
}
