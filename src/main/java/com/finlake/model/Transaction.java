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
public class Transaction {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name, description, amount, status;

    @ManyToOne
    @JoinColumn(name = "paid_by_user", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_transactions_users"))
    private User paid_by_user;

    @ManyToOne
    @JoinColumn(name = "finance_room", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_transactions_finance_rooms"))
    private FinanceRoom finance_room;

    @GeneratedValue
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp created_at;

    @GeneratedValue
    @Column(name = "updated_at", columnDefinition = "timestamp")
    @UpdateTimestamp
    private Timestamp updated_at;

}
