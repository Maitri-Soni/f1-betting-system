package com.sportygroup.f1betting.service;

import com.sportygroup.f1betting.model.entity.Bet;
import com.sportygroup.f1betting.model.entity.EventOutcome;
import com.sportygroup.f1betting.model.entity.User;
import com.sportygroup.f1betting.model.request.EventOutcomeRequest;
import com.sportygroup.f1betting.repo.BetRepository;
import com.sportygroup.f1betting.repo.EventOutcomeRepository;
import com.sportygroup.f1betting.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutcomeService {

    private final EventOutcomeRepository outcomeRepository;
    private final BetRepository betRepository;
    private final UserRepository userRepository;

    /**
     * Saves the outcome of a completed F1 event, updates related bets as won or lost, and adjusts user balances accordingly.
     * @param request
     * @return
     */
    public String saveOutcome(EventOutcomeRequest request) {
        // Save the outcome
        outcomeRepository.save(new EventOutcome(request.getSessionId(), request.getWinningDriverId()));

        // Retrieve all bets for this event
        List<Bet> bets = betRepository.findBySessionId(request.getSessionId());

        for (Bet bet : bets) {
            if (bet.getDriverId().equals(request.getWinningDriverId())) {
                bet.setStatus("WON");

                // Update user balance with winnings
                Optional<User> userOpt = userRepository.findById(bet.getUserId());
                userOpt.ifPresent(user -> {
                    double winnings = bet.getAmount() * bet.getOdds();
                    user.setBalance(user.getBalance() + winnings);
                    userRepository.save(user);
                });

            } else {
                bet.setStatus("LOST");
            }

            // Update bet status in DB
            betRepository.save(bet);
        }

        return "Event outcome processed and bets updated.";
    }
}
