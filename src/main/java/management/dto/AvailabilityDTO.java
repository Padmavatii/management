package management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "doctorId is required")
    private Long doctorId;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String doctorName;

    @NotNull(message = "Timing is required")
    private LocalTime shiftTimingStartTime;

    @NotNull(message = "Timing is required")
    private LocalTime shiftTimingEndTime;

    @NotBlank(message = "Status is required")
    private String status;

}
