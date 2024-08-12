package com.finlake.common.strategy;

import com.finlake.model.User;

import java.util.function.Function;

public interface RetrieveStrategy {
    Function<String, User> getStrategy();
}
