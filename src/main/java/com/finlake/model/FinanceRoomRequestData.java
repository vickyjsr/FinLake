package com.finlake.models;

import com.finlake.model.FinanceRoomBody;
import com.finlake.model.UserResponse;

import java.util.List;

public class FinanceRoomRequestData {
    private FinanceRoomBody financeRoom;
    private List<UserResponse> userList;

    public FinanceRoomRequestData(FinanceRoomBody financeRoom, List<UserResponse> userList) {
        this.financeRoom = financeRoom;
        this.userList = userList;
    }

    public FinanceRoomBody getFinanceRoom() {
        return financeRoom;
    }

    public void setFinanceRoom(FinanceRoomBody financeRoom) {
        this.financeRoom = financeRoom;
    }

    public List<UserResponse> getUserList() {
        return userList;
    }

    public void setUserList(List<UserResponse> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "FinanceRoomRequestData{" +
                "financeRoom=" + financeRoom.toString() +
                ", userList=" + userList.toString() +
                '}';
    }
}
