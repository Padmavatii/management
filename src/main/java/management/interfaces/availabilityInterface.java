package management.interfaces;

import management.common.GenericResponse;
import management.dto.AvailabilityDTO;
import management.entity.Availability;

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

    /**
     *
     * @return list of doctor availability
     */
    public Availability getAvailability();

    /**
     *
     * @param id
     * @return
     */
    Optional<Availability> getAvailabilityById(Long id);
}
