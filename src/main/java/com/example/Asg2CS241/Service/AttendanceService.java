package com.example.Asg2CS241.Service;
import com.example.Asg2CS241.Entity.Attendance;
import com.example.Asg2CS241.Repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    // Find attendance by week, student ID, and day of the week
    public Optional<Attendance> findByWeekAndStudentIdAndDayOfWeekAndClassId(Long studentId, Long classId, int week, String dayOfWeek) {
        return attendanceRepository.findByWeekAndStudentIdAndDayOfWeekAndClassId(studentId, classId, week, dayOfWeek);
    }
    // Save or update attendance
    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }
    // Delete attendance record
    public void deleteAttendance(Attendance attendance) {
        attendanceRepository.delete(attendance);
    }
}