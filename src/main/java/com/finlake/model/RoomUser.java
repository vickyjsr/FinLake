package com.finlake.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
public class RoomUser {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne
    @JoinColumn(name = "finance_room", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_room_users_finance_rooms"))
    private FinanceRoom finance_room;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_room_users_users"))
    private User user;

    private String status;

    @GeneratedValue
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp created_at;

    @GeneratedValue
    @Column(name = "updated_at", columnDefinition = "timestamp")
    @UpdateTimestamp
    private Timestamp updated_at;

    public RoomUser() {

    }

    public RoomUser(String id, FinanceRoom finance_room, User user, Timestamp created_at, Timestamp updated_at, String status) {
        this.id = id;
        this.finance_room = finance_room;
        this.user = user;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FinanceRoom getFinance_room() {
        return finance_room;
    }

    public void setFinance_room(FinanceRoom finance_room) {
        this.finance_room = finance_room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
