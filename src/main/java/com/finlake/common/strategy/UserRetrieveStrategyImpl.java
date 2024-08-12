package com.finlake.common.strategy;

import com.finlake.dao.UserDaoImpl;
import com.finlake.model.User;

public class UserRetrieveStrategyImpl implements UserRetrieveStrategy {
    private final UserDaoImpl userDao;

    public UserRetrieveStrategyImpl(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findUserByMobileNumber(String mobileNumber) {
        return userDao.findUserByMobileNumber(mobileNumber);
    }

    @Override
    public User findUserById(String id) {
        return userDao.findUserById(id);
    }
}
