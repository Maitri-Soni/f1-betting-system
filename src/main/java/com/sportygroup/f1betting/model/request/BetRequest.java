package com.sportygroup.f1betting.model.request;

import lombok.Data;

@Data
public class BetRequest {
    private int userId;
    private String sessionId;
    private String driverId;
    private double amount;
}
