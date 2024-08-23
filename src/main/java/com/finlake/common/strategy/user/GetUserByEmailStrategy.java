package com.finlake.common.strategy.user;

import com.finlake.common.strategy.RetrieveStrategy;
import com.finlake.model.User;

import java.util.function.Function;

public class GetUserByEmailStrategy implements RetrieveStrategy<User> {
    private final UserRetrieveStrategyImpl userRetrieveStrategy;

    public GetUserByEmailStrategy(UserRetrieveStrategyImpl userRetrieveStrategy) {
        this.userRetrieveStrategy = userRetrieveStrategy;
    }

    @Override
    public Function<String, User> getStrategy() {
        return userRetrieveStrategy::findUserByEmail;
    }
}

