package management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultation")
@Data
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_id")
    private Long consultationID;

    @Column(name ="doctor_id")
    private Long doctorID;

    @Column(name ="patient_id")
    private Long patientID ;

    @Column(name ="consultation_date")
    private LocalDateTime consultationDate;

    @Column(name = "diagnosis")
    private String diagnosis;
}
