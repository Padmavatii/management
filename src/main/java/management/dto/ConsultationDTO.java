package management.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationDTO {

    private Long consultationID;

    @NotNull(message = "doctorId is required")
    private Long doctorID;

    @NotNull(message = "patientId is required")
    private Long patientID ;

    private LocalDateTime consultationDate;

    @NotBlank(message = "Diagnosis description cannot be null")
    @Size(min = 2, max = 50, message = "Diagnosis must be between 2 and 50 characters")
    private String diagnosis;
}
