package management.interfaces;

import management.common.GenericResponse;
import management.dto.ConsultationDTO;
import management.entity.Consultation;

import java.util.List;
import java.util.Optional;

public interface consultationInterface {
    /**
     *
     * @param consultationDTO
     * @return
     */
    GenericResponse submitConsultation(ConsultationDTO consultationDTO);

    /**
     *
     * @param id
     * @param consultationDTO
     * @return
     */
    GenericResponse updateConsultation(Long id,ConsultationDTO consultationDTO);

    /**
     *
     * @return list of consultations of doctor
     */
    List<Consultation> getAllConsultations();

    /**
     *
     * @param id
     * @return
     */
    Optional<Consultation> getConsultation(long id);
}
