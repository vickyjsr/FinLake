package com.finlake.common.strategy.user;

import com.finlake.model.User;

public interface UserRetrieveStrategy {
    User findUserByEmail(String email);

    User findUserByMobileNumber(String mobileNumber);

    User findUserById(String id);
}
