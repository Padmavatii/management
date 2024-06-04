package management.interfaces;

import management.common.GenericResponse;
import management.dto.AvailabilityDTO;
import management.entity.Availability;

import java.util.List;
import java.util.Optional;

public interface availabilityInterface {

    /**
     *
     * @param availabilityDTO
     * @return
     */
    GenericResponse submitAvailability(AvailabilityDTO availabilityDTO);


    /**
     *
     * @param id
     * @param availabilityDTO
     * @return
     */
    GenericResponse updateAvailability(Long id, AvailabilityDTO availabilityDTO);

    public Availability getAvailability();

    Optional<Availability> getAvailabilityById(Long id);
}
