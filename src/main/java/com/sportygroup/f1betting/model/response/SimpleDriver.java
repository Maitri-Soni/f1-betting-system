package com.sportygroup.f1betting.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleDriver {
    private String driverId;
    private String name;
}