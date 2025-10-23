package com.manideep.skilltunerai.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    @Column(name = "updation_time")
    @UpdateTimestamp
    private LocalDateTime updationTime;

    @Column(name = "active_resume_id")
    private long activeResumeId;

    public User(long id, String firstName, String lastName, String email, String password, LocalDateTime creationTime,
            LocalDateTime updationTime, long activeResumeId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.creationTime = creationTime;
        this.updationTime = updationTime;
        this.activeResumeId = activeResumeId;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public LocalDateTime getUpdationTime() {
        return updationTime;
    }

    public long getActiveResumeId() {
        return activeResumeId;
    }
    public void setActiveResumeId(long activeResumeId) {
        this.activeResumeId = activeResumeId;
    }

}
