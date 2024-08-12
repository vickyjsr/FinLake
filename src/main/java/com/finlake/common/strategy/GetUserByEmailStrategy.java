package com.finlake.common.strategy;

import com.finlake.model.User;

import java.util.function.Function;

public class GetUserByEmailStrategy implements RetrieveStrategy {
    private final UserRetrieveStrategyImpl userRetrieveStrategy;

    public GetUserByEmailStrategy(UserRetrieveStrategyImpl userRetrieveStrategy) {
        this.userRetrieveStrategy = userRetrieveStrategy;
    }

    @Override
    public Function<String, User> getStrategy() {
        return userRetrieveStrategy::findUserByEmail;
    }
}

