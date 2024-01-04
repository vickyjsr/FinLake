package com.finlake.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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

    public RoomUser(String id, FinanceRoom finance_room, User user, String status, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.finance_room = finance_room;
        this.user = user;
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
