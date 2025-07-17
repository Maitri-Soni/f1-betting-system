package com.sportygroup.f1betting.model.request;

import lombok.Data;

@Data
public class EventOutcomeRequest {
    private String sessionId;
    private String winningDriverId;
}
