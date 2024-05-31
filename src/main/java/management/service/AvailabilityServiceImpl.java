package management.service;

import management.common.GenericResponse;
import management.common.NotFoundException;
import management.dto.AvailabilityDTO;
import management.entity.Availability;
import management.interfaces.availabilityInterface;
import management.repository.AvailabilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements availabilityInterface {

    Logger logger = LoggerFactory.getLogger(AvailabilityServiceImpl.class);

    private final AvailabilityRepository availabilityRepository;

    private final MessageSource messageSource;

    @Autowired
    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, MessageSource messageSource) {
        this.availabilityRepository = availabilityRepository;
        this.messageSource = messageSource;
    }

    private static final String DOCTOR_AVAILABILITY_SUBMIT_SUCCESS="Availability.submit.success";
    private static final String DOCTOR_AVAILABILITY_UPDATE_SUCCESS="Availability.update.success";
    private static final String DOCTOR_AVAILABILITY_NOT_FOUND="Availability.Not.Found";

    @Transactional
    @Override
    public GenericResponse submitAvailability(AvailabilityDTO availabilityDTO) {
        logger.info("API call to add availability of doctor{}",availabilityDTO);

//        Long doctorId = availabilityDTO.getDoctorId();
//        if(doctorId == null){
//            throw new GenericException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), messageSource.getMessage(, null, Locale.ENGLISH))
//        }

        Availability availability = new Availability();
        availability.setDoctorId(availabilityDTO.getDoctorId());
        availability.setDoctorName(availabilityDTO.getDoctorName());
        availability.setShiftTimingStartTime(availabilityDTO.getShiftTimingStartTime());
        availability.setShiftTimingEndTime(availabilityDTO.getShiftTimingEndTime());
        availability.setStatus(availabilityDTO.getStatus());

        availabilityRepository.save(availability);

        GenericResponse response = new GenericResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setResponseCode(DOCTOR_AVAILABILITY_SUBMIT_SUCCESS);
        response.setResponseMessage(messageSource.getMessage(DOCTOR_AVAILABILITY_SUBMIT_SUCCESS, null, Locale.ENGLISH));

        return response;
    }

    @Transactional
    @Override
    public GenericResponse updateAvailability(Long id, AvailabilityDTO availabilityDTO){
        logger.info("API call to update availability of doctor{}", availabilityDTO);
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(DOCTOR_AVAILABILITY_NOT_FOUND, null, Locale.ENGLISH)));

        availability.setDoctorId(availabilityDTO.getDoctorId());
        availability.setDoctorName(availabilityDTO.getDoctorName());
        availability.setShiftTimingStartTime(availabilityDTO.getShiftTimingStartTime());
        availability.setShiftTimingEndTime(availabilityDTO.getShiftTimingEndTime());
        availability.setStatus(availabilityDTO.getStatus());

        availabilityRepository.save(availability);

        GenericResponse response = new GenericResponse();
        response.setStatus(HttpStatus.OK.value());
        response.setResponseCode(DOCTOR_AVAILABILITY_UPDATE_SUCCESS);
        response.setResponseMessage(messageSource.getMessage(DOCTOR_AVAILABILITY_UPDATE_SUCCESS, null, Locale.ENGLISH));

        return response;

    }

    @Override
    public List<Availability> getAvailability() {
        logger.info("API call to get all the availability of doctors");
        return availabilityRepository.findAll();
    }

    @Override
    public Optional<Availability> getAvailabilityById(Long availabilityId) {
        logger.info("API call to get a doctor by their ID{}",availabilityId );
        Optional<Availability> availability = availabilityRepository.findById(availabilityId);
        if(availability.isEmpty()){
            logger.error("Invalid Id passed{}",availabilityId);
            throw new NotFoundException(messageSource.getMessage(DOCTOR_AVAILABILITY_NOT_FOUND, null, Locale.ENGLISH));
        }
        return availability;
    }


}
