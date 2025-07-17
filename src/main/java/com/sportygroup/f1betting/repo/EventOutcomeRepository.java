package com.sportygroup.f1betting.repo;

import com.sportygroup.f1betting.model.entity.EventOutcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventOutcomeRepository extends JpaRepository<EventOutcome, String> {

}
