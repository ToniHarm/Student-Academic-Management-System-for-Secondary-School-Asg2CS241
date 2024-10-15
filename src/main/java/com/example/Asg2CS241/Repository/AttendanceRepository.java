package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Attendance;
import com.example.Asg2CS241.Entity.Course;
import com.example.Asg2CS241.Entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
//    // Retrieve attendance records by student and course
//    List<Attendance> findByStudentAndCourse(Student student, Course course);
//
//    // Retrieve attendance records by student, course, and week
//    List<Attendance> findByStudentAndCourseAndWeek(Student student, Course course, int week);
//
//    // Retrieve attendance records by course and week
//    List<Attendance> findByCourseAndWeek(Course course, int week);
//
//    // Retrieve attendance records by course, week, and day of the week
//    List<Attendance> findByCourseAndWeekAndDay_of_week(Course course, int week, String day_of_week);


//    Optional<Attendance> findByStudentAndweekAndday_of_week(Long studentId, int week, String dayOfWeek);

    List<Attendance> findByCourse_Classid(Long classId);

    // Fetch attendance for a specific class and week, with pagination
    Page<Attendance> findByCourse_ClassidAndWeek(Long classId, int week, Pageable pageable);

    List<Attendance> findByCourse_ClassidAndWeek(Long classId, int week);

    // Count distinct number of weeks for a given class
    @Query("SELECT COUNT(DISTINCT a.week) FROM Attendance a WHERE a.course.classid = :classId")
    int countDistinctWeeksByClassId(@Param("classId") Long classId);

    @Query("SELECT a FROM Attendance a WHERE a.student.stuid = :studentId AND a.course.classid = :classId ORDER BY a.week ASC")
    List<Attendance> findByStudentIdAndClassId(@Param("studentId") Long studentId, @Param("classId") Long classId);


    @Query("SELECT "
            + "(SUM(CASE WHEN a.status = 'present' THEN 1 ELSE 0 END) / COUNT(a)) * 100 AS presentPercentage, "
            + "(SUM(CASE WHEN a.status = 'late' THEN 1 ELSE 0 END) / COUNT(a)) * 100 AS latePercentage, "
            + "(SUM(CASE WHEN a.status = 'absent' THEN 1 ELSE 0 END) / COUNT(a)) * 100 AS absentPercentage "
            + "FROM Attendance a WHERE a.course.classid = :courseId")
    Map<String, Double> findAttendancePercentagesByCourseId(@Param("courseId") Long courseId);

}
