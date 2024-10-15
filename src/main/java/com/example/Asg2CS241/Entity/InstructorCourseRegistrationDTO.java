package com.example.Asg2CS241.Entity;

public class InstructorCourseRegistrationDTO {

    private Long classId;
    private Long courseInstructorId;

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getCourseInstructorId() {
        return courseInstructorId;
    }

    public void setCourseInstructorId(Long courseInstructorId) {
        this.courseInstructorId = courseInstructorId;
    }
}
