package com.finlake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finlake.model.FinanceRoomBody;
import com.finlake.model.UserResponse;

import java.util.List;

public class JsonPayloadConverter {

    public static FinanceRoomBody jsonToFinanceRoomBody(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode financeRoomBodyNode = jsonNode.get("financeRoomBody");
            return objectMapper.treeToValue(financeRoomBodyNode, FinanceRoomBody.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<UserResponse> jsonToUserResponseList(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode userResponseListNode = jsonNode.get("userResponseList");
            TypeReference<List<UserResponse>> typeRef = new TypeReference<List<UserResponse>>() {
            };
            return objectMapper.convertValue(userResponseListNode, typeRef);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



