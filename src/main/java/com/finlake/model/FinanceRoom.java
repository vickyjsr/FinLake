package com.finlake.model;

import com.finlake.common.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FinanceRoom {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "request_id")
    String requestId;

    @Column(name = "finance_room_name")
    private String financeRoomName;

    @Column(name = "created_by")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type")
    private RoomType roomType;

    @Column(name = "status")
    private String status;

    @GeneratedValue
    @Column(name = "created_at", columnDefinition = "TIMESTAMP", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @GeneratedValue
    @Column(name = "updated_at", columnDefinition = "timestamp")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Version
    private Integer version;
}
