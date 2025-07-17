package com.sportygroup.f1betting.service;

import com.sportygroup.f1betting.model.entity.Bet;
import com.sportygroup.f1betting.model.entity.User;
import com.sportygroup.f1betting.model.request.BetRequest;
import com.sportygroup.f1betting.repo.BetRepository;
import com.sportygroup.f1betting.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BetService {

    private final UserRepository userRepository;
    private final BetRepository betRepository;

    /**
     * Places a bet for the given userId after validating the user and ensuring sufficient account balance.
     * @param request
     * @return message
     */
    public String placeBet(BetRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.getUserId());

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (request.getAmount() > user.getBalance()) {
            return "Insufficient balance";
        }

        // Randomly assign odds 2, 3, or 4 (as per requirement)
        int[] oddsOptions = {2, 3, 4};
        int odds = oddsOptions[new Random().nextInt(oddsOptions.length)];

        Bet bet = new Bet();
        bet.setUserId(request.getUserId());
        bet.setDriverId(request.getDriverId());
        bet.setSessionId(request.getSessionId());
        bet.setAmount(request.getAmount());
        bet.setOdds(odds);
        bet.setStatus("PLACED");

        // Save the bet
        betRepository.save(bet);

        // Deduct from user balance
        user.setBalance(user.getBalance() - request.getAmount());
        userRepository.save(user);

        return "Bet placed successfully with odds " + odds;
    }
}
