package com.manideep.skilltunerai.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_table")
public class Users {

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
    private Long activeResumeId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resume> resumes = new ArrayList<>();

    public Users() {
    }
    public Users(String firstName, String lastName, String email, String password, LocalDateTime creationTime, LocalDateTime updationTime, Long activeResumeId) {
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
    public void setActiveResumeId(Long activeResumeId) {
        this.activeResumeId = activeResumeId;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    // Helper methods
    public void addResume(Resume resume) {
        resumes.add(resume);
        resume.setUser(this);
    }
    public void removeResume(Resume resume) {
        resumes.remove(resume);
        resume.setUser(null);
    }

}
