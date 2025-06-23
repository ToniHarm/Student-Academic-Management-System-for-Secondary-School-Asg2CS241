package com.example.Asg2CS241.Entity;

import java.util.HashMap;
import java.util.Map;

public class WeeklyAttendanceDTO {
    private Long studentId;
    private String studentName;
    private int week;
    private Map<String, String> dailyAttendance = new HashMap<>();  // Map with day of week as key and attendance status as value

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public Map<String, String> getDailyAttendance() {
        return dailyAttendance;
    }

    public void setDailyAttendance(Map<String, String> dailyAttendance) {
        this.dailyAttendance = dailyAttendance;
    }
// Getters and setters
}