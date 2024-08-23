package com.finlake.controller;

import com.finlake.common.enums.ResponseCode;
import com.finlake.model.FinanceRoom;
import com.finlake.model.request.FinanceRoomRequestDTO;
import com.finlake.model.response.FinlakeResponse;
import com.finlake.service.BaseResponseService;
import com.finlake.service.FinanceRoomServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1/room")
@Tag(name = "3. Finance Room Controller")
public class FinanceRoomController implements FinanceRoomControllerApi {

    private final FinanceRoomServiceImpl financeRoomService;

    private final BaseResponseService baseResponseService;

    public FinanceRoomController(FinanceRoomServiceImpl financeRoomService, BaseResponseService baseResponseService) {
        this.financeRoomService = financeRoomService;
        this.baseResponseService = baseResponseService;
    }

    @Override
    public ResponseEntity<FinlakeResponse<FinanceRoom>> saveFinanceRoom(FinanceRoomRequestDTO financeRoomRequestDTO) {
        FinanceRoom financeRoom = financeRoomService.save(financeRoomRequestDTO);
        return baseResponseService.ok(financeRoom, financeRoomRequestDTO.getRequestId(), ResponseCode.FINANCE_ROOM_CREATED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<FinanceRoom>> getFinanceRoom(String requestId, String id, String status) {
        FinanceRoom financeRoom = financeRoomService.getFinanceRoom(requestId, id, status);
        return baseResponseService.ok(financeRoom, requestId, ResponseCode.FINANCE_ROOM_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<FinanceRoom>>> getAllFinanceRooms(String requestId, Pageable pageable, String status) {
        Page<FinanceRoom> financeRooms = financeRoomService.allFinanceRooms(requestId, status, pageable);
        return baseResponseService.ok(financeRooms, requestId, ResponseCode.FINANCE_ROOM_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<FinanceRoom>>> filterUserFromFinanceRoom(String requestId, Pageable pageable, String status, String userId, String roomType) {
        Page<FinanceRoom> financeRooms = financeRoomService.filterRoomsByUserId(requestId, status, userId, roomType, pageable);
        return baseResponseService.ok(financeRooms, requestId, ResponseCode.FINANCE_ROOM_FETCHED.getCode());
    }

    //    @PostMapping("/newFinanceRoom")
//    public FinanceRoom saveFinanceRoom(@RequestBody FinanceRoomRequestDTO financeRoomRequestDTO) {
//
////        FinanceRoom financeRoom = new FinanceRoom();
////        financeRoom.setFinanceRoomName(financeRoomRequestDTO.getFinanceRoomName());
////        financeRoom.setStatus(GlobalEnum.STATUS_ACTIVE.getStringValue());
////
////        Optional<User> roomUserCreator = userRepository.findById(financeRoomRequestDTO.getCreatedBy());
////
////        if (roomUserCreator.isEmpty()) {
////            return null;
////        } else {
////            financeRoom.setCreatedBy(roomUserCreator.get().getId());
////        }
////
////        if (financeRoomRequestDTO.getRoomType().equals(RoomType.GROUP.getValue())) {
////            financeRoom.setRoomType(RoomType.GROUP);
////        } else {
////            financeRoom.setRoomType(RoomType.ONE_ON_ONE);
////        }
////
////        FinanceRoom createFinanceRoom = financeRoomRepository.save(financeRoom);
////        User user = roomUserCreator.get();
////        UserResponse userCreatorResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getMobileNumber(), user.getCreatedAt(), user.getUpdatedAt());
////        userResponses.add(userCreatorResponse);
////        createFinanceRoomWithUser(createFinanceRoom, userResponses);
////        return createFinanceRoom;
//    }
//
//    @GetMapping("/get_finance_room_name")
//    String getFinanceRoomName(@RequestParam(name = "id") String id) {
////        Optional<FinanceRoom> financeRoomOptional = financeRoomRepository.findById(id);
////        if (financeRoomOptional.isPresent()) {
////            return financeRoomOptional.get().getFinanceRoomName();
////        } else {
////            return "";
////        }
//    }
//
//    @GetMapping("/listAllFinanceRooms")
//    List<FinanceRoom> getAllFinanceRooms(
//            @RequestParam(name = "pageable", required = false, defaultValue = "0") Pageable pageable,
//            @RequestParam(name = "status", required = false, defaultValue = "active") String status) {
////        return financeRoomRepository.findAllByStatusAndPagination(status, pageable);
//    }
//
//    @GetMapping("/filterUserFromFinanceRoom")
//    public List<FinanceRoom> filterUserFromFinanceRoom(
//            @RequestParam(name = "pageable", required = false, defaultValue = "0") Pageable pageable,
//            @RequestParam(name = "status", required = false, defaultValue = "active") String status,
//            @RequestParam(name = "id") String user_id) {
////        System.out.println("Fetched" + page + " " + pageSize + " " + pagination + " " + status + " " + user_id);
////        List<Sort.Order> sorting = new ArrayList<>();
////        sorting.add(new Sort.Order(Sort.Direction.ASC, "updated_at"));
////        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));
////        List<RoomUser> roomUsers;
////        todo use sql use joins u idiot wth is this
////        if (pagination) {
////            roomUsers = roomUserRepository.findAllByUser_IdAndStatusAndPaginate(user_id, status, pageable);
////        } else {
////            roomUsers = roomUserRepository.findAllByUser_IdAndStatus(user_id, status);
////        }
////        List<String> financeRoomIds = new ArrayList<>();
////        for (RoomUser roomUser : roomUsers) {
////            financeRoomIds.add(roomUser.getFinance_room().getId());
////        }
////        return financeRoomRepository.findAllByIdAndStatus(financeRoomIds, status);
//    }

//    public void createFinanceRoomWithUser(FinanceRoom financeRoom, List<UserResponse> userList) {
//        for (UserResponse userResponse : userList) {
//            RoomUser roomUser = new RoomUser();
//            Optional<User> user = userRepository.findById(userResponse.getId());
//            user.ifPresent(roomUser::setUser);
//            roomUser.setFinance_room(financeRoom);
//            roomUser.setStatus(GlobalEnum.STATUS_ACTIVE.getStringValue());
//            roomUserRepository.save(roomUser);
//        }
//    }
}
