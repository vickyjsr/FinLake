package com.finlake.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
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

    public Transaction() {

    }

    public Transaction(String id, String name, String description, String amount, User paid_by_user, FinanceRoom finance_room, String status, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.paid_by_user = paid_by_user;
        this.finance_room = finance_room;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPaid_by_user() {
        return paid_by_user;
    }

    public void setPaid_by_user(User paid_by_user) {
        this.paid_by_user = paid_by_user;
    }

    public FinanceRoom getFinance_room() {
        return finance_room;
    }

    public void setFinance_room(FinanceRoom finance_room) {
        this.finance_room = finance_room;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
