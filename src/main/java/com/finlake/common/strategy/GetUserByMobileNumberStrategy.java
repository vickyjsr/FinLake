package com.finlake.common.strategy;

import com.finlake.model.User;

import java.util.function.Function;

public class GetUserByMobileNumberStrategy implements RetrieveStrategy {
    private final UserRetrieveStrategyImpl userRetrieveStrategy;

    public GetUserByMobileNumberStrategy(UserRetrieveStrategyImpl userRetrieveStrategy) {
        this.userRetrieveStrategy = userRetrieveStrategy;
    }

    @Override
    public Function<String, User> getStrategy() {
        return userRetrieveStrategy::findUserByMobileNumber;
    }
}
