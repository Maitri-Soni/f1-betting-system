package com.sportygroup.f1betting.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class EventResponse {
    private String sessionId;
    private String sessionType;
    private String country;
    private String date;
    private List<DriverMarket> driverMarkets;
}
