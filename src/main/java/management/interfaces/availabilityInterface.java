package management.interfaces;

import management.common.GenericResponse;
import management.dto.AvailabilityDTO;

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
}
