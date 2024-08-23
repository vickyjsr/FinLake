package com.finlake.common.strategy.user;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.InvalidFieldFactoryException;
import com.finlake.common.strategy.RetrieveStrategy;
import com.finlake.model.User;

import java.util.function.Function;

public class GetUserStrategy {
    private final RetrieveStrategy<User> strategy;

    public GetUserStrategy(UserRetrieveStrategyImpl userRetrieveStrategy, String email, String mobileNumber, String id, String requestId) {
        if (email != null && !email.isEmpty()) {
            this.strategy = new GetUserByEmailStrategy(userRetrieveStrategy);
        } else if (mobileNumber != null && !mobileNumber.isEmpty()) {
            this.strategy = new GetUserByMobileNumberStrategy(userRetrieveStrategy);
        } else if (id != null && !id.isEmpty()) {
            this.strategy = new GetUserByIdStrategy(userRetrieveStrategy);
        } else {
            throw new InvalidFieldFactoryException(requestId, ResponseCode.INVALID_FIELD_FACTORY_EXCEPTION);
        }
    }

    public static String getSearchValue(String email, String mobileNumber, String id) {
        if (email != null && !email.isEmpty()) {
            return email;
        } else if (mobileNumber != null && !mobileNumber.isEmpty()) {
            return mobileNumber;
        } else {
            return id;
        }
    }

    public Function<String, User> getStrategy() {
        return strategy.getStrategy();
    }
}
