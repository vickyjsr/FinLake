package com.finlake.common.strategy;

import java.util.function.Function;

public interface RetrieveStrategy<T> {
    Function<String, T> getStrategy();
}
