package com.Daredevil.studentmanagment.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false, unique = true)
    private String courseCode;

    private String duration;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(precision = 12, scale = 2, nullable = false)
    private BigDecimal fee;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL, orphanRemoval = true )
    private Set<Enrollment> enrollments = new HashSet<>();


    public Courses(){
    }

    public Courses(String courseName, String courseCode, String duration, boolean active, BigDecimal fee, String description, LocalDateTime createdAt) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.duration = duration;
        this.active = active;
        this.fee = fee;
        this.description = description;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
    }
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", duration='" + duration + '\'' +
                ", active=" + active +
                ", fee=" + fee +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
