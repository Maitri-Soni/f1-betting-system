package com.sportygroup.f1betting.controller;

import com.sportygroup.f1betting.model.request.BetRequest;
import com.sportygroup.f1betting.model.request.EventOutcomeRequest;
import com.sportygroup.f1betting.model.response.EventResponse;
import com.sportygroup.f1betting.service.BetService;
import com.sportygroup.f1betting.service.EventService;
import com.sportygroup.f1betting.service.OutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class F1BettingController {

    private final EventService eventService;
    private final BetService betService;
    private final OutcomeService outcomeService;

    // 1. View Events
    @GetMapping("/events")
    public List<EventResponse> getEvents(
            @RequestParam(required = false) String year,
            @RequestParam(required = false) String sessionType,
            @RequestParam(required = false) String country
    ) {
        return eventService.getFilteredEvents(year, sessionType, country);
    }

    // 2. Place a bet
    @PostMapping("/bets")
    public String placeBet(@RequestBody BetRequest request) {
        return betService.placeBet(request);
    }

    // 3. Save event outcome and process bets
    @PostMapping("/event-outcome")
    public String saveOutcome(@RequestBody EventOutcomeRequest request) {
        return outcomeService.saveOutcome(request);
    }
}
