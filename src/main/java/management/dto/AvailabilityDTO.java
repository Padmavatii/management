package management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvailabilityDTO {

    private Long availabilityId;

    @NotBlank(message = "doctor Id cannot be null")
    private Long doctorId;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String doctorName;

    @NotBlank(message = "Timing is required")
    @Pattern(regexp = "^([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$", message = "Timing is not valid")
    private LocalTime shiftTiming;

    @NotBlank(message = "Status is required")
    private String status;

}
