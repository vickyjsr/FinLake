package com.finlake.model;

import com.finlake.enums.RoomType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
public class FinanceRoom {

    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "FK_finance_rooms_users"))
    private User created_by;

    @Enumerated(EnumType.STRING)
    private RoomType room_type;

    @GeneratedValue
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    @CreationTimestamp
    private Timestamp created_at;

    @GeneratedValue
    @Column(name = "updated_at", columnDefinition = "timestamp")
    @UpdateTimestamp
    private Timestamp updated_at;

    public FinanceRoom() {

    }

    public FinanceRoom(String id, String name, User created_by, RoomType room_type, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.name = name;
        this.created_by = created_by;
        this.room_type = room_type;
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

    public User getCreated_by() {
        return created_by;
    }

    public void setCreated_by(User created_by) {
        this.created_by = created_by;
    }

    public RoomType getRoom_type() {
        return room_type;
    }

    public void setRoom_type(RoomType room_type) {
        this.room_type = room_type;
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

    @Override
    public String toString() {
        return "FinanceRoom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", created_by=" + created_by +
                ", room_type=" + room_type +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
