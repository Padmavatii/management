package management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "availability")
@Data
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availability_id")
    private Long availabilityId;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "doctor_name")
    private String doctorName;

    @Column(name = "shift_timing_start_time")
    private LocalTime shiftTimingStartTime;

    @Column(name = "shift_timing_end_time")
    private LocalTime shiftTimingEndTime;

    @Column(name = "status")
    private String status;

}
