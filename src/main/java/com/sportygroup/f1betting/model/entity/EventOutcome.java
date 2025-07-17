package com.sportygroup.f1betting.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_outcome")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventOutcome {
    @Id
    private String sessionId;
    private String winnerDriverId;
}
