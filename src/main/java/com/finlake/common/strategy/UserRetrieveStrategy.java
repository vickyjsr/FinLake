package com.finlake.common.strategy;

import com.finlake.model.User;

public interface UserRetrieveStrategy {
    User findUserByEmail(String email);

    User findUserByMobileNumber(String mobileNumber);

    User findUserById(String id);
}
