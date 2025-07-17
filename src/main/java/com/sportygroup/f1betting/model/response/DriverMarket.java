package com.sportygroup.f1betting.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverMarket {
    private String driverId;
    private String driverName;
    private int odds;
}
