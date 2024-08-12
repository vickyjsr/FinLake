package com.finlake.model;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionSplit {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "user_id")
    private String userId;

    //    todo should i store a paid_by too??
    @Column(name = "paid_by_user")
    private String paidByUser;

    @Column(name = "amount")
    private String amount;

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
