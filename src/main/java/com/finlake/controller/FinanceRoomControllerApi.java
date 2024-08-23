package com.finlake.controller;

import com.finlake.model.FinanceRoom;
import com.finlake.model.request.FinanceRoomRequestDTO;
import com.finlake.model.response.FinlakeResponse;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public interface FinanceRoomControllerApi {
    @PostMapping("/new")
    ResponseEntity<FinlakeResponse<FinanceRoom>> saveFinanceRoom(@RequestBody FinanceRoomRequestDTO financeRoomRequestDTO);

    @GetMapping("/get")
    ResponseEntity<FinlakeResponse<FinanceRoom>> getFinanceRoom(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                @RequestParam(name = "id") String id,
                                                                @RequestParam(name = "status", required = false, defaultValue = "active") String status);

    @GetMapping("/list")
    ResponseEntity<FinlakeResponse<Page<FinanceRoom>>> getAllFinanceRooms(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                          @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                                  direction = Sort.Direction.ASC) Pageable pageable,
                                                                          @RequestParam(name = "status", required = false, defaultValue = "active") String status);

    //    todo in future can make strategy class to make this method suitable for userId, roomId
    @GetMapping("/filter")
    ResponseEntity<FinlakeResponse<Page<FinanceRoom>>> filterUserFromFinanceRoom(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                                 @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                                         direction = Sort.Direction.ASC) Pageable pageable,
                                                                                 @RequestParam(name = "status", required = false, defaultValue = "active") String status,
                                                                                 @RequestParam(name = "userId") String userId,
                                                                                 @RequestParam(name = "roomType") String roomType);
}
