package com.project.arbeit.attendance;

import com.project.arbeit.common.BaseEntity;
import com.project.arbeit.employee.Employee;
import com.project.arbeit.store.Store;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer attendanceDate;

    private String checkInTime;

    private String checkOutTime;

    private AttendanceStatus status;

    private String workHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
