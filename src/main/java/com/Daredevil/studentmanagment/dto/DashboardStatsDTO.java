package com.Daredevil.studentmanagment.dto;

public class DashboardStatsDTO {

    private long totalStudents;
    private long totalCourses;
    private String topPerformingCourse;
    private long studentsEnrolledThisMonth;


    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public String getTopPerformingCourse() {
        return topPerformingCourse;
    }

    public void setTopPerformingCourse(String topPerformingCourse) {
        this.topPerformingCourse = topPerformingCourse;
    }

    public long getStudentsEnrolledThisMonth() {
        return studentsEnrolledThisMonth;
    }

    public void setStudentsEnrolledThisMonth(Long studentsEnrolledThisMonth) {
        this.studentsEnrolledThisMonth = studentsEnrolledThisMonth;
    }
}
